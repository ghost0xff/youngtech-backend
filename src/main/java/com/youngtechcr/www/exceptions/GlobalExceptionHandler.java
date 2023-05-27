package com.youngtechcr.www.exceptions;

import com.youngtechcr.www.exceptions.custom.EmptyRepositoryException;
import com.youngtechcr.www.exceptions.custom.NoDataForRequestedObjectFoundException;
import com.youngtechcr.www.exceptions.custom.ParameterValueAndRequestBodyMismatchException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyRepositoryException.class)
    public ProblemDetail handleEmptyRepositoryException(EmptyRepositoryException exception) {
        return ExceptionUtils.handle(exception);
    }

    @ExceptionHandler(NoDataForRequestedObjectFoundException.class)
    public ProblemDetail handleNoDataForRequestedIdFoundException(NoDataForRequestedObjectFoundException exception) {
        return ExceptionUtils.handle(exception);
    }

    @ExceptionHandler(ParameterValueAndRequestBodyMismatchException.class)
    public ProblemDetail handleParameterValueAndRequestBodyMismatchException(ParameterValueAndRequestBodyMismatchException exception) {
        return ExceptionUtils.handle(exception);
    }

}
