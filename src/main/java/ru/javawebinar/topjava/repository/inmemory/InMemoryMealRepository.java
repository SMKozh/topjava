package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private static final Map<Integer, Meal> EMPTY_MAP = Collections.emptyMap();
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.userMeals.forEach(meal -> save(meal, 1));
        MealsUtil.adminMeals.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.putIfAbsent(userId, new ConcurrentHashMap<>());
            repository.get(userId).put(meal.getId(), meal);
            log.info("save {}", meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        return repository.getOrDefault(userId, EMPTY_MAP).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        return repository.getOrDefault(userId, EMPTY_MAP).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return getByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAllFilteredByDate");
        return getByPredicate(userId,
                meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate == null ? null : endDate.plusDays(1)));
    }

    private List<Meal> getByPredicate(int userId, Predicate<Meal> filter) {
        return repository.getOrDefault(userId, EMPTY_MAP).values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

