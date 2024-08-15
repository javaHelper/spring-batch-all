package com.example;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EmployeeFieldSetMapper implements FieldSetMapper<Employee> {
    @Override
    public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
        return Employee.builder()
                .empId(fieldSet.readRawString("empId"))
                .firstName(fieldSet.readRawString("firstName"))
                .lastName(fieldSet.readRawString("lastName"))
                .role(fieldSet.readRawString("role"))
                .build();
    }
}