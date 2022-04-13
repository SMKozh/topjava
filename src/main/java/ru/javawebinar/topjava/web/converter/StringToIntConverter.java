package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class StringToIntConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        return StringUtils.hasLength(source) ? Integer.parseInt(source) : 0;
    }
}
