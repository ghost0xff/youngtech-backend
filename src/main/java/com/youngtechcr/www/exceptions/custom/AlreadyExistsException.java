package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.ErrorCode;
import com.youngtechcr.www.exceptions.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends RuntimeException implements CustomRuntimeException {

    private String defaultTitle = "Data already exists and can't be duplicated";
    private ErrorCode customErrorCode = ErrorCode.getCode(3);
    private HttpStatus status = HttpStatus.CONFLICT;

    public AlreadyExistsException(String detail) {
        super(detail);
    }

    @Override
    public String getDefaultTitle() {
        return null;
    }

    @Override
    public ErrorCode getCustomErrorCode() {
        return null;
    }

    @Override
    public HttpStatus getDefaultHttpStatus() {
        return null;
    }

    @Override
    public String getDetail() {
        return null;
    }
}
