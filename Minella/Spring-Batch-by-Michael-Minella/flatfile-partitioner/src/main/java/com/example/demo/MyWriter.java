package com.example.demo;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;


public class MyWriter implements ItemWriter<Customer>{

	@Override
	public void write(Chunk<? extends Customer> chunk) throws Exception {
		System.out.println("Size: "+ chunk.size());
	}
}
