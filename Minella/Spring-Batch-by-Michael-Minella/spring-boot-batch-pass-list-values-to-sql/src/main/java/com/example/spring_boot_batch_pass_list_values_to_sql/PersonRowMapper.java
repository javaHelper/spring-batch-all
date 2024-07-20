package com.example.spring_boot_batch_pass_list_values_to_sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PersonRowMapper implements RowMapper<Person> {

	@Override
	public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Person person = new Person();
		person.setId(resultSet.getInt("id"));
		person.setName(resultSet.getString("name"));
		return person;
	}

}