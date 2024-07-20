package com.example.spring_boot_batch_pass_list_values_to_sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Person {
	private int id;
	private String name;
}
