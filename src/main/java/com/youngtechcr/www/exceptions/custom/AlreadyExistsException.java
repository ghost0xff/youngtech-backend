package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.ErrorCode;
import com.youngtechcr.www.exceptions.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends RuntimeException implements CustomRuntimeException {

    private String defaultTitle = "Data already exists and can't be duplicated";
    private ErrorCode customErrorCode = ErrorCode.from(3);
    private HttpStatus defaultStatus = HttpStatus.CONFLICT;

    public AlreadyExistsException(String detail) {
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
