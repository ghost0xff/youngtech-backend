package com.youngtechcr.www.backend.exceptions;

import com.youngtechcr.www.backend.exceptions.custom.EmptyRepositoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyRepositoryException.class)
    public ProblemDetail handleEmptyRepositoryException(EmptyRepositoryException noProductsFoundException) {
        ProblemDetail customProblemDetail = ProblemDetail.
                forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        noProductsFoundException.getMessage());
        customProblemDetail.setProperty("errorCode", noProductsFoundException.getCustomErrorCode());
        return customProblemDetail;
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ProblemDetail handleSQLIntegrityConstraintViolationException(
                    SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
        ProblemDetail customProblemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Could not complete transaction. Due to encomplete or bad http request body");
        customProblemDetail.setProperty("errorCode", "2");
        return customProblemDetail;
    }
}
