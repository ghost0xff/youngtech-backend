package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class FailedExternalHttpConnectionException extends AbstractRuntimeException {

    public FailedExternalHttpConnectionException(
                String detail
    ) {
        super(
                "Failed to connect to external HTTP server",
                ErrorCode.from(8),
                HttpStatus.INTERNAL_SERVER_ERROR,
                detail
        );
    }

}