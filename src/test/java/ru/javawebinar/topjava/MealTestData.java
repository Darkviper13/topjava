package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    private static final int SEQ = START_SEQ + 2;

    public static final Meal USER_MEAL_1 = new Meal(SEQ, LocalDateTime.of(2019, 10, 21, 9, 30), "Завтрак", 500);
    public static final Meal USER_MEAL_2 = new Meal(SEQ + 1, LocalDateTime.of(2019, 10, 21, 12, 0), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal(SEQ + 2, LocalDateTime.of(2019, 10, 21, 18, 30), "Ужин", 510);
    public static final Meal ADMIN_MEAL_1 = new Meal(SEQ + 3, LocalDateTime.of(2019, 10, 21, 9, 30), "Завтрак", 500);
    public static final Meal ADMIN_MEAL_2 = new Meal(SEQ + 4, LocalDateTime.of(2019, 10, 21, 12, 0), "Обед", 1000);
    public static final Meal ADMIN_MEAL_3 = new Meal(SEQ + 5, LocalDateTime.of(2019, 10, 21, 18, 30), "Ужин", 500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).hasSameElementsAs(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

}
