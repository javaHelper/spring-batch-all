package com.example.classifier;

import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import com.example.model.Customer;


/**
 * Interface for a classifier. At its simplest a Classifier is just a map from objects of one type to objects of another type. 
 * Note that implementations can only be serializable if the parameter types are themselves serializable.
 */

public class CustomerClassifier implements Classifier<Customer, ItemWriter<? super Customer>>{
	private static final long serialVersionUID = 1L;

	private ItemWriter<Customer> evenItemWriter;
	private ItemWriter<Customer> oddItemWriter;
	
	public CustomerClassifier(ItemWriter<Customer> evenItemWriter, ItemWriter<Customer> oddItemWriter) {
		this.evenItemWriter = evenItemWriter;
		this.oddItemWriter = oddItemWriter;
	}
	
	@Override
	public ItemWriter<? super Customer> classify(Customer customer) {
		return customer.getId() % 2 == 0 ? evenItemWriter : oddItemWriter;
	}	
}
