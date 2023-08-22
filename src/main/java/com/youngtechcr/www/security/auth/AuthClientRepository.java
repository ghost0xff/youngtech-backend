package com.youngtechcr.www.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthClientRepository extends JpaRepository<AuthClient, String> {
    Optional<AuthClient > findByClientId(String clientId);

}
