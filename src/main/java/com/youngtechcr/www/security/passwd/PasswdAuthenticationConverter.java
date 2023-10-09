package com.youngtechcr.www.security.passwd;

import com.youngtechcr.www.security.eidte.EidteAuthenticationConverter;
import com.youngtechcr.www.security.idp.IdentityProviderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class PasswdAuthenticationConverter implements AuthenticationConverter {

    private static final Logger logger = LoggerFactory.getLogger(EidteAuthenticationConverter.class);
    private final RegisteredClientRepository registeredClientRepository;
    private final IdentityProviderRepository idpRepository;

    public PasswdAuthenticationConverter(
            RegisteredClientRepository registeredClientRepository,
            IdentityProviderRepository idpRepository
    ) {
        this.registeredClientRepository = registeredClientRepository;
        this.idpRepository = idpRepository;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        return null;
    }
}
