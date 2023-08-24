package com.youngtechcr.www.security.eidte;

import com.youngtechcr.www.security.idp.IdentityProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class EidteAuthentication extends AbstractOAuth2TokenAuthenticationToken<OidcIdToken> {
    private OidcIdToken token;
//    private Authentication principal;
    private IdentityProvider identityProvider;
    private RegisteredClient registeredClient;
    private OAuth2ClientAuthenticationToken clientPrincipal;

    protected EidteAuthentication(
            OidcIdToken token,
            OAuth2ClientAuthenticationToken clientPrincipal,
            IdentityProvider identityProvider,
            RegisteredClient registeredClient
    ) {
        super(token, token, registeredClient.getClientSecret(), Collections.emptyList());
        this.token = token;
        this.clientPrincipal = clientPrincipal;
        this.identityProvider = identityProvider;
        this.registeredClient = registeredClient;
    }

    @Override
    public Map<String, Object> getTokenAttributes() {
        return this.token.getClaims();
    }
    public RegisteredClient getRegisteredClient() {
        return registeredClient;
    }
    public IdentityProvider getIdentityProvider() {
        return identityProvider;
    }
    public OAuth2ClientAuthenticationToken getClientPrincipal() { return this.clientPrincipal;}

}
