package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.ErrorCode;
import com.youngtechcr.www.exceptions.CustomRuntimeException;
import org.springframework.http.HttpStatus;


public class ToManyElementsRequestedException extends RuntimeException implements CustomRuntimeException {

    private String defaultTitle = "Too much elements requested ";
    private HttpStatus defaultStatus = HttpStatus.TOO_MANY_REQUESTS; // HTTP 429 status code
    private ErrorCode customErrorCode = ErrorCode.from(1);


    public ToManyElementsRequestedException(String detail) {
        super(detail);
    }


    @Override
    public String getDefaultTitle() {
        return this.defaultTitle;
    }

    @Override
    public ErrorCode getCustomErrorCode() {
        return this.customErrorCode;
    }

    @Override
    public HttpStatus getDefaultHttpStatus() {
        return this.defaultStatus;
    }

    @Override
    public String getDetail() {
        return this.getMessage();
    }
}
