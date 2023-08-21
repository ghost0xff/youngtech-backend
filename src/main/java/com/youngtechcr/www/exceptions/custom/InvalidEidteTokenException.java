package com.youngtechcr.www.exceptions.custom;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class InvalidEidteTokenException extends OAuth2AuthenticationException {
    public InvalidEidteTokenException(String errorCode) {
        super(errorCode);
    }

    public InvalidEidteTokenException(OAuth2Error error) {
        super(error);
    }

    public InvalidEidteTokenException(OAuth2Error error, Throwable cause) {
        super(error, cause);
    }

    public InvalidEidteTokenException(OAuth2Error error, String message) {
        super(error, message);
    }

    public InvalidEidteTokenException(OAuth2Error error, String message, Throwable cause) {
        super(error, message, cause);
    }
}