package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        if (!StringUtils.hasLength(text)) {
            return null;
        }
        return LocalTime.from(getDateFormatter(locale).parse(text));
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        if (object == null) {
            return "";
        }
        return object.format(getDateFormatter(locale));
    }

    protected DateTimeFormatter getDateFormatter(Locale locale) {
        return DateTimeFormatter.ISO_LOCAL_TIME.withLocale(locale);
    }
}
