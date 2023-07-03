package com.youngtechcr.www.domain;

public interface Validator<T> {

    boolean isValid(T obj);

}
