package ru.javawebinar.topjava.dao;

import java.util.List;

//Base CRUD interface
public interface CrudDao<T> {
    T find(Integer id);

    void save(T model);

    void update(Integer id, T model);

    void delete(Integer id);

    List<T> findAll();
}
