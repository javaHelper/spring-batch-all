package com.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class RestartApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestartApplication.class, "ran=Hello");
	}
}
