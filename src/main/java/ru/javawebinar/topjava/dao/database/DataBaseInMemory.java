package ru.javawebinar.topjava.dao.database;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//This class is imitation of DataBase and needed for testing.
//Use singletone pattern.

public class DataBaseInMemory {
    private final static DataBaseInMemory dataBaseInMemory = new DataBaseInMemory();

    //For using in multithreading application, take ConcurrentHashMap.
    private final static ConcurrentHashMap<Integer, Meal> dataBase = new ConcurrentHashMap<>();

    //We use AtomicInteger for generate primary keys for our data base.
    private static AtomicInteger id = new AtomicInteger(0);

    private DataBaseInMemory() {
    }

    public static DataBaseInMemory getDataBaseInMemory() {
        return dataBaseInMemory;
    }

    public static ConcurrentHashMap<Integer, Meal> getDataBase() {
        return dataBase;
    }

    public static void addMeal(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(id.incrementAndGet());
        }
        dataBase.put(meal.getId(), meal);
    }

    //Add data
    static {
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
}
