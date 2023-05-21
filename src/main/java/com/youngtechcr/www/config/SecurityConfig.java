package com.youngtechcr.www.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/api/**")
//                .authorizeHttpRequests( auth ->
//                        auth
//                                .requestMatchers("/api/products").permitAll()
//                                .anyRequest().denyAll())
//                .csrf( csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                . httpBasic(withDefaults())
//                .build();
//
//}
//    @Bean
//    public RoleHierarchy roleHierarchy(){
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        String hierarchy = "ROLE_ADMIN > ROLE_USER";
//        roleHierarchy.setHierarchy(hierarchy);
//        return roleHierarchy;
//    }
//
//}
