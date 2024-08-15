package com.example.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// https://stackoverflow.com/questions/52363472/spring-batch-two-steps-with-one-decider/52365730#52365730
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableBatchProcessing
public class DeciderCheckmarksApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeciderCheckmarksApplication.class, args);
	}

}
