package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    List<Meal> readAll();

    Meal read(Integer id);

    void create(Meal meal);

    void update(Meal meal);

    void delete(Integer id);
}
