package com.youngtechcr.www.security.auth;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tbl_auth_client")
public class AuthClient {

    @Id
    @Column(name = "id_registered_client")
    private String id;

    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_id_issued_at")
    private Instant clientIdIssuedAt;

    @Column(name = "client_secret")
    private String clientSecret;
    @Column(name = "client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    @Column(name = "client_name")
    private String clientName;

    @Column(length = 1000, name = "client_authentication_methods")
    private String clientAuthenticationMethods;
    @Column(length = 1000, name = "authorization_grant_types")
    private String authorizationGrantTypes;

    @Column(length = 1000, name = "redirect_uris")
    private String redirectUris;
    @Column(length = 1000, name = "post_logout_redirect_uris")
    private String postLogoutRedirectUris;

    @Column(length = 1000)
    private String scopes;
    @Column(length = 1000, name = "client_settings")
    private String clientSettings;
    @Column(length = 1000, name = "token_settings")
    private String tokenSettings;

    public String getId() {
        return id;
    }

    public AuthClient setId(String id) {
        this.id = id;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public AuthClient setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public Instant getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }

    public AuthClient setClientIdIssuedAt(Instant clientIdIssuedAt) {
        this.clientIdIssuedAt = clientIdIssuedAt;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public AuthClient setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public Instant getClientSecretExpiresAt() {
        return clientSecretExpiresAt;
    }

    public AuthClient setClientSecretExpiresAt(Instant clientSecretExpiresAt) {
        this.clientSecretExpiresAt = clientSecretExpiresAt;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public AuthClient setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getClientAuthenticationMethods() {
        return clientAuthenticationMethods;
    }

    public AuthClient setClientAuthenticationMethods(String clientAuthenticationMethods) {
        this.clientAuthenticationMethods = clientAuthenticationMethods;
        return this;
    }

    public String getAuthorizationGrantTypes() {
        return authorizationGrantTypes;
    }

    public AuthClient setAuthorizationGrantTypes(String authorizationGrantTypes) {
        this.authorizationGrantTypes = authorizationGrantTypes;
        return this;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public AuthClient setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
        return this;
    }

    public String getScopes() {
        return scopes;
    }

    public AuthClient setScopes(String scopes) {
        this.scopes = scopes;
        return this;
    }

    public String getClientSettings() {
        return clientSettings;
    }

    public AuthClient setClientSettings(String clientSettings) {
        this.clientSettings = clientSettings;
        return this;
    }

    public String getTokenSettings() {
        return tokenSettings;
    }

    public AuthClient setTokenSettings(String tokenSettings) {
        this.tokenSettings = tokenSettings;
        return this;
    }

    public String getPostLogoutRedirectUris() {
        return postLogoutRedirectUris;
    }

    public AuthClient setPostLogoutRedirectUris(String postLogoutRedirectUris) {
        this.postLogoutRedirectUris = postLogoutRedirectUris;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthClient that = (AuthClient) o;
        return Objects.equals(id, that.id) && Objects.equals(clientId, that.clientId) && Objects.equals(clientIdIssuedAt, that.clientIdIssuedAt) && Objects.equals(clientSecret, that.clientSecret) && Objects.equals(clientSecretExpiresAt, that.clientSecretExpiresAt) && Objects.equals(clientName, that.clientName) && Objects.equals(clientAuthenticationMethods, that.clientAuthenticationMethods) && Objects.equals(authorizationGrantTypes, that.authorizationGrantTypes) && Objects.equals(redirectUris, that.redirectUris) && Objects.equals(postLogoutRedirectUris, that.postLogoutRedirectUris) && Objects.equals(scopes, that.scopes) && Objects.equals(clientSettings, that.clientSettings) && Objects.equals(tokenSettings, that.tokenSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, clientIdIssuedAt, clientSecret, clientSecretExpiresAt, clientName, clientAuthenticationMethods, authorizationGrantTypes, redirectUris, postLogoutRedirectUris, scopes, clientSettings, tokenSettings);
    }

    @Override
    public String toString() {
        return "AuthClient{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientIdIssuedAt=" + clientIdIssuedAt +
                ", clientSecret='" + clientSecret + '\'' +
                ", clientSecretExpiresAt=" + clientSecretExpiresAt +
                ", clientName='" + clientName + '\'' +
                ", clientAuthenticationMethods='" + clientAuthenticationMethods + '\'' +
                ", authorizationGrantTypes='" + authorizationGrantTypes + '\'' +
                ", redirectUris='" + redirectUris + '\'' +
                ", postLogoutRedirectUris='" + postLogoutRedirectUris + '\'' +
                ", scopes='" + scopes + '\'' +
                ", clientSettings='" + clientSettings + '\'' +
                ", tokenSettings='" + tokenSettings + '\'' +
                '}';
    }
}
