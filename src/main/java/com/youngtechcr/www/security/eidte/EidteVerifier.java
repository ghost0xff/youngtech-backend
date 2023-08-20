package com.youngtechcr.www.security.eidte;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

public interface EidteVerifier {
    public Optional<OidcIdToken> verify(String tokenValue) throws GeneralSecurityException, IOException;

}
