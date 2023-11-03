package com.youngtechcr.www.security.emailpasswd;

import com.youngtechcr.www.security.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

public class EmailPasswdAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private static final Logger logger = LoggerFactory.getLogger(EmailPasswdAuthenticationProvider.class);
    public EmailPasswdAuthenticationProvider(
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
        logger.trace("Starting email/passwd authenication");

        // #1 Retrieve objs
        EmailPasswdAuthentication emailPasswdAuthentication =
                (EmailPasswdAuthentication) authentication;

        /* #2 Check if user exists
            - TRUE:
                check if passwd has same digest with stored in DB
            - FALSE:
                register new user

        */
//        this.userService


        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailPasswdAuthentication.class.isAssignableFrom(authentication);
    }
}
