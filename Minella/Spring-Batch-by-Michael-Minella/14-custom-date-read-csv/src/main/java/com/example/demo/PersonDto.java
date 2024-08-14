package com.example.demo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonDto {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
