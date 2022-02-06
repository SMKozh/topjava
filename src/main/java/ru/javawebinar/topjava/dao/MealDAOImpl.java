package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAOImpl implements MealDAO{
    private static final Map<Integer, Meal> MEAL_REPOSITORY_MAP = new ConcurrentHashMap<>();

    private static final AtomicInteger MEAL_ID_HOLDER = new AtomicInteger();

    static {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        for (Meal meal : meals) {
            meal.setId(MEAL_ID_HOLDER.incrementAndGet());
            MEAL_REPOSITORY_MAP.put(meal.getId(), meal);
        }
    }

    @Override
    public List<Meal> readAll() {
        return new ArrayList<>(MEAL_REPOSITORY_MAP.values());
    }

    @Override
    public Meal read(Integer id) {
        return MEAL_REPOSITORY_MAP.get(id);
    }

    @Override
    public void create(Meal meal) {
        meal.setId(MEAL_ID_HOLDER.incrementAndGet());
        MEAL_REPOSITORY_MAP.put(meal.getId(), meal);
    }

    @Override
    public void update(Meal meal) {
        MEAL_REPOSITORY_MAP.put(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        MEAL_REPOSITORY_MAP.remove(id);
    }
}
