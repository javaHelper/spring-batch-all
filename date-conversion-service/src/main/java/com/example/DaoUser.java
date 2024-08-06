package com.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DaoUser {
    private String email, fullname, matricule, password, username;
    private java.sql.Date dateIntegration;
}