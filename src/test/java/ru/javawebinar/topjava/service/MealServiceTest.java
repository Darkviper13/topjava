package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-jdbc.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_MEAL_1.getId(), USER_ID);
        assertMatch(meal, USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongMeal() throws Exception {
        service.get(ADMIN_MEAL_1.getId(), USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_MEAL_1.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), USER_MEAL_3, USER_MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongMeal() throws Exception {
        service.delete(ADMIN_MEAL_1.getId(), USER_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> meals = service.getBetweenDates(LocalDate.MIN, LocalDate.MAX, ADMIN_ID);
        assertMatch(meals, ADMIN_MEAL_3, ADMIN_MEAL_2, ADMIN_MEAL_1);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setCalories(800);
        updated.setDescription("Обед");
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongMeal() throws Exception {
        service.update(USER_MEAL_1, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.MIN, "Dinner", 300);
        Meal createMeal = service.create(newMeal, USER_ID);
        newMeal.setId(createMeal.getId());
        assertMatch(service.getAll(USER_ID), USER_MEAL_3, USER_MEAL_2, USER_MEAL_1, newMeal);
    }

    @Test
    public void createCheckingReturnResult() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.MIN, "Dinner", 300);
        Meal createMeal = service.create(newMeal, USER_ID);
        newMeal.setId(createMeal.getId());
        assertMatch(createMeal, newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicateUserAndDateTime() throws Exception {
        service.create(new Meal(null, USER_MEAL_1.getDateTime(), "Duplicate", 300), USER_ID);
    }
}