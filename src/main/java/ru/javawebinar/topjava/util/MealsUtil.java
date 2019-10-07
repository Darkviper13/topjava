package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    private static final int DEFAULT_CALORIES_PER_DAY = 2000;

    //Delete try-catch block when start use data base
    private static MealTo createTo(Meal meal, boolean excess) {
        try {
            int id = meal.getId();
            return new MealTo(id, meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List<MealTo> createListOfMealTo(List<Meal> meals) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > DEFAULT_CALORIES_PER_DAY))
                .collect(Collectors.toList());
    }
}