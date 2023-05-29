package com.youngtechcr.www.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public final class ResponseEntityUtils {


    public static <T> ResponseEntity<T> created(T resourceToBeCreated) {
        ResponseEntity<T> responseEntity = new ResponseEntity(resourceToBeCreated,HttpStatus.CREATED);
        return responseEntity;
    }
}
