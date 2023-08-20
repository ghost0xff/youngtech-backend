package com.youngtechcr.www.security.eidte;

import com.nimbusds.jose.proc.SecurityContext;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.security.CommonOAuthRequestParams;
import com.youngtechcr.www.security.eidte.google.EidteGoogleVerifier;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class EidteAuthenticationConverter implements AuthenticationConverter {

    private static final Logger logger = LoggerFactory.getLogger(
            EidteAuthenticationConverter.class);

    @Override
    public Authentication convert(HttpServletRequest request) {
       MultiValueMap<String, String> httpParams =  getParameters(request);

       //#1 Check if request is a EIDT Exchange
        String grantType = httpParams.getFirst(OAuth2ParameterNames.GRANT_TYPE);
       if(!EidteParameters.GRANT_TYPE_VALUE.equals(grantType)) {
           return null;
       }

       // #2 Check for client credentials, because we don't want no
       // script kiddie messing around with this awsome authz server
       String cliendId = httpParams.getFirst(CommonOAuthRequestParams.CLIENT_ID);
       String clientSecret = httpParams.getFirst(CommonOAuthRequestParams.CLIENT_SECRET);
       if(cliendId == null || clientSecret == null) {
           logger.warn("cliendId -> "  + cliendId);
           logger.warn("cliendSecret -> "  + clientSecret);
           logger.warn("no client_secret provided >;v");
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
       }
       //#3 Check if mf sent the mf jwt mf
        String eidteValue = httpParams.getFirst(OAuth2ParameterNames.ASSERTION);
        if(!StringUtils.hasText(eidteValue)){
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_TOKEN);
        }

        //#4 Verify legitimacy of EIDTE Token with specified provider
       String eidteIpName = httpParams.getFirst(EidteParameters.IPN);
       Optional<OidcIdToken> eidteToken = Optional.empty();
       if(eidteIpName.equals("google")) { // hard coded because I just can mf mf >;v
           eidteToken = new EidteGoogleVerifier().verify(eidteValue);
       }

       RegisteredClient client = RegisteredClient
               .withId("1")
               .clientId(cliendId)
               .clientSecret(clientSecret)
               .authorizationGrantType(EidteParameters.GRANT_TYPE_INSTANCE)
               .build();

       var principal = new OAuth2ClientAuthenticationToken(
               client,
               ClientAuthenticationMethod.NONE,
               null
       );

       principal.setAuthenticated(true);

       // #5 return the mf authentication instance
       return EidteAuthentication
               .builder()
               .token(
                  eidteToken.orElseThrow( () -> {
                       logger.debug(HttpErrorMessages.FAILED_GENERATE_TOKEN);
                       return new OAuth2AuthenticationException(
                               OAuth2ErrorCodes.SERVER_ERROR
                       );
                   })
              )
              .principal(principal)
              .credentials(clientSecret)
              .authorities(Collections.emptyList())
              .build();
     }



     private static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            if (values.length > 0) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }
        });
        return parameters;
    }
}

