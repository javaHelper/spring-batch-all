package com.example.mapper;

import com.example.model.Employee;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EmployeeFieldSetMapper implements FieldSetMapper<Employee> {
    @Override
    public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
        Employee employee = new Employee();
        employee.setEmpId(fieldSet.readLong(0));
        employee.setFirstName(fieldSet.readString(1));
        employee.setLastName(fieldSet.readString(2));
        employee.setAge(fieldSet.readInt(3));
        employee.setEmail(fieldSet.readString(4));
        return employee;
    }
}