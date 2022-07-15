package com.example;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class TransactionFieldSetMapper implements FieldSetMapper<Transaction> {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public Transaction mapFieldSet(FieldSet fieldSet) {
        Transaction trans = new Transaction();
        trans.setAccountNumber(fieldSet.readString("accountNumber"));
        trans.setAmount(fieldSet.readDouble("amount"));
        trans.setTransactionDate(fieldSet.readDate("transactionDate", DATE_FORMAT));
        return trans;
    }
}