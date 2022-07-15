package com.example.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class GenerateMultipleCsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateMultipleCsvApplication.class, args);
	}
}
