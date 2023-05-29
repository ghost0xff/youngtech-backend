package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.ErrorCode;
import com.youngtechcr.www.exceptions.CustomRuntimeException;
import org.springframework.http.HttpStatus;

public class FileOperationException extends RuntimeException implements CustomRuntimeException {

    private String defaultTitle = "Can't process file";
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private ErrorCode errorCode = ErrorCode.from(5);

    public FileOperationException(String detail) {
        super(detail);
    }

    @Override
    public String getDefaultTitle() {
        return this.defaultTitle;
    }

    @Override
    public ErrorCode getCustomErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getDefaultHttpStatus() {
        return this.status;
    }

    @Override
    public String getDetail() {
        return this.getMessage();
    }
}
