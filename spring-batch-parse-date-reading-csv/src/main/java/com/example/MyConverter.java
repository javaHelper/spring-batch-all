package com.example;

import org.springframework.core.convert.converter.Converter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MyConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        Locale locale = new Locale("en", "US");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy h:mm:ss:SSSa").withLocale(locale);
        LocalDateTime ldt = LocalDateTime.parse(s, formatter);
        LocalDate ld = ldt.toLocalDate();
        String output = ld.format(DateTimeFormatter.BASIC_ISO_DATE);
        return ldt.toLocalDate();
    }
}