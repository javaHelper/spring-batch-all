package com.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {
    private String empId;
    private String firstName;
    private String lastName;
    private String role;
	@Override
	public String toString() {
		return empId + ","+ firstName+ ","+ lastName+ ","+ role;
	}
    
    
}