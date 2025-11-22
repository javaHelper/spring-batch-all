package com.example.demo;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Customer,Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        System.out.println(item.toString());
        return item;
    }
}
