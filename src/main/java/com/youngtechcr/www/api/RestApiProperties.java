package com.youngtechcr.www.api;

import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties
public record RestApiProperties(
    int version,
    String baseUrl,

    //    @Pattern(regexp = "https?:\/\/\S+\.\S+")
    @Pattern(regexp = "https?:\\/\\/\\S+\\:[0-9]+$") //  -> http://localhost:4200
    String webClient
) {
}
