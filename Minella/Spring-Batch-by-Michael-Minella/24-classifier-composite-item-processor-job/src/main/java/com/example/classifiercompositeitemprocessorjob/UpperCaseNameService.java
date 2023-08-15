package com.example.classifiercompositeitemprocessorjob;

import org.springframework.stereotype.Service;

@Service
public class UpperCaseNameService {

	public Customer upperCase(Customer customer) {
		System.out.println("--------------------");
		Customer newCustomer = new Customer(customer);
		newCustomer.setFirstName(newCustomer.getFirstName().toUpperCase());
		newCustomer.setMiddleInitial(newCustomer.getMiddleInitial().toUpperCase());
		newCustomer.setLastName(newCustomer.getLastName().toUpperCase());
		newCustomer.setCity(newCustomer.getCity().toUpperCase());
		return newCustomer;
	}
}