package com.youngtechcr.www.exceptions;

import com.youngtechcr.www.exceptions.custom.*;
import com.youngtechcr.www.regex.RegexMatchingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.regex.PatternSyntaxException;

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

    @ExceptionHandler(InvalidElementException.class)
    public ProblemDetail handleInvalidElementException(InvalidElementException exception) {
        return ExceptionUtils.handle(exception);
    }

    @ExceptionHandler(RegexMatchingException.class)
    public ProblemDetail handleRegexMatchingException(RegexMatchingException exception) {
        return ExceptionUtils.handle(exception);
    }
}
