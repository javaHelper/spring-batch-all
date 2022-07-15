package com.example;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {

	public Customer mapFieldSet(FieldSet fieldSet) {
		Customer customer = new Customer();
		customer.setFirstName(fieldSet.readString("firstName"));
		customer.setMiddleInitial(fieldSet.readString("middleInitial"));
		customer.setLastName(fieldSet.readString("lastName"));
		customer.setAddressNumber(fieldSet.readString("addressNumber"));
		customer.setStreet(fieldSet.readString("street"));
		customer.setCity(fieldSet.readString("city"));
		customer.setState(fieldSet.readString("state"));
		customer.setZipCode(fieldSet.readString("zipCode"));
		return customer;
	}
}