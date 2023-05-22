package com.youngtechcr.www.exceptions;


import org.springframework.stereotype.Component;

public class ErrorCode {

    private Integer codeNumber;

    private ErrorCode(Integer codeNumber){
        this.codeNumber = codeNumber;
    }

    public static ErrorCode getCode(Integer code){
        return new ErrorCode(code);
    }


}
