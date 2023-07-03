package com.youngtechcr.www.regex;

import com.youngtechcr.www.exceptions.CustomRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class RegexMatchingException extends RuntimeException implements CustomRuntimeException {

    private String defaultTitle = "Internal error while matching element";
    private ErrorCode customErrorCode = ErrorCode.from(7);
    private HttpStatus defaultHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public RegexMatchingException(String detail) {
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
