package com.example;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@XmlType(name = "transaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {
    private String accountNumber;
    
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime transactionDate;
    private Double amount;
}
