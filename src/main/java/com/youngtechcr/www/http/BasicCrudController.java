package com.youngtechcr.www.http;

import org.springframework.http.ResponseEntity;
/*
* Should only be use when just really really really basic CRUD opetations
* are requiered since handling various domain objects might get confusing
* and complicated when naming methods
 * */
public interface BasicCrudController<T> {
    ResponseEntity<T> findById(Integer id);
    ResponseEntity<T> create(T toBeCreated);
    ResponseEntity<T> updateById(Integer id, T toBeUpdated);
    ResponseEntity<T> deleteById(Integer id);
}
