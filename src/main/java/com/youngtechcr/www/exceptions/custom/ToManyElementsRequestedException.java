package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import com.youngtechcr.www.exceptions.CustomRuntimeException;
import org.springframework.http.HttpStatus;


public class ToManyElementsRequestedException extends AbstractRuntimeException {
    public ToManyElementsRequestedException(
            String detail
    ) {
        super(
                "Too much elements requested",
                ErrorCode.from(1),
                HttpStatus.TOO_MANY_REQUESTS,
                detail
        );
    }

}
