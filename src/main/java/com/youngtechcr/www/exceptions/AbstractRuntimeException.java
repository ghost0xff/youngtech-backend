package com.youngtechcr.www.exceptions;

import org.springframework.http.HttpStatus;

public class AbstractRuntimeException extends RuntimeException{

    private final String defaultTitle;
    private final ErrorCode customErrorCode;
    private final HttpStatus defaultHttpStatus;
    private final String detail;
    protected AbstractRuntimeException(
            String defaultTitle,
            ErrorCode customErrorCode,
            HttpStatus defaultHttpStatus,
            String detail
    ) {
        super(detail);
        this.defaultTitle = defaultTitle;
        this.customErrorCode = customErrorCode;
        this.defaultHttpStatus = defaultHttpStatus;
        this.detail = detail;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public ErrorCode getCustomErrorCode() {
        return customErrorCode;
    }

    public HttpStatus getDefaultHttpStatus() {
        return defaultHttpStatus;
    }

    public String getDetail() {
        return detail;
    }
}
