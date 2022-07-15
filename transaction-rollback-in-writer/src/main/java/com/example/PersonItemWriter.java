package com.example;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

public class PersonItemWriter implements ItemWriter<Person>{

	private JdbcTemplate jdbcTemplate;

    PersonItemWriter(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public void write(List<? extends Person> items) throws Exception {
		System.out.println("---- Writing items: "); 
		items.forEach(System.out::println);
		
        for (Person person : items) {
            if ("bar".equalsIgnoreCase(person.getName())) {
                System.out.println("Throwing exception: No bars here!");
                throw new IllegalStateException("No bars here!");
            }
            jdbcTemplate.update("INSERT INTO person (id, name) VALUES (?, ?)", person.getId(), person.getName());
        }
	}

}
