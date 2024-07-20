package com.example.rowmapper;

import com.example.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee result = new Employee();
        result.setEmpId(rs.getLong("empId"));
        result.setFirstName(rs.getString("firstName"));
        result.setLastName(rs.getString("lastName"));
        result.setAge(rs.getInt("age"));
        result.setEmail(rs.getString("email"));
        return result;
    }
}