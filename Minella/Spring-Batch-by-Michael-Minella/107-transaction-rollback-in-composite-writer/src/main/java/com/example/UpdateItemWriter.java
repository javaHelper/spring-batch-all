package com.example;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

public class UpdateItemWriter implements ItemWriter<Person> {

	private JdbcTemplate jdbcTemplate;

	public UpdateItemWriter(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void write(List<? extends Person> items) {
		System.out.println("--- Updated Item Writer"+ items.toString());
		for (Person person : items) {
			if ("foo".equalsIgnoreCase(person.getName())) {
				jdbcTemplate.update("UPDATE person SET name = 'foo!!' WHERE id = 1");
			}
		}
	}
}