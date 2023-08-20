package com.youngtechcr.www.exceptions.custom;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class FailedExternalHttpConnectionException extends OAuth2AuthenticationException{
    public FailedExternalHttpConnectionException(String msg) {
        super(msg);
    }
}