package com.youngtechcr.www.security.auth;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tbl_auth_authorization_consent")
@IdClass(AuthAuthorizationConsent.AuthorizationConsentId.class)
public class AuthAuthorizationConsent {

    @Id
    @Column(name = "id_registered_client")
    private String registeredClientId;
    @Id
    @Column(name = "principal_name")
    private String principalName;
    @Column(length = 1000)
    private String authorities;

    public String getRegisteredClientId() {
        return registeredClientId;
    }

    public AuthAuthorizationConsent setRegisteredClientId(String registeredClientId) {
        this.registeredClientId = registeredClientId;
        return this;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public AuthAuthorizationConsent setPrincipalName(String principalName) {
        this.principalName = principalName;
        return this;
    }

    public String getAuthorities() {
        return authorities;
    }

    public AuthAuthorizationConsent setAuthorities(String authorities) {
        this.authorities = authorities;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthAuthorizationConsent that = (AuthAuthorizationConsent) o;

        if (!Objects.equals(registeredClientId, that.registeredClientId))
            return false;
        if (!Objects.equals(principalName, that.principalName))
            return false;
        return Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        int result = registeredClientId != null ? registeredClientId.hashCode() : 0;
        result = 31 * result + (principalName != null ? principalName.hashCode() : 0);
        result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthAuthorizationConsent{" +
                "idRegisteredClient='" + registeredClientId + '\'' +
                ", principalName='" + principalName + '\'' +
                ", authorities='" + authorities + '\'' +
                '}';
    }

    public static class AuthorizationConsentId implements Serializable {
        private String registeredClientId;
        private String principalName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AuthorizationConsentId that = (AuthorizationConsentId) o;
            return registeredClientId.equals(that.registeredClientId) && principalName.equals(that.principalName);
        }
        @Override
        public int hashCode() {
            return Objects.hash(registeredClientId, principalName);
        }
    }
}

