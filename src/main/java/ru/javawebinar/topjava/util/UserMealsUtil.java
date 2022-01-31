package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println();
        System.out.println(filteredByStreams2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();

        for (UserMeal userMeal : meals) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            caloriesMap.merge(localDate, userMeal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> result = new ArrayList<>();

        for (UserMeal userMeal : meals) {
            LocalTime localTime = userMeal.getDateTime().toLocalTime();
            LocalDate localDate = userMeal.getDateTime().toLocalDate();

            if (TimeUtil.isBetweenHalfOpen(localTime, startTime, endTime)) {
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(userMeal.getDateTime(),
                        userMeal.getDescription(), userMeal.getCalories(), caloriesMap.get(localDate) > caloriesPerDay);
                result.add(userMealWithExcess);
            }
        }

        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = meals.stream()
                .collect(Collectors.toMap(x -> x.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        List<UserMealWithExcess> result = meals.stream()
                .filter(x -> TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                .map(x -> new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(),
                        caloriesMap.get(x.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(toList());

        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> result = meals.stream().collect(Collectors.teeing(Collectors.toMap(x -> x.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum),
                Collectors.mapping(Function.identity(), toList()),
                (s1, s2) -> s2.stream().filter(x -> TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                        .map(x -> new UserMealWithExcess(x.getDateTime(), x.getDescription(), x.getCalories(),
                        s1.get(x.getDateTime().toLocalDate()) > caloriesPerDay))
                        .collect(toList())));

        return result;
    }
}
