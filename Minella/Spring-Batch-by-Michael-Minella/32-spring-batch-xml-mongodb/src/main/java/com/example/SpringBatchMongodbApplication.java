package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.repository")
public class SpringBatchMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchMongodbApplication.class, args);
	}
}