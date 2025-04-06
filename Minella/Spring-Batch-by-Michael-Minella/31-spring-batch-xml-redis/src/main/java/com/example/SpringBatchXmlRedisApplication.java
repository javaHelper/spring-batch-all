package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class SpringBatchXmlRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchXmlRedisApplication.class, args);
	}

}