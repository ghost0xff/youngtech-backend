package com.youngtechcr.www.security;

import com.youngtechcr.www.security.auth.JpaRegisteredClientRepository;
import com.youngtechcr.www.security.eidte.EidteParameters;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/test")
public class QuickTestController {

    private final JpaRegisteredClientRepository clientRepo;
    private final OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer;
    private final SessionRegistry sessionRegistry;
    public QuickTestController(
            JpaRegisteredClientRepository clientRepo,
            OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer, SessionRegistry sessionRegistry){
        this.clientRepo = clientRepo;
        this.tokenCustomizer = tokenCustomizer;
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping
    @RequestMapping(path = "/sessions")
    public ResponseEntity<?> viewCurrentSessionRegistery() {
        return ResponseEntity.ok(String.valueOf(sessionRegistry));
    }


    @GetMapping
    @RequestMapping(path = "/customizer")
    public ResponseEntity<?> viewCurrentTokenCustomizer() {

        String rs = null;

//        rs = tokenCustomizer.toString();
//        tokenCustomizer.
        return ResponseEntity.ok(rs);
    }

    @GetMapping
    @RequestMapping(path = "/principal")
    public ResponseEntity<?> viewPrincipal(Principal principal) {
        return ResponseEntity.ok(principal);
    }

    @GetMapping
    @RequestMapping(path = "/auth")
    public ResponseEntity<?> viewAuthentication(Authentication auth) {
        return ResponseEntity.ok(auth);
    }

   @GetMapping
   @RequestMapping(path = "/public")
   public List<String> publicResources() {
        return Arrays.asList("some list","of very", "non-secret data");
   }

    @GetMapping
    @RequestMapping(path = "/param")
    public Person publicParam(@RequestParam String name) {
        return new Person(name);
    }

    @GetMapping
    @RequestMapping(path = "/name")
    public String getAuthName(Authentication auth) {
        return auth.getName();
    }

    public static record Person(String name){}

    @PostMapping
    public ResponseEntity<?> registerWebClient() {
        String id = UUID.randomUUID().toString();
        TokenSettings tokenSettings = TokenSettings
                .builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofMinutes(20))
                .refreshTokenTimeToLive(Duration.ofDays(15))
                .build();
       RegisteredClient registeredClient = RegisteredClient
               .withId(id)
               .clientIdIssuedAt(Instant.now())
               .clientName("YoungTech's E-commerce Web Application")
               .clientId("web")
               .clientSecret("{noop}secret")
               .scopes( scopes -> {
                   scopes.add(OidcScopes.OPENID);
                   scopes.add(OidcScopes.PROFILE);
                   scopes.add(OidcScopes.EMAIL);
                   scopes.add(OidcScopes.ADDRESS);
                   scopes.add(OidcScopes.ADDRESS);
                   scopes.add(OidcScopes.PHONE);
                   scopes.add("all");
               })
               .tokenSettings(tokenSettings)
               .clientAuthenticationMethods( methods -> {
                   methods.add(ClientAuthenticationMethod.CLIENT_SECRET_POST);
                   methods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
               })
               .authorizationGrantTypes( grantTypes -> {
                   grantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
                   grantTypes.add(EidteParameters.GRANT_TYPE_INSTANCE);
               })
               .build();

       clientRepo.save(registeredClient);
       return ResponseEntity.ok(registeredClient);
    }


}
