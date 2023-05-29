package com.youngtechcr.www.services;
/*
* Should only be use when just really really basic CRUD opetations
* are requiered since handling various domain objects might get confusing
* and complicated when naming methods
* */
public interface BasicCrudService<T> {
    T findById(Integer id);
    T create(T toBeCreated);
    T updateById(Integer id, T toBeUpdated);
    void deleteById(Integer id);
}
