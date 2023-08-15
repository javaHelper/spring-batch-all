package com.example.validation;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

import com.example.model.Customer;

public class CustomerValidator implements Validator<Customer>{

	@Override
	public void validate(Customer value) throws ValidationException {
		if(value.getFirstName().startsWith("A") || value.getFirstName().startsWith("J")) {
			System.out.println("The customer record has either A or J" + value.getId());
			throw new ValidationException("First Name that begin with A are invalid"+ value);
		}
	}
}
