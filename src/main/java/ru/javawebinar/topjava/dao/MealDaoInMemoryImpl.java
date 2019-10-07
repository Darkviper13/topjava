package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.dao.database.DataBaseInMemory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Implementation MealDao for working with in-memory data
public class MealDaoInMemoryImpl implements MealDao {

    public MealDaoInMemoryImpl() {
    }

    @Override
    public Meal find(Integer id) {
        if (DataBaseInMemory.getDataBase().containsKey(id)) {
            return DataBaseInMemory.getDataBase().get(id);
        }
        return null;
    }

    @Override
    public void save(Meal model) {
        DataBaseInMemory.addMeal(model);
    }

    @Override
    public void update(Integer id, Meal model) {
        model.setId(id);
        if (DataBaseInMemory.getDataBase().containsKey(id)) {
            DataBaseInMemory.getDataBase().merge(id, model, (oldVal, newVal) -> newVal);
        }
    }

    @Override
    public void delete(Integer id) {
        DataBaseInMemory.getDataBase().remove(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(DataBaseInMemory.getDataBase().values());
    }

    @Override
    public List<Meal> findByLocalDate(LocalDate localDate) {
        return DataBaseInMemory.getDataBase().values().stream()
                .filter(x -> x.getDate().isEqual(localDate)).collect(Collectors.toList());
    }
}
