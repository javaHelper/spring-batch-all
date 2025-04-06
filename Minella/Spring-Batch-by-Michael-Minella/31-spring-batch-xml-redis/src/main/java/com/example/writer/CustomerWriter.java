package com.example.writer;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

public class CustomerWriter implements ItemWriter<Customer>{
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public void write(Chunk<? extends Customer> chunk) throws Exception {
		customerRepository.saveAll(chunk);
	}
}