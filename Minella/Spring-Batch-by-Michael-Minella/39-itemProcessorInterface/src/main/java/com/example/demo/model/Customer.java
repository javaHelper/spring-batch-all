package com.example.demo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
	private  long id;
	private  String firstName;
	private  String lastName;
	private  Date birthdate;
}

