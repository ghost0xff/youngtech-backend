package com.youngtechcr.www.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.youngtechcr.www.api.ApiProperties;
import com.youngtechcr.www.http.HttpUtils;
import com.youngtechcr.www.security.crypto.AsymmetricKeyType;
import com.youngtechcr.www.security.crypto.CryptoUtils;
import com.youngtechcr.www.security.crypto.CryptoProps;
import com.youngtechcr.www.security.eidte.*;
import com.youngtechcr.www.security.idp.IdentityProviderRepository;
import com.youngtechcr.www.security.user.UserService;
import com.youngtechcr.www.utils.DsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final ApiProperties apiProperties;
    private final CryptoProps cryptoProps;
    private final IdentityProviderRepository idpRepository;
    private final UserService userService;

    public SecurityConfig(
            ApiProperties apiProperties,
            CryptoProps cryptoProps,
            IdentityProviderRepository idpRepository, UserService userService) {
        this.apiProperties = apiProperties;
        this.cryptoProps = cryptoProps;
        this.idpRepository = idpRepository;
        this.userService = userService;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authzServerSecurityFilterChain(
            HttpSecurity http,
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<?> tokenGenerator,
            RegisteredClientRepository registeredClientRepository
    ) throws Exception {
//       OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

//       http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        authorizationServerConfigurer
                .oidc(Customizer.withDefaults()) // Enable OpenID Connect 1.0
                .tokenEndpoint( tokenEndpoint ->
                        tokenEndpoint
                                .accessTokenRequestConverters( converters -> {
                                    converters.add(new EidteAuthenticationConverter(
                                            registeredClientRepository,
                                            this.idpRepository
                                    ));
                                })
                                .authenticationProviders( providers -> {
                                   providers.add(new EidteAuthenticationProvider(
                                           authorizationService,
                                           tokenGenerator,
                                           this.userService
                                   ));
                                })
                );
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        http
                .securityMatcher(endpointsMatcher)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf( csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
//                .csrf( csrf -> csrf.disable())
//                 Accept access tokens for User InfZZo and/or Client Registration
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(Customizer.withDefaults())
                )
                .apply(authorizationServerConfigurer)
        ;

        return http.build();
    }

    @Bean
//    @Order
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
//                .securityMatcher("/api/**")
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(HttpMethod.GET,"/products").authenticated()
                                .requestMatchers(HttpMethod.GET, "/test").permitAll()
                                .requestMatchers(HttpMethod.POST, "/test").permitAll()
                                .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
//                        jwt -> {
//                            jwt.authenticationManager(null);
//                            jwt.jwtAuthenticationConverter(null);
//                            jwt.jwkSetUri(null);
//                        }
                        Customizer.withDefaults()
                ))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthoritiesClaimName(CustomClaims.ROLES);
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


//    @Bean
//    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
//        return context -> {
//            JwtClaimsSet.Builder claims = context.getClaims();
//            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
//                // Customize headers/claims for access_token
//               Set<String> roles = context.getAuthorizedScopes();
//               claims.claims(claimMap -> {
//                   claimMap.put(CustomClaims.ROLES, DsaUtils.spaceSeparated(roles));
//               });
//            } else if (context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN)) {
//                // Customize headers/claims for id_token
//
//            }
//        };
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedOrigins(Arrays.asList(this.apiProperties.webClient()));
        corsConfiguration.addAllowedOrigin(this.apiProperties.webClient());
        corsConfiguration.setAllowedMethods(HttpUtils.getCommonHttpMethods());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(
                jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }


     @Bean
     public JWKSource<SecurityContext> jwkSource() {
        List<JWK> jwkList = cryptoProps
                .keys()
                .stream()
                .map(keyMetadata -> {
                    RSAPublicKey publicKey = (RSAPublicKey) CryptoUtils.rsaKeyFromDerFile(
                            keyMetadata.publicKeyDerPath(), AsymmetricKeyType.PUBLIC);
                    RSAPrivateKey privateKey = (RSAPrivateKey) CryptoUtils.rsaKeyFromDerFile(
                            keyMetadata.privateKeyDerPath(), AsymmetricKeyType.PRIVATE);
                    String keyId = keyMetadata.keyId();
                    return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(keyId).build();
                }
          ).collect(Collectors.toList());

     JWKSet jwkSet = new JWKSet(jwkList);
     return new ImmutableJWKSet<>(jwkSet);
    }


    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    /* TODO: Fix this...
        Fore some reason, whenever I registers the PasswordEncoder bean I can't authenticate
        OAuth2.0/OIDC/EIDTE clients with {noop} credentials, meaning that the next operations
        can't be accomplished:
            - IEDTE Exchange
            - Any OAuth2.0/OIDC flows that requiere client authentication with client_secret
        UPDATE: Reason why this happens is because ClientSecretAuthenticationProvider (which
        is encharged of authenticating clients before reaching any AuthenticationProviders that
        actually generate tokens and particiapate in authz/authn flows) tries to match provided
        client_secret with the equivalent from the RegisteredClient (which is stored probably in
        DB or Cache (altough it can be simply stored in memory)
    */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }

}
