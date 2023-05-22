package com.youngtechcr.www.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CustomProblemDetail extends ProblemDetail {
        ErrorCode errorCode;

        private CustomProblemDetail(String title, String detail, HttpStatus status,ErrorCode code) {
            this.setTitle(title);
            this.setDetail(detail);
            this.setStatus(status);
            this.setErrorCode(code);
        }

        public static CustomProblemDetail of(String title, String detail, HttpStatus status, ErrorCode code) {
            return new CustomProblemDetail(title, detail, status, code);
        }

        public void setErrorCode(ErrorCode errorCode){
            this.setProperty("errorCode", errorCode);
        }
        public ErrorCode getErrorCode() {
            return errorCode;
        }
}
