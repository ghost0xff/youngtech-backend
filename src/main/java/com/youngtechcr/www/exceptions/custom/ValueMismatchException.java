package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class ValueMismatchException extends AbstractRuntimeException {
    public ValueMismatchException(
            String detail
    ) {
        super(
                "Bad request parameters exception",
                ErrorCode.from(4),
                HttpStatus.BAD_REQUEST,
                detail
        );
    }

}
