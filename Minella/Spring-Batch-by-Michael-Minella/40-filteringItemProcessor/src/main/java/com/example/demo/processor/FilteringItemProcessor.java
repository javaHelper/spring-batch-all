package com.example.demo.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.model.Customer;

public class FilteringItemProcessor implements ItemProcessor<Customer, Customer> {
	@Override
	public Customer process(Customer item) throws Exception {

		if(item.getId() % 2 == 0) {
			return null;
		}
		else {
			return item;
		}
	}
}
