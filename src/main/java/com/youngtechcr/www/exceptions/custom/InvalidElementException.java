package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.CustomRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidElementException extends RuntimeException implements CustomRuntimeException {


    private String defaultTitle = "Incomplete, invalid or faulty element in HTTP body";
    private ErrorCode customErrorCode = ErrorCode.from(6);
    private HttpStatus defaultHttpStatus = HttpStatus.BAD_REQUEST;

    public InvalidElementException(String detail) {
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
        return this.defaultHttpStatus;
    }

    @Override
    public String getDetail() {
        return this.getMessage();
    }
}
