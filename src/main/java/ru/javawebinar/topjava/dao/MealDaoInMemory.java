package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//Implementation MealDao for working with in-memory data
public class MealDaoInMemory implements CrudDao<Meal> {

    //For using in multithreading application, take ConcurrentHashMap.
    private final ConcurrentHashMap<Integer, Meal> dataBase = new ConcurrentHashMap<>();

    //We use AtomicInteger for generate primary keys for our data base.
    private static AtomicInteger id = new AtomicInteger(0);

    private void addMeal(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(id.incrementAndGet());
        }
        dataBase.put(meal.getId(), meal);
    }

    //Add data
    {
        addMeal(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 500));
        addMeal(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 1000));
        addMeal(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 500));
        addMeal(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1000));
        addMeal(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500));
        addMeal(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 510));
        addMeal(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1100));
        addMeal(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        addMeal(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public MealDaoInMemory() {
    }

    @Override
    public void save(Meal model) {
        addMeal(model);
    }

    @Override
    public void update(Integer id, Meal model) {
        model.setId(id);
        dataBase.computeIfPresent(id, (key, value) -> value = model);
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
