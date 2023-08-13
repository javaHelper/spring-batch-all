package com.example.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@EnableBatchProcessing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SplitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplitApplication.class, args);
	}

}
