package com.example.rowmapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.jdbc.core.RowMapper;

import com.example.model.Payment;

public class PaymentRowMapper implements RowMapper<Payment>{

	@Override
	public Payment mapRow(ResultSet resultSet, int arg1) throws SQLException {
		return toPayment(resultSet);
	}

	private Payment toPayment(ResultSet resultSet) throws SQLException {
		Payment payment = new Payment();
        payment.setPaymentId(resultSet.getLong("paymentId"));
        payment.setAmount(resultSet.getDouble("amount"));
        payment.setCustomerId(resultSet.getLong("customerId"));
        payment.setPaymentDate(getPaymentDate(resultSet.getDate("paymentDate")));
        return payment;
    }

	private LocalDate getPaymentDate(Date paymentDate) {
		return paymentDate.toLocalDate();
	}
}
