package com.example.demo;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class SysOutItemWriter implements ItemWriter<String> {

	@Override
	public void write(Chunk<? extends String> chunk) throws Exception {
		System.out.println("The size of this chunk was: " + chunk.size());

		chunk.forEach(System.out::println);
	}
}