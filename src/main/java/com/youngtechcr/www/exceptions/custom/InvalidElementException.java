package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidElementException extends AbstractRuntimeException {
    public InvalidElementException(
            String detail
    ) {
        super(
                "Incomplete, invalid or faulty element in HTTP body",
                ErrorCode.from(6),
                HttpStatus.BAD_REQUEST,
                detail
        );
    }
}
