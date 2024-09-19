package com.example.demo;

import java.util.List;

import org.springframework.batch.item.ItemWriter;


public class MyWriter implements ItemWriter<Customer>{

	@Override
	public void write(List<? extends Customer> items) throws Exception {
		System.out.println("Size: "+ items.size());
	}

}
