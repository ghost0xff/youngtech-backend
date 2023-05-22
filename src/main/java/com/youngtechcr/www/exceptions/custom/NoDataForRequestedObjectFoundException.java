package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.ErrorCode;
import com.youngtechcr.www.exceptions.ICustomRuntimeException;
import org.springframework.http.HttpStatus;

public class NoDataForRequestedObjectFoundException extends RuntimeException implements ICustomRuntimeException {

    private String defaultTitle = "No entities found";
    private HttpStatus defaultStatus = HttpStatus.NOT_FOUND;
    private ErrorCode customErrorCode =  ErrorCode.getCode(2);

    public NoDataForRequestedObjectFoundException(String detail) {
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
