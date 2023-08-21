package com.youngtechcr.www.api;

import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "api")
public record ApiProperties(
    // API specific stuff
    int version,
    String baseUrl,
    @Pattern(regexp = "https?:\\/\\/\\S+\\:[0-9]+$") //  -> http://localhost:3000
    String webClient
) {
}
