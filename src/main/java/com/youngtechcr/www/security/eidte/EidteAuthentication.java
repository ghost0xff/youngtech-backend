package com.youngtechcr.www.security.eidte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;

public class EidteAuthentication extends AbstractOAuth2TokenAuthenticationToken<OidcIdToken> {
    private static final Logger logger = LoggerFactory.getLogger(EidteAuthentication.class);
    private OAuth2ClientAuthenticationToken principal;
    private OidcIdToken token;

    private EidteAuthentication(
            OidcIdToken token,
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(token, principal, credentials, authorities);
        this.token = token;
        this.principal = (OAuth2ClientAuthenticationToken) principal;
    }

    @Override
    public Map<String, Object> getTokenAttributes() {
        return this.token.getClaims();
    }

    public OAuth2ClientAuthenticationToken getPrincipal() {
        return this.principal;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private OAuth2ClientAuthenticationToken principal;
        private OidcIdToken token;
        private Object credentials;
        private Collection<? extends GrantedAuthority> authorities;

        public Builder principal(OAuth2ClientAuthenticationToken principal) {
            this.principal = principal;
            return this;
        }

        public Builder token(OidcIdToken token) {
            this.token = token;
            return this;
        }

        public Builder credentials(Object credentials) {
            this.credentials = credentials;
            return this;
        }

        public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public EidteAuthentication build() {
           Assert.notNull(this.token, "eidteToken can't be null");
           Assert.notNull(this.principal, "principal can't be null");
           return new EidteAuthentication(
                  this.token,
                  this.principal,
                  this.credentials,
                  this.authorities
           );
       }

    }

}
