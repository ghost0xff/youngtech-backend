package com.youngtechcr.www.security.exceptions;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnkownTokenException extends AbstractRuntimeException {


    public UnkownTokenException(
            String detail
    ) {
        super(
                "Unkown user read from request",
                ErrorCode.from(7),
                HttpStatus.UNAUTHORIZED,
                detail
        );
    }
}
