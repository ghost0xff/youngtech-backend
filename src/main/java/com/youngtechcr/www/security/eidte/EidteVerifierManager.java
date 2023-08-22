package com.youngtechcr.www.security.eidte;

import com.youngtechcr.www.security.eidte.google.EidteGoogleVerifier;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Component
public class EidteVerifierManager implements EidteVerifier{

    private Map<String,EidteVerifier> eidteVerifiers;

    public EidteVerifierManager() {
       this.eidteVerifiers = new HashMap<>();
       this.eidteVerifiers.put("google", new EidteGoogleVerifier());
    }


    @Override
    public Optional<OidcIdToken> verify(String tokenValue)
            throws GeneralSecurityException, IOException {

        return Optional.empty();
    }


   public Map<String, EidteVerifier> verifiers() {
       return this.eidteVerifiers;
   }
}
