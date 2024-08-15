package com.example.scriptitemprocessorjob;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zip;

	public Customer(Customer original) {
		this.firstName = original.getFirstName();
		this.middleInitial = original.getMiddleInitial();
		this.lastName = original.getLastName();
		this.address = original.getAddress();
		this.city = original.getCity();
		this.state = original.getState();
		this.zip = original.getZip();
	}
}
