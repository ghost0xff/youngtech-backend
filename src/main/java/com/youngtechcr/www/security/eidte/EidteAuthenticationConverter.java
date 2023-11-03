package com.youngtechcr.www.security.eidte;

import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.http.HttpUtils;
import com.youngtechcr.www.security.SecurityConfig;
import com.youngtechcr.www.security.eidte.google.EidteGoogleVerifier;
import com.youngtechcr.www.security.idp.IdentityProvider;
import com.youngtechcr.www.security.idp.IdentityProviderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.ietf.jgss.Oid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class EidteAuthenticationConverter implements AuthenticationConverter {

    private static final Logger logger = LoggerFactory.getLogger(EidteAuthenticationConverter.class);
    private final RegisteredClientRepository registeredClientRepository;
    private final IdentityProviderRepository idpRepository;
    private static final Authentication ANONYMOUS_AUTHENTICATION =
            new AnonymousAuthenticationToken(
                    "anonymous",
                    "anonymousUser",
                    AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
            );
    public EidteAuthenticationConverter(
            RegisteredClientRepository registeredClientRepository,
            IdentityProviderRepository idpRepository) {
        this.registeredClientRepository = registeredClientRepository;
        this.idpRepository = idpRepository;
    }


    @Override
    public Authentication convert(HttpServletRequest request) {
        MultiValueMap<String, String> httpParams = HttpUtils.getParameters(request);

        //#1 Check if request is a EIDTE Exchange
        String grantType = httpParams.getFirst(OAuth2ParameterNames.GRANT_TYPE);
        if (!EidteParameters.GRANT_TYPE_VALUE.equals(grantType)) {
            return null;
        }

        // #2 Check for client credentials, because we don't want no
        // script kiddie messing around with this awesome authz server
        // NOTE: ^^^ client authentication is already done at this point
        // so checking for credentials is useless BUT I'm gonna leave it
        // just because yes (a spring filter authenticates this :v)
        String cliendId = httpParams.getFirst(CommonOAuthRequestParams.CLIENT_ID);
        String clientSecret = httpParams.getFirst(CommonOAuthRequestParams.CLIENT_SECRET);
        if (cliendId == null || clientSecret == null) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
        }

        // #2.5 Retrieve registeredClient from repo because we gonna use it above
        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(cliendId);
        if (registeredClient == null) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
        }
        // #2.7 create client auth token
        var clientAuthToken = new OAuth2ClientAuthenticationToken(
                registeredClient,
                ClientAuthenticationMethod.CLIENT_SECRET_POST,
                clientSecret
        );
        clientAuthToken.setAuthenticated(true); // because at this point the Client is actually authn

        //#3 Check if mf sent the mf jwt mf
        String eidteValue = httpParams.getFirst(OAuth2ParameterNames.ASSERTION);
        if (eidteValue == null || !StringUtils.hasText(eidteValue)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_TOKEN);
        }

        //#4 Verify legitimacy of EIDTE Token with specified provider
        String eidteIpName = httpParams.getFirst(EidteParameters.IDPN);
        IdentityProvider identityProvider = this
                .idpRepository
                .findByName(eidteIpName)
                .orElseThrow(() -> {
                    return new OAuth2AuthenticationException(EidteErrorCodes.UNKNOWN_IPDN);
                });
       /*
            TODO: This is hardcoded at the moment, build the API to
                not have to do this and instead have some type of
                EidteManagerVerifier/EideteDelegatingVerifier that
                tries to verify tokens according to a list of
                providede IdentityProviders and a given IDPN (identity
                provider name)

       */
        Optional<OidcIdToken> eidteToken = Optional.empty();
        if (identityProvider.getName().equals("google")) {
            eidteToken = new EidteGoogleVerifier().verify(eidteValue);
        } else {
            throw new OAuth2AuthenticationException(EidteErrorCodes.UNKNOWN_IPDN);
        }


        // #5 return the mf authentication instance :'v
        return new EidteAuthentication(
                eidteToken.orElseThrow(() -> {
                    logger.debug(HttpErrorMessages.FAILED_GENERATE_TOKEN);
                    return new OAuth2AuthenticationException(
                            OAuth2ErrorCodes.SERVER_ERROR
                    );
                }),
                clientAuthToken,
                identityProvider,
                registeredClient

        );
    }

}
