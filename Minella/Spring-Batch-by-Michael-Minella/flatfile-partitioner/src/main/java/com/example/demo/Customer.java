package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private String contactNo;
	private String country;
	private String dob;
}