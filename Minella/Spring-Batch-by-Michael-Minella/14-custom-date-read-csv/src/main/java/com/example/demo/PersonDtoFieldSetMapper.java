package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PersonDtoFieldSetMapper implements FieldSetMapper<PersonDto>{
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	

	
	@Override
	public PersonDto mapFieldSet(FieldSet fieldSet) throws BindException {
		String dob = fieldSet.readRawString("dob");
		
		return PersonDto.builder()
				.id(fieldSet.readRawString("id"))
				.firstName(fieldSet.readRawString("firstName"))
				.lastName(fieldSet.readRawString("lastName"))
				.dob(LocalDate.parse(dob, formatter))
				.build();
	}

}
