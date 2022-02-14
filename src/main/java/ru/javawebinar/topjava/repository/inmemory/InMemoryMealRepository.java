package ru.javawebinar.topjava.repository.inmemory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final ListMultimap<Integer, Meal> repository = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getDescription().contains("user") ? 1 : 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        synchronized (repository) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                meal.setUserId(userId);
                repository.put(meal.getUserId(), meal);
                log.info("save {}", meal);
                return meal;
            }
            // handle case: update, but not present in storage
            Meal oldMeal = repository.get(userId).stream()
                    .filter(m -> m.getId().equals(meal.getId()))
                    .findAny()
                    .orElse(null);
            if (oldMeal != null) {
                meal.setUserId(userId);
                log.info("update {}", meal);
                repository.remove(userId, oldMeal);
                repository.put(userId, meal);
                return meal;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        synchronized (repository) {
            Meal meal = repository.get(userId).stream()
                    .filter(m -> m.getId() == id)
                    .findAny()
                    .orElse(null);
            if (meal == null) {
                return false;
            }
            log.info("delete {}", id);
            return repository.remove(userId, meal);
        }
    }

    @Override
    public Meal get(int id, int userId) {
        synchronized (repository) {
            Meal meal = repository.get(userId).stream()
                    .filter(m -> m.getId() == id)
                    .findAny()
                    .orElse(null);
            log.info("get {}", id);
            return meal;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return getByPredicate(userId, meal -> true);

    }

    @Override
    public List<Meal> getAllFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAllFilteredByDate");
        return getByPredicate(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate));
    }

    private List<Meal> getByPredicate(int userId, Predicate<Meal> filter) {
        synchronized (repository) {
            return repository.get(userId).stream()
                    .filter(filter)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
    }
}

