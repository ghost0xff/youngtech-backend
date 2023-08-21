package com.youngtechcr.www.security.crypto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;
import java.util.List;

@ConfigurationProperties(prefix = "crypto")
public record CryptoProps(
        List<KeyMetadata> keys
){

    public record KeyMetadata (
        String keyId,
        Path privateKeyDerPath,
        Path publicKeyDerPath
    ) {}

}
