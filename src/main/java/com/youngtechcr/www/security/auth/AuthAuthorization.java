package com.youngtechcr.www.security.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tbl_auth_authorization")
public class AuthAuthorization {
    @Id
    @Column(name = "id_authorization")
    private String id;

    @Column(name = "id_registered_client")
    private String registeredClientId;
    @Column(name = "principal_name")
    private String principalName;

    @Column(name = "authorization_grant_type")
    private String authorizationGrantType;
    @Column(length = 1000, name = "authorized_scopes")
    private String authorizedScopes;
    @Column(length = 2000)
    private String attributes;

    @Column(length = 500)
    private String state;
    @Column(length = 1000, name = "authorization_code_value")
    private String authorizationCodeValue;
    @Column(name = "authorization_code_issued_at")
    private Instant authorizationCodeIssuedAt;
    @Column(name = "authorization_code_expires_at")
    private Instant authorizationCodeExpiresAt;
    @Column(length = 1000, name = "authorization_code_metadata")
    private String authorizationCodeMetadata;

    @Column(length = 1000, name = "access_token_value")
    private String accessTokenValue;
    @Column(name = "access_token_issued_at")
    private Instant accessTokenIssuedAt;
    @Column(name = "access_token_expires_at")
    private Instant accessTokenExpiresAt;
    @Column(length = 1000, name = "access_token_metadata")
    private String accessTokenMetadata;
    @Column(name = "access_token_type")
    private String accessTokenType;
    @Column(length = 1000, name = "access_token_scopes")
    private String accessTokenScopes;

    @Column(length = 1000, name = "refresh_token_value")
    private String refreshTokenValue;
    @Column(name = "refresh_token_issued_at")
    private Instant refreshTokenIssuedAt;
    @Column(name = "refresh_token_expires_at")
    private Instant refreshTokenExpiresAt;
    @Column(length = 1000, name = "refresh_token_metadata")
    private String refreshTokenMetadata;

    @Column(length = 1000, name = "oidc_id_token_value")
    private String oidcIdTokenValue;
    @Column(name = "oidc_id_token_issued_at")
    private Instant oidcIdTokenIssuedAt;
    @Column(name = "oidc_id_token_expires_at")
    private Instant oidcIdTokenExpiresAt;
    @Column(length = 1000, name = "oidc_id_token_metadata")
    private String oidcIdTokenMetadata;
    @Column(length = 1000, name = "oidc_id_token_claims")
    private String oidcIdTokenClaims;

    @Column(length = 1000, name = "user_code_value")
    private String userCodeValue;
    @Column(name = "user_code_issued_at")
    private Instant userCodeIssuedAt;
    @Column(name = "user_code_expires_at")
    private Instant userCodeExpiresAt;
    @Column(length = 1000, name = "user_code_metadata")
    private String userCodeMetadata;

    @Column(length = 1000, name = "device_code_value")
    private String deviceCodeValue;
    @Column(name = "device_code_issued_at")
    private Instant deviceCodeIssuedAt;
    @Column(name = "device_code_expires_at")
    private Instant deviceCodeExpiresAt;
    @Column(length = 1000, name = "device_code_metadata")
    private String deviceCodeMetadata;

    public String getId() {
        return id;
    }

    public AuthAuthorization setId(String id) {
        this.id = id;
        return this;
    }

    public String getRegisteredClientId() {
        return registeredClientId;
    }

    public AuthAuthorization setRegisteredClientId(String registeredClientId) {
        this.registeredClientId = registeredClientId;
        return this;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public AuthAuthorization setPrincipalName(String principalName) {
        this.principalName = principalName;
        return this;
    }

    public String getAuthorizationGrantType() {
        return authorizationGrantType;
    }

    public AuthAuthorization setAuthorizationGrantType(String authorizationGrantType) {
        this.authorizationGrantType = authorizationGrantType;
        return this;
    }

    public String getAuthorizedScopes() {
        return authorizedScopes;
    }

    public AuthAuthorization setAuthorizedScopes(String authorizedScopes) {
        this.authorizedScopes = authorizedScopes;
        return this;
    }

    public String getAttributes() {
        return attributes;
    }

    public AuthAuthorization setAttributes(String attributes) {
        this.attributes = attributes;
        return this;
    }

    public String getState() {
        return state;
    }

    public AuthAuthorization setState(String state) {
        this.state = state;
        return this;
    }

    public String getAuthorizationCodeValue() {
        return authorizationCodeValue;
    }

    public AuthAuthorization setAuthorizationCodeValue(String authorizationCodeValue) {
        this.authorizationCodeValue = authorizationCodeValue;
        return this;
    }

    public Instant getAuthorizationCodeIssuedAt() {
        return authorizationCodeIssuedAt;
    }

    public AuthAuthorization setAuthorizationCodeIssuedAt(Instant authorizationCodeIssuedAt) {
        this.authorizationCodeIssuedAt = authorizationCodeIssuedAt;
        return this;
    }

    public Instant getAuthorizationCodeExpiresAt() {
        return authorizationCodeExpiresAt;
    }

    public AuthAuthorization setAuthorizationCodeExpiresAt(Instant authorizationCodeExpiresAt) {
        this.authorizationCodeExpiresAt = authorizationCodeExpiresAt;
        return this;
    }

    public String getAuthorizationCodeMetadata() {
        return authorizationCodeMetadata;
    }

    public AuthAuthorization setAuthorizationCodeMetadata(String authorizationCodeMetadata) {
        this.authorizationCodeMetadata = authorizationCodeMetadata;
        return this;
    }

    public String getAccessTokenValue() {
        return accessTokenValue;
    }

    public AuthAuthorization setAccessTokenValue(String accessTokenValue) {
        this.accessTokenValue = accessTokenValue;
        return this;
    }

    public Instant getAccessTokenIssuedAt() {
        return accessTokenIssuedAt;
    }

    public AuthAuthorization setAccessTokenIssuedAt(Instant accessTokenIssuedAt) {
        this.accessTokenIssuedAt = accessTokenIssuedAt;
        return this;
    }

    public Instant getAccessTokenExpiresAt() {
        return accessTokenExpiresAt;
    }

    public AuthAuthorization setAccessTokenExpiresAt(Instant accessTokenExpiresAt) {
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        return this;
    }

    public String getAccessTokenMetadata() {
        return accessTokenMetadata;
    }

    public AuthAuthorization setAccessTokenMetadata(String accessTokenMetadata) {
        this.accessTokenMetadata = accessTokenMetadata;
        return this;
    }

    public String getAccessTokenType() {
        return accessTokenType;
    }

    public AuthAuthorization setAccessTokenType(String accessTokenType) {
        this.accessTokenType = accessTokenType;
        return this;
    }

    public String getAccessTokenScopes() {
        return accessTokenScopes;
    }

    public AuthAuthorization setAccessTokenScopes(String accessTokenScopes) {
        this.accessTokenScopes = accessTokenScopes;
        return this;
    }

    public String getRefreshTokenValue() {
        return refreshTokenValue;
    }

    public AuthAuthorization setRefreshTokenValue(String refreshTokenValue) {
        this.refreshTokenValue = refreshTokenValue;
        return this;
    }

    public Instant getRefreshTokenIssuedAt() {
        return refreshTokenIssuedAt;
    }

    public AuthAuthorization setRefreshTokenIssuedAt(Instant refreshTokenIssuedAt) {
        this.refreshTokenIssuedAt = refreshTokenIssuedAt;
        return this;
    }

    public Instant getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    public AuthAuthorization setRefreshTokenExpiresAt(Instant refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
        return this;
    }

    public String getRefreshTokenMetadata() {
        return refreshTokenMetadata;
    }

    public AuthAuthorization setRefreshTokenMetadata(String refreshTokenMetadata) {
        this.refreshTokenMetadata = refreshTokenMetadata;
        return this;
    }

    public String getOidcIdTokenValue() {
        return oidcIdTokenValue;
    }

    public AuthAuthorization setOidcIdTokenValue(String oidcIdTokenValue) {
        this.oidcIdTokenValue = oidcIdTokenValue;
        return this;
    }

    public Instant getOidcIdTokenIssuedAt() {
        return oidcIdTokenIssuedAt;
    }

    public AuthAuthorization setOidcIdTokenIssuedAt(Instant oidcIdTokenIssuedAt) {
        this.oidcIdTokenIssuedAt = oidcIdTokenIssuedAt;
        return this;
    }

    public Instant getOidcIdTokenExpiresAt() {
        return oidcIdTokenExpiresAt;
    }

    public AuthAuthorization setOidcIdTokenExpiresAt(Instant oidcIdTokenExpiresAt) {
        this.oidcIdTokenExpiresAt = oidcIdTokenExpiresAt;
        return this;
    }

    public String getOidcIdTokenMetadata() {
        return oidcIdTokenMetadata;
    }

    public AuthAuthorization setOidcIdTokenMetadata(String oidcIdTokenMetadata) {
        this.oidcIdTokenMetadata = oidcIdTokenMetadata;
        return this;
    }

    public String getOidcIdTokenClaims() {
        return oidcIdTokenClaims;
    }

    public AuthAuthorization setOidcIdTokenClaims(String oidcIdTokenClaims) {
        this.oidcIdTokenClaims = oidcIdTokenClaims;
        return this;
    }

    public String getUserCodeValue() {
        return userCodeValue;
    }

    public AuthAuthorization setUserCodeValue(String userCodeValue) {
        this.userCodeValue = userCodeValue;
        return this;
    }

    public Instant getUserCodeIssuedAt() {
        return userCodeIssuedAt;
    }

    public AuthAuthorization setUserCodeIssuedAt(Instant userCodeIssuedAt) {
        this.userCodeIssuedAt = userCodeIssuedAt;
        return this;
    }

    public Instant getUserCodeExpiresAt() {
        return userCodeExpiresAt;
    }

    public AuthAuthorization setUserCodeExpiresAt(Instant userCodeExpiresAt) {
        this.userCodeExpiresAt = userCodeExpiresAt;
        return this;
    }

    public String getUserCodeMetadata() {
        return userCodeMetadata;
    }

    public AuthAuthorization setUserCodeMetadata(String userCodeMetadata) {
        this.userCodeMetadata = userCodeMetadata;
        return this;
    }

    public String getDeviceCodeValue() {
        return deviceCodeValue;
    }

    public AuthAuthorization setDeviceCodeValue(String deviceCodeValue) {
        this.deviceCodeValue = deviceCodeValue;
        return this;
    }

    public Instant getDeviceCodeIssuedAt() {
        return deviceCodeIssuedAt;
    }

    public AuthAuthorization setDeviceCodeIssuedAt(Instant deviceCodeIssuedAt) {
        this.deviceCodeIssuedAt = deviceCodeIssuedAt;
        return this;
    }

    public Instant getDeviceCodeExpiresAt() {
        return deviceCodeExpiresAt;
    }

    public AuthAuthorization setDeviceCodeExpiresAt(Instant deviceCodeExpiresAt) {
        this.deviceCodeExpiresAt = deviceCodeExpiresAt;
        return this;
    }

    public String getDeviceCodeMetadata() {
        return deviceCodeMetadata;
    }

    public AuthAuthorization setDeviceCodeMetadata(String deviceCodeMetadata) {
        this.deviceCodeMetadata = deviceCodeMetadata;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthAuthorization that = (AuthAuthorization) o;
        return Objects.equals(id, that.id) && Objects.equals(registeredClientId, that.registeredClientId) && Objects.equals(principalName, that.principalName) && Objects.equals(authorizationGrantType, that.authorizationGrantType) && Objects.equals(authorizedScopes, that.authorizedScopes) && Objects.equals(attributes, that.attributes) && Objects.equals(state, that.state) && Objects.equals(authorizationCodeValue, that.authorizationCodeValue) && Objects.equals(authorizationCodeIssuedAt, that.authorizationCodeIssuedAt) && Objects.equals(authorizationCodeExpiresAt, that.authorizationCodeExpiresAt) && Objects.equals(authorizationCodeMetadata, that.authorizationCodeMetadata) && Objects.equals(accessTokenValue, that.accessTokenValue) && Objects.equals(accessTokenIssuedAt, that.accessTokenIssuedAt) && Objects.equals(accessTokenExpiresAt, that.accessTokenExpiresAt) && Objects.equals(accessTokenMetadata, that.accessTokenMetadata) && Objects.equals(accessTokenType, that.accessTokenType) && Objects.equals(accessTokenScopes, that.accessTokenScopes) && Objects.equals(refreshTokenValue, that.refreshTokenValue) && Objects.equals(refreshTokenIssuedAt, that.refreshTokenIssuedAt) && Objects.equals(refreshTokenExpiresAt, that.refreshTokenExpiresAt) && Objects.equals(refreshTokenMetadata, that.refreshTokenMetadata) && Objects.equals(oidcIdTokenValue, that.oidcIdTokenValue) && Objects.equals(oidcIdTokenIssuedAt, that.oidcIdTokenIssuedAt) && Objects.equals(oidcIdTokenExpiresAt, that.oidcIdTokenExpiresAt) && Objects.equals(oidcIdTokenMetadata, that.oidcIdTokenMetadata) && Objects.equals(oidcIdTokenClaims, that.oidcIdTokenClaims) && Objects.equals(userCodeValue, that.userCodeValue) && Objects.equals(userCodeIssuedAt, that.userCodeIssuedAt) && Objects.equals(userCodeExpiresAt, that.userCodeExpiresAt) && Objects.equals(userCodeMetadata, that.userCodeMetadata) && Objects.equals(deviceCodeValue, that.deviceCodeValue) && Objects.equals(deviceCodeIssuedAt, that.deviceCodeIssuedAt) && Objects.equals(deviceCodeExpiresAt, that.deviceCodeExpiresAt) && Objects.equals(deviceCodeMetadata, that.deviceCodeMetadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registeredClientId, principalName, authorizationGrantType, authorizedScopes, attributes, state, authorizationCodeValue, authorizationCodeIssuedAt, authorizationCodeExpiresAt, authorizationCodeMetadata, accessTokenValue, accessTokenIssuedAt, accessTokenExpiresAt, accessTokenMetadata, accessTokenType, accessTokenScopes, refreshTokenValue, refreshTokenIssuedAt, refreshTokenExpiresAt, refreshTokenMetadata, oidcIdTokenValue, oidcIdTokenIssuedAt, oidcIdTokenExpiresAt, oidcIdTokenMetadata, oidcIdTokenClaims, userCodeValue, userCodeIssuedAt, userCodeExpiresAt, userCodeMetadata, deviceCodeValue, deviceCodeIssuedAt, deviceCodeExpiresAt, deviceCodeMetadata);
    }

    @Override
    public String toString() {
        return "AuthAuthorization{" +
                "id='" + id + '\'' +
                ", registeredClientId='" + registeredClientId + '\'' +
                ", principalName='" + principalName + '\'' +
                ", authorizationGrantType='" + authorizationGrantType + '\'' +
                ", authorizedScopes='" + authorizedScopes + '\'' +
                ", attributes='" + attributes + '\'' +
                ", state='" + state + '\'' +
                ", authorizationCodeValue='" + authorizationCodeValue + '\'' +
                ", authorizationCodeIssuedAt=" + authorizationCodeIssuedAt +
                ", authorizationCodeExpiresAt=" + authorizationCodeExpiresAt +
                ", authorizationCodeMetadata='" + authorizationCodeMetadata + '\'' +
                ", accessTokenValue='" + accessTokenValue + '\'' +
                ", accessTokenIssuedAt=" + accessTokenIssuedAt +
                ", accessTokenExpiresAt=" + accessTokenExpiresAt +
                ", accessTokenMetadata='" + accessTokenMetadata + '\'' +
                ", accessTokenType='" + accessTokenType + '\'' +
                ", accessTokenScopes='" + accessTokenScopes + '\'' +
                ", refreshTokenValue='" + refreshTokenValue + '\'' +
                ", refreshTokenIssuedAt=" + refreshTokenIssuedAt +
                ", refreshTokenExpiresAt=" + refreshTokenExpiresAt +
                ", refreshTokenMetadata='" + refreshTokenMetadata + '\'' +
                ", oidcIdTokenValue='" + oidcIdTokenValue + '\'' +
                ", oidcIdTokenIssuedAt=" + oidcIdTokenIssuedAt +
                ", oidcIdTokenExpiresAt=" + oidcIdTokenExpiresAt +
                ", oidcIdTokenMetadata='" + oidcIdTokenMetadata + '\'' +
                ", oidcIdTokenClaims='" + oidcIdTokenClaims + '\'' +
                ", userCodeValue='" + userCodeValue + '\'' +
                ", userCodeIssuedAt=" + userCodeIssuedAt +
                ", userCodeExpiresAt=" + userCodeExpiresAt +
                ", userCodeMetadata='" + userCodeMetadata + '\'' +
                ", deviceCodeValue='" + deviceCodeValue + '\'' +
                ", deviceCodeIssuedAt=" + deviceCodeIssuedAt +
                ", deviceCodeExpiresAt=" + deviceCodeExpiresAt +
                ", deviceCodeMetadata='" + deviceCodeMetadata + '\'' +
                '}';
    }
}
