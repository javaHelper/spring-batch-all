package com.example.compositeitemwriterjob;

import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        System.out.println(item);
        return item;
    }
}