package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

//Meal CRUD interface. Added some method's for better search
public interface MealDao extends CrudDao<Meal> {
    List<Meal> findByLocalDate(LocalDate localDate);
}
