package com.example;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class EmployeeItemWriter implements ItemWriter<Employee> {
    @Override
    public void write(List<? extends Employee> items) throws Exception {
        System.out.println(items);
    }
}