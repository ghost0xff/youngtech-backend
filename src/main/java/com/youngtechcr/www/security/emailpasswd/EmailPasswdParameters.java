package com.youngtechcr.www.security.emailpasswd;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

public final class EmailPasswdParameters {

    public static final String GRANT_TYPE_VALUE = "email_passwd";
    public static final AuthorizationGrantType
        GRANT_TYPE_INSTANCE = new AuthorizationGrantType("email_passwdd");
    public static final String EMAIL = "email";
    public static final String PASSWD = "passwd";

}
