package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoDataFoundException extends AbstractRuntimeException  {

    public NoDataFoundException(
            String detail
    ) {
        super(
            "No entities found",
                ErrorCode.from(2),
                HttpStatus.NOT_FOUND,
                detail
        );
    }

}
