package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    List<Meal> readAll();

    Meal read(int id);

    Meal create(Meal meal);

    Meal update(Meal meal);

    void delete(int id);
}
