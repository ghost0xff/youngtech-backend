package com.youngtechcr.www.http;
/*
* Should only be use when just really really basic CRUD opetations
* are requiered since handling various domain objects might get confusing
* and complicated when naming methods
* */
public interface BasicCrudService<T> {
    T find(Integer id);
    T create(T toBeCreated);
    T update(Integer id, T toBeUpdated);
    void delete(Integer id);
}
