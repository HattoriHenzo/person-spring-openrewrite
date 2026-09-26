package com.practice.application.spring.generic;


import java.util.List;

public interface GenericService<T> {

    T findById(Long id);
    List<T> findAll();
    T createOrUpdate(T object);
    void delete(Long id);
}
