package com.youngtechcr.www.security.eidte;


import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.security.idp.IdentityProvider;
import com.youngtechcr.www.security.user.User;
import com.youngtechcr.www.security.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.Map;

public class EidteAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(EidteAuthenticationProvider.class);
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private final UserService userService;

    public EidteAuthenticationProvider(
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
            UserService userService) {
        this.userService = userService;
        // Yeah, I absolutely copy pasted this 2 assertions above
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       EidteAuthentication eidteAuthentication = (EidteAuthentication) authentication;
       RegisteredClient registeredClient = eidteAuthentication.getRegisteredClient();
       Map<String, Object> claims = eidteAuthentication.getTokenAttributes();
       OidcIdToken eidteToken = eidteAuthentication.getToken();
       IdentityProvider identityProvider = eidteAuthentication.getIdentityProvider();

//       AnonymousAuthenticationToken principal =
//               (AnonymousAuthenticationToken) eidteAuthentication.getPrincipal();

       OAuth2ClientAuthenticationToken clientAuthToken =
               getAuthenticatedClientElseThrowInvalidClient(eidteAuthentication);
        logger.trace("clientAuthToken -> " +  clientAuthToken);


//       // ------------------------------------------------------
//       //  #1 Register user here if not already :v
//       // ------------------------------------------------------
       User user = userService.createFromEidteExchange(eidteToken, identityProvider);
       EidteAuthenticationPrincipal authPrincipal =
               new EidteAuthenticationPrincipal(user);
//
//
       // #2 Generate tokens
       OAuth2TokenContext accessTokenContext = contextFromParams (
               OAuth2TokenType.ACCESS_TOKEN, eidteAuthentication, registeredClient, clientAuthToken
       );
        OAuth2TokenContext refreshTokenContext = contextFromParams (
                OAuth2TokenType.REFRESH_TOKEN, eidteAuthentication, registeredClient, clientAuthToken
        );
        OAuth2AccessToken accessToken = (OAuth2AccessToken) tokenFromContext(accessTokenContext);
        OAuth2RefreshToken refreshToken = (OAuth2RefreshToken) tokenFromContext(refreshTokenContext);




        // #3 Build and save authorization

        OAuth2Authorization authorizationBuilder = OAuth2Authorization
                .withRegisteredClient(registeredClient)
                .principalName(String.valueOf(authPrincipal.getName()))
                .authorizationGrantType(EidteParameters.GRANT_TYPE_INSTANCE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .attribute(Principal.class.getName(), authPrincipal)
                // ^^^ this mf attribute up here is important as hell,
                // had to deal with a NullPointerException because of this crap.
                // guess I just need to re-write everything in Rust
                .build();

        this.authorizationService.save(authorizationBuilder);
        logger.info("yeeeeeiii we authenticated a user!!!!");
        return new OAuth2AccessTokenAuthenticationToken(
                registeredClient,
                clientAuthToken,
                accessToken,
                refreshToken
        );

//         Generatedtokens ^^^^
//        generate access_token() -> done
//        generate refresh_token() -> done
//        generate id_token() -> how ???
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EidteAuthentication.class.isAssignableFrom(authentication);
    }

    public OAuth2TokenContext contextFromParams(
            OAuth2TokenType tokenType,
            Authentication representationOfAuthzGrant,
            RegisteredClient registeredClient,
            OAuth2ClientAuthenticationToken clientPrincipal
    ) {
        return DefaultOAuth2TokenContext.builder()
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .tokenType(tokenType)
                .authorizationGrantType(EidteParameters.GRANT_TYPE_INSTANCE)
                .authorizationGrant(representationOfAuthzGrant)
                .registeredClient(registeredClient)
                .principal(clientPrincipal)
                .build();
    }


    public OAuth2Token tokenFromContext(OAuth2TokenContext context) {
        OAuth2Token generatedToken = this.tokenGenerator.generate(context);
        if(generatedToken == null) {
            OAuth2Error error = new OAuth2Error(
                    OAuth2ErrorCodes.SERVER_ERROR,
                    HttpErrorMessages.FAILED_GENERATE_TOKEN,
                    null);
            throw new OAuth2AuthenticationException(error);
        }
       OAuth2TokenType tokenType = context.getTokenType();
       OAuth2Token actualToken = null;
       if(tokenType.equals(OAuth2TokenType.ACCESS_TOKEN)) {
            actualToken = new OAuth2AccessToken(
                    OAuth2AccessToken.TokenType.BEARER,
                    generatedToken.getTokenValue(),
                    generatedToken.getIssuedAt(),
                    generatedToken.getExpiresAt()
            );
       }
       else if (tokenType.equals(OAuth2TokenType.REFRESH_TOKEN)) {
           actualToken = new OAuth2RefreshToken(
                   generatedToken.getTokenValue(),
                   generatedToken.getIssuedAt(),
                   generatedToken.getExpiresAt()
           );
       }
       return actualToken;
    }


    public static OAuth2ClientAuthenticationToken
    getAuthenticatedClientElseThrowInvalidClient(
            EidteAuthentication authentication
    ) {
        OAuth2ClientAuthenticationToken clientPrincipal = null;
        Class clientPrincipalsAuthnClass = authentication.getClientPrincipal().getClass();
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(clientPrincipalsAuthnClass)) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getClientPrincipal();
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }

}
