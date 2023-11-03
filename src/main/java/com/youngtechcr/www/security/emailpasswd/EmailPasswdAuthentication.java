package com.youngtechcr.www.security.emailpasswd;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class EmailPasswdAuthentication implements Authentication {

    private String email;
    private String passwd;
    private boolean authenticated;
    private Collection<? extends GrantedAuthority> authorities
            = Collections.emptyList();
    public EmailPasswdAuthentication(String email, String paswd) {
        this.email = email;
        this.passwd = paswd;
    }

    public EmailPasswdAuthentication(
            String email,
            String paswd,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.email = email;
        this.passwd = paswd;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return this.passwd;
    }

    @Override
    public Object getDetails() {
        return this.email;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return this.email;
    }
}
