package com.youngtechcr.www.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthAuthorizationConsentRepository extends
        JpaRepository<AuthAuthorizationConsent, AuthAuthorizationConsent.AuthorizationConsentId> {
    Optional<AuthAuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
