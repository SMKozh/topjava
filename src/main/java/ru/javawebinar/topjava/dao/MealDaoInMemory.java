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

public class MealDaoInMemory implements MealDao {
    private final Map<Integer, Meal> repositoryMap = new ConcurrentHashMap<>();

    private final AtomicInteger idCounter = new AtomicInteger();

    {
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
            create(meal);
        }
    }

    @Override
    public List<Meal> readAll() {
        return new ArrayList<>(repositoryMap.values());
    }

    @Override
    public Meal read(int id) {
        return repositoryMap.get(id);
    }

    @Override
    public Meal create(Meal meal) {
        meal.setId(idCounter.incrementAndGet());
        repositoryMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal) {
        if (!repositoryMap.containsKey(meal.getId())) {
            throw new RuntimeException();
        }
        repositoryMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        repositoryMap.remove(id);
    }
}
