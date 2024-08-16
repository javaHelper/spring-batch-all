package com.example;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

public class InsertItemWriter implements ItemWriter<Person> {

	private JdbcTemplate jdbcTemplate;

	public InsertItemWriter(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void write(List<? extends Person> items) {
		System.out.println("--- Insert Item Writer" + items.toString());
		
		for (Person person : items) {
			if ("bar".equalsIgnoreCase(person.getName())) {
				jdbcTemplate.update("INSERT INTO person (id, name) VALUES (?, ?)", person.getId(), person.getName());
				System.out.println("####### Performing Rollbacl  #########");
				throw new IllegalStateException("Something went wrong!");
			}
			//jdbcTemplate.update("INSERT INTO person (id, name) VALUES (?, ?)", person.getId(), person.getName());
		}
		
	}
}