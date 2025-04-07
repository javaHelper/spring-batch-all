package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestartApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestartApplication.class, "ran=Hello");
	}
}