package com.example;

import org.springframework.core.convert.converter.Converter;

import java.sql.Date;
import java.time.LocalDate;

public class MyConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        Date oldDate = Date.valueOf(s);
        return oldDate.toLocalDate();
    }
}