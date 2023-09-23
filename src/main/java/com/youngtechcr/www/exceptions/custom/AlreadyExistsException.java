package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends AbstractRuntimeException {

    public AlreadyExistsException(
            String detail
    ) {
        super(
                "Data already exists and can't be duplicated",
                ErrorCode.from(3),
                HttpStatus.CONFLICT,
                detail
        );
    }
}
