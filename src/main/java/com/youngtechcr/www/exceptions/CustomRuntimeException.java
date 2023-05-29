package com.youngtechcr.www.exceptions;

import org.springframework.http.HttpStatus;

public interface CustomRuntimeException {

    public String getDefaultTitle();

    public ErrorCode getCustomErrorCode();

    public HttpStatus getDefaultHttpStatus();

    //  getDetail should should always return the getMessage()
    public String getDetail();

}
