package com.example;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
