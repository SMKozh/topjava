package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T checkValue, T startValue, T endValue) {
        if (startValue == null && endValue != null) {
            return checkValue.compareTo(endValue) < 0;
        }
        if (startValue != null && endValue == null) {
            return checkValue.compareTo(startValue) >= 0;
        }
        if (startValue == null && endValue == null) {
            return true;
        }
        return checkValue.compareTo(startValue) >= 0 && checkValue.compareTo(endValue) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

