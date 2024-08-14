package com.example.aggregator;

import org.springframework.batch.item.file.transform.LineAggregator;

import com.example.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Interface used to create string representing object.
 */
public class CustomLineAggregator implements LineAggregator<Customer> {
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String aggregate(Customer item) {
		try {
			return objectMapper.writeValueAsString(item);
		} catch (Exception e) {
			throw new RuntimeException("Unable to serialize Customer", e);
		}
	}
}
