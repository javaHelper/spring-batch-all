package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class CompositeItemReaderDemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CompositeItemReaderDemoApplication.class, args);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void run(String... args) throws Exception {
		Integer persons = jdbcTemplate.queryForObject("select count(*) from person_target", Integer.class);
		System.out.println("Persons in db = " + persons); // should be 6
	}

}
