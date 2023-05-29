package com.youngtechcr.www.exceptions;


public class ErrorCode {

    private Integer codeNumber;

    private ErrorCode(Integer codeNumber){
        this.codeNumber = codeNumber;
    }

    public static ErrorCode from(Integer code){
        return new ErrorCode(code);
    }

    public Integer getCodeNumber() {
        return this.codeNumber;
    }

}
