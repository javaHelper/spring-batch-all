package com.example.multifilejob;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	private String accountNumber;
	private Date transactionDate;
	private Double amount;
}
