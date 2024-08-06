package com.example;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MyWriter implements ItemWriter<DaoUser> {
    @Override
    public void write(List<? extends DaoUser> items) throws Exception {
        items.forEach(System.out::println);
    }
}