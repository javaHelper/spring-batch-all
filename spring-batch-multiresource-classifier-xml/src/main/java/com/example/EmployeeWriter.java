package com.example;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class EmployeeWriter implements ItemWriter<Employee>{
	@Override
	public void write(List<? extends Employee> items) throws Exception {
		items.forEach(e -> System.out.println(e));
	}
}
