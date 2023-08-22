package com.youngtechcr.www.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthAuthorizationRepository
        extends JpaRepository<AuthAuthorization, String> {
    Optional<AuthAuthorization> findByState(String state);
    Optional<AuthAuthorization> findByAuthorizationCodeValue(String authorizationCode);
    Optional<AuthAuthorization> findByAccessTokenValue(String accessToken);
    Optional<AuthAuthorization> findByRefreshTokenValue(String refreshToken);
    Optional<AuthAuthorization> findByOidcIdTokenValue(String idToken);
    Optional<AuthAuthorization> findByUserCodeValue(String userCode);
    Optional<AuthAuthorization> findByDeviceCodeValue(String deviceCode);
    @Query("select a from AuthAuthorization a where a.state = :token" +
            " or a.authorizationCodeValue = :token" +
            " or a.accessTokenValue = :token" +
            " or a.refreshTokenValue = :token" +
            " or a.oidcIdTokenValue = :token" +
            " or a.userCodeValue = :token" +
            " or a.deviceCodeValue = :token"
    )
    Optional<AuthAuthorization>
    findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(@Param("token") String token);
}
