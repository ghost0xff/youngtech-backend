package com.youngtechcr.www.security.oidc;

import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

public final class OIdcHelpers {
     public static final OAuth2TokenType ID_TOKEN_TYPE_INSTANCE=
            new OAuth2TokenType(OidcParameterNames.ID_TOKEN);
}
