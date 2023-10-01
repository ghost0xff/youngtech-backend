package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;


public class QuantityOfElementsException extends AbstractRuntimeException {
    public QuantityOfElementsException(
            String detail
    ) {
        super(
                "Unsupported quantity of elements requested",
                ErrorCode.from(1),
                HttpStatus.BAD_REQUEST,
                detail
        );
    }

}
