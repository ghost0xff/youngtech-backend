package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.ErrorCode;
import com.youngtechcr.www.exceptions.CustomRuntimeException;
import org.springframework.http.HttpStatus;


public class EmptyRepositoryException extends RuntimeException implements CustomRuntimeException {

    private String defaultTitle = "No entities available";
    private HttpStatus defaultStatus = HttpStatus.NOT_FOUND;
    private ErrorCode customErrorCode = ErrorCode.getCode(1);


    public EmptyRepositoryException(String detail) {
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
