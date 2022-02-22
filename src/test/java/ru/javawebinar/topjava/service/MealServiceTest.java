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
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
//         Only for postgres driver logging
//         It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal created = getNew();
        created.setDateTime(userMeal1.getDateTime());
        assertThrows(DataAccessException.class, () -> service.create(created, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(userMeal2.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(userMeal2.getId(), USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotMyMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(adminMeal1.getId(), USER_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        Integer updatedId = updated.getId();
        service.update(updated, USER_ID);
        assertMatch(service.get(updatedId, USER_ID), getUpdated());
    }

    @Test
    public void updateNotFound() {
        Meal updated = getUpdated();
        updated.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void updateNotMyMeal() {
        Meal updated = getUpdated();
        updated.setId(adminMeal1.getId());
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(userMeal1.getId(), USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotMyMeal() {
        assertThrows(NotFoundException.class, () -> service.get(adminMeal1.getId(), USER_ID));
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30), LocalDate.of(2020, Month.JANUARY, 30), USER_ID);
        assertMatch(meals, userMeal3, userMeal2, userMeal1);
    }
}