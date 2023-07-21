package com.youngtechcr.www.domain;

import com.youngtechcr.www.exceptions.custom.InvalidElementException;

public interface Validator<T> {

    boolean isValid(T obj) throws InvalidElementException;

}
