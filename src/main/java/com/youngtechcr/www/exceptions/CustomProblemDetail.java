package com.youngtechcr.www.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CustomProblemDetail extends ProblemDetail {

    Integer errorCode;

    private CustomProblemDetail(String title, String detail, HttpStatus status, ErrorCode code) {
        this.setTitle(title);
        this.setDetail(detail);
        this.setStatus(status);
        this.setErrorCode(code.getCodeNumber());
    }

//    private CustomProblemDetail(String title, String detail, HttpStatus status) {
//        this.setTitle(title);
//        this.setDetail(detail);
//        this.setStatus(status);
//    }

    public static CustomProblemDetail of(String title, String detail, HttpStatus status, ErrorCode code) {
        return new CustomProblemDetail(title, detail, status, code);
    }

    private void setErrorCode(Integer errorCode){
            super.setProperty("errorCode", errorCode);
        }


}
