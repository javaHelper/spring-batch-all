package com.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Demo {
    public static void main(String[] args) throws java.lang.Exception {
        String input = "Apr  1 2022 6:00:00:000AM".replace("  ", " ");
        Locale locale = new Locale("en", "US");
        DateTimeFormatter formatter = DateTimeFormatter
                        .ofPattern("MMM d yyyy h:mm:ss:SSSa")
                        .withLocale(locale);
        LocalDateTime ldt = LocalDateTime.parse(input, formatter);
        LocalDate ld = ldt.toLocalDate();
        String output = ld.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(output);
    }
}