package com.youngtechcr.www.security.eidte;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

public final class EidteParameters {

    public static final String IPN = "ipn"; // Identity Provider Name
    public static final String GRANT_TYPE_VALUE= "eidte";
    public static final AuthorizationGrantType GRANT_TYPE_INSTANCE = new AuthorizationGrantType("eidte");

}
