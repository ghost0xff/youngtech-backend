package com.youngtechcr.www.regex;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class RegexMatchingException extends AbstractRuntimeException {

    public RegexMatchingException(
            String detail
    ) {
        super(
                "Internal error while matching element",
                ErrorCode.from(10),
                HttpStatus.INTERNAL_SERVER_ERROR,
                detail
        );
    }
}
