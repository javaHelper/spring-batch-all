package com.example.writer;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class EmployeeWriter implements ItemWriter<EmployeeWriter> {


    @Override
    public void write(List<? extends EmployeeWriter> items) throws Exception {
        System.out.println(items);
    }
}