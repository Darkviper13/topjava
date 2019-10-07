package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//Implementation MealDao for working with in-memory data
public class MealDaoInMemory implements CrudDao<Meal> {

    //For using in multithreading application, take ConcurrentHashMap.
    private final Map<Integer, Meal> dataBase = new ConcurrentHashMap<>();

    //We use AtomicInteger for generate primary keys for our data base.
    private AtomicInteger counter = new AtomicInteger(0);

    //Add data
    {
        save(new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 500));
        save(new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 1000));
        save(new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 500));
        save(new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1000));
        save(new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500));
        save(new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 510));
        save(new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1100));
        save(new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public Meal get(Integer id) {
        return dataBase.get(id);
    }

    @Override
    public Meal save(Meal model) {
        if (model.getId() == null) {
            model.setId(counter.incrementAndGet());
        }
        dataBase.put(model.getId(), model);
        return model;
    }

    @Override
    public Meal update(Integer id, Meal model) {
        if (id > 0 && id <= counter.get()) {
            model.setId(id);
            dataBase.computeIfPresent(id, (key, value) -> model);
            return model;
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        dataBase.remove(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(dataBase.values());
    }
}