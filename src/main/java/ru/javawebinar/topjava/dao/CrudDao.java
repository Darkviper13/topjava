package ru.javawebinar.topjava.dao;

import java.util.List;

//Base CRUD interface
public interface CrudDao<T> {
    T get(Integer id);

    T save(T model);

    T update(Integer id, T model);

    void delete(Integer id);

    List<T> findAll();
}
