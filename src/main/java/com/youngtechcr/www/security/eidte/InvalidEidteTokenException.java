package com.youngtechcr.www.security.eidte;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class InvalidEidteTokenException extends AbstractRuntimeException {
    public InvalidEidteTokenException(
            String detail
    ) {
        super(
                "Can't process eidte token",
                ErrorCode.from(9),
                HttpStatus.UNAUTHORIZED,
                detail
        );
    }
}