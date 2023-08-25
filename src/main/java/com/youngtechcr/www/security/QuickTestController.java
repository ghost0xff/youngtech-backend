package com.youngtechcr.www.security;

import com.youngtechcr.www.security.auth.JpaRegisteredClientRepository;
import com.youngtechcr.www.security.eidte.EidteParameters;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.jpa.domain.AbstractAuditable_;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/test")
public class QuickTestController {

    private final JpaRegisteredClientRepository clientRepo;
    public QuickTestController(JpaRegisteredClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    @GetMapping
    public ResponseEntity<?> viewClientRepoOperations(
            Principal principal
    ) {
        return ResponseEntity.ok(principal);
    }
//
//    @PostMapping
//    public ResponseEntity<?> registerWebClient() {
//        String id = UUID.randomUUID().toString();
//        TokenSettings tokenSettings = TokenSettings
//                .builder()
//                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
//                .accessTokenTimeToLive(Duration.ofMinutes(15))
//                .refreshTokenTimeToLive(Duration.ofMinutes(30))
//                .build();
//       RegisteredClient registeredClient = RegisteredClient
//               .withId(id)
//               .clientIdIssuedAt(Instant.now())
//               .clientName("YoungTech's E-commerce Web Application")
//               .clientId("young-tech-web")
//               .clientSecret("{noop}secret")
//               .scopes( scopes -> {
//                   scopes.add(OidcScopes.OPENID);
//                   scopes.add(OidcScopes.PROFILE);
//                   scopes.add(OidcScopes.EMAIL);
//                   scopes.add(OidcScopes.ADDRESS);
//                   scopes.add(OidcScopes.ADDRESS);
//                   scopes.add(OidcScopes.PHONE);
//                   scopes.add("all");
//               })
//               .tokenSettings(tokenSettings)
//               .clientAuthenticationMethods( methods -> {
//                   methods.add(ClientAuthenticationMethod.CLIENT_SECRET_POST);
//                   methods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
//               })
//               .authorizationGrantTypes( grantTypes -> {
//                   grantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
//                   grantTypes.add(EidteParameters.GRANT_TYPE_INSTANCE);
//               })
//               .build();
//
//       clientRepo.save(registeredClient);
//       return ResponseEntity.ok(registeredClient);
//    }
}
