package com.youngtechcr.www.security.emailpasswd;

import com.youngtechcr.www.http.HttpUtils;
import com.youngtechcr.www.security.eidte.CommonOAuthRequestParams;
import com.youngtechcr.www.security.eidte.EidteAuthenticationConverter;
import com.youngtechcr.www.security.idp.IdentityProviderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

public class EmailPasswdAuthenticationConverter implements AuthenticationConverter {

    private static final Logger logger = LoggerFactory.getLogger(EidteAuthenticationConverter.class);
    private final RegisteredClientRepository registeredClientRepository;

    public EmailPasswdAuthenticationConverter(
            RegisteredClientRepository registeredClientRepository
    ) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        MultiValueMap<String, String> httpParams = HttpUtils.getParameters(request);

        // #1 Check if request is a Psswd authn req
        String grantType = httpParams.getFirst(OAuth2ParameterNames.GRANT_TYPE);
        if(!EmailPasswdParameters.GRANT_TYPE_VALUE.equals(grantType)) {
            return null;
        }
        // #2 Check for client credentials
        String cliendId = httpParams.getFirst(CommonOAuthRequestParams.CLIENT_ID);
        String clientSecret = httpParams.getFirst(CommonOAuthRequestParams.CLIENT_SECRET);
        if (cliendId == null || clientSecret == null) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
        }

        //#3 Get email/password
        String email = httpParams.getFirst(EmailPasswdParameters.EMAIL);
        String passwd = httpParams.getFirst(EmailPasswdParameters.PASSWD);
        if(!StringUtils.hasText(email) || !StringUtils.hasText(passwd)) {
            throw new OAuth2AuthenticationException(
                    OAuth2ErrorCodes.INVALID_REQUEST
            );
        }

        return new EmailPasswdAuthentication(
                email,
                passwd
        );
    }
}
