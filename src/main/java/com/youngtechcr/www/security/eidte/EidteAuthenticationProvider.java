package com.youngtechcr.www.security.eidte;


import com.youngtechcr.www.security.idp.IdentityProvider;
import com.youngtechcr.www.security.oidc.OIdcHelpers;
import com.youngtechcr.www.security.user.User;
import com.youngtechcr.www.security.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
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
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class EidteAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(EidteAuthenticationProvider.class);
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private final UserService userService;

    public EidteAuthenticationProvider(
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
            UserService userService
    ) {
        this.userService = userService;
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.trace("Starting EIDTE exchange");
        EidteAuthentication eidteAuthentication = (EidteAuthentication) authentication;
        RegisteredClient registeredClient = eidteAuthentication.getRegisteredClient();
        Map<String, Object> claims = eidteAuthentication.getTokenAttributes();
        OidcIdToken eidteToken = eidteAuthentication.getToken();
        IdentityProvider identityProvider = eidteAuthentication.getIdentityProvider();

        OAuth2ClientAuthenticationToken clientAuthToken =
                getAuthenticatedClientElseThrowInvalidClient(eidteAuthentication);
        logger.trace("clientAuthToken -> " + clientAuthToken);


        // ------------------------------------------------------
        //  #1 Register user here if not already :v
        // ------------------------------------------------------
        User user = userService.createFromEidteExchange(eidteToken, identityProvider);
        EidteAuthenticationPrincipal eidteAuthPrincipal = new EidteAuthenticationPrincipal(user);


        // -----------------------------------------------
        // #2 Prepare parameters for building tokens and authorization
        // -----------------------------------------------
        Set<String> authorizedScopes = user.getRoles()
                .stream().map(role -> role.getName()).collect(Collectors.toSet());

        if(registeredClient.getScopes().contains(OidcScopes.OPENID)) {
            authorizedScopes.add(OidcScopes.OPENID);
        }

        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(eidteAuthPrincipal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authorizedScopes)
                .authorizationGrant(eidteAuthentication);

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization
                .withRegisteredClient(registeredClient)
//                .principalName(String.valueOf(eidteAuthPrincipal.getName()))
                .principalName(String.valueOf(eidteAuthPrincipal.getId()))
                .authorizationGrantType(EidteParameters.GRANT_TYPE_INSTANCE)
                .authorizedScopes(authorizedScopes)
                .attribute(Principal.class.getName(), eidteAuthPrincipal)
                // ^^^ this mf attribute up here is important as hell,
                // had to deal with a NullPointerException because of this crap.
                // guess I just need to re-write everything in Rust
                ;

        // -----------------------------------------------
        // #3 Access Token
        // -----------------------------------------------
        OAuth2TokenContext tokenContext =
                tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }
        this.logger.trace("Generated access token");

        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER, generatedAccessToken.getTokenValue(),
                generatedAccessToken.getIssuedAt(), generatedAccessToken.getExpiresAt(),
                tokenContext.getAuthorizedScopes());
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) -> {
                metadata.put(
                        OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
                        ((ClaimAccessor) generatedAccessToken).getClaims()
                );
                metadata.put(
                        OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, false);
            });
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        // -----------------------------------------------
        // #3 Refresh Token
        // -----------------------------------------------
        tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
        OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
        if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the refresh token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }
        this.logger.trace("Generated access token");
        OAuth2RefreshToken refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
        authorizationBuilder.refreshToken(refreshToken);


        // -----------------------------------------------
        // #3 Id Token
        // -----------------------------------------------
        // also check if mf requested scopes in EIDTE request
        // e.g: if requestedScopes.contains(OidcScopes.OPENID) ...
        OidcIdToken idToken;
        if (registeredClient.getScopes().contains(OidcScopes.OPENID)) {


            // ---------------------------------------------------------------------------------------------------------
            /*   TODO: GIVE A PROPER FIX (implementation details???) to THIS PROBLEM BELOW!!!!!
            *   DONT CHANGE THE CODE BETWEEN THE hyphened-lines("---") PLZZZZZ
            *   It is a WORKAROUND that LETS clients make REFRESH_TOKEN REQUESTS
            *   WHEN OBTAINING TOKENS from and EIDTE exchange.
            *   Dont mess aourund with this in production PLZZZZZZZ, since I spent
            *   like 7 hours reading spring's security source code and reseacrhing
            *   the till I finally found a patch
            * */
            OAuth2AuthorizationRequest authReq = OAuth2AuthorizationRequest
                    .authorizationCode()
                    .additionalParameters(params -> params.put(
                            OidcParameterNames.NONCE, "nonce"
                    ))
                    .authorizationUri("/")
                    .clientId(registeredClient.getClientId())
                    .build();
            authorizationBuilder
                    .attribute(OAuth2AuthorizationRequest.class.getName(), authReq);
            String id = String.valueOf(UUID.randomUUID());
            SessionInformation sessisonInfo = new SessionInformation(
                    user,
                    id,
                    Date.from(Instant.now())
            );
            tokenContext = tokenContextBuilder
                    .tokenType(OIdcHelpers.ID_TOKEN_TYPE_INSTANCE)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorization(authorizationBuilder.build())
                    // FUCKING SHIT
                    .put(SessionInformation.class, sessisonInfo)
                    // ^^^ ID token customizer may need access to the access token and/or refresh token
                    .build();
            // ---------------------------------------------------------------------------------------------------------

            OAuth2Token generatedIdToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedIdToken instanceof Jwt)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the ID token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }
            this.logger.trace("Generated id token");
            idToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
                    generatedIdToken.getExpiresAt(), ((Jwt) generatedIdToken).getClaims());
            authorizationBuilder.token(idToken, (metadata) -> {
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, idToken.getClaims());
                });

        } else {
            idToken = null;
        }

        // -----------------------------------------------
        // #4 Savbe authorization object
        // -----------------------------------------------
        OAuth2Authorization authorization = authorizationBuilder.build();
        this.authorizationService.save(authorization);
        this.logger.trace("Saved authorization");
        this.logger.trace("yeeeeeiii we authenticated a user!!!!");

        // -----------------------------------------------
        // #5 Add id_token to additionalParameters so it gets sent in the response
        // -----------------------------------------------
        Map<String, Object> additionalParameters = Collections.emptyMap();
        if(idToken != null) {
            additionalParameters = new HashMap<>();
            additionalParameters.put(OidcParameterNames.ID_TOKEN, idToken.getTokenValue());
        }
        if (this.logger.isTraceEnabled()){
            this.logger.trace("Authenticated token request");
            this.logger.trace("Yeeeeiiiiiii we authenticated a user");
        }
        return new OAuth2AccessTokenAuthenticationToken(
                registeredClient,
                clientAuthToken,
                accessToken,
                refreshToken,
                additionalParameters
        );
//         Generatedtokens ^^^^
//        generate access_token() -> done
//        generate refresh_token() -> done
//        generate id_token() -> done
//        scopes -> done
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EidteAuthentication.class.isAssignableFrom(authentication);
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
