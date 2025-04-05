package com.example.config;

import com.example.domain.Customer;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerItemPreparedStmSetter implements ItemPreparedStatementSetter<Customer> {

		public void setValues(Customer result, PreparedStatement ps) throws SQLException {
			ps.setLong(1, result.getId());
			ps.setDate(2, java.sql.Date.valueOf(result.getBirthdate()));
			ps.setString(3, result.getFirstName());
			ps.setString(4, result.getLastName());
		}
	}