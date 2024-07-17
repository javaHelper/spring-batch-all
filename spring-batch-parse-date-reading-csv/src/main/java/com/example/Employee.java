package com.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
@Data
@Builder
public class Employee {
    private Long empId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String joiningDate;
}