package com.youngtechcr.www.exceptions;

import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.EmptyRepositoryException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.ValueMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EmptyRepositoryException.class)
    public ProblemDetail handleEmptyRepositoryException(EmptyRepositoryException exception) {
        return ExceptionUtils.handle(exception);
    }
    @ExceptionHandler(NoDataFoundException.class)
    public ProblemDetail handleNoDataFoundException(NoDataFoundException exception) {
        return ExceptionUtils.handle(exception);
    }
    @ExceptionHandler(ValueMismatchException.class)
    public ProblemDetail handleValueMismatchException(ValueMismatchException exception) {
        return ExceptionUtils.handle(exception);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ProblemDetail handleAlreadyExistsException(AlreadyExistsException exception) {
        return ExceptionUtils.handle(exception);
    }

}
