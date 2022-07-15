package com.example.demo;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.util.Arrays;

public class PersonMapper implements FieldSetMapper<Person> {
    @Override
    public Person mapFieldSet(FieldSet fieldSet) throws BindException {
        Person p = new Person();
        p.setId(fieldSet.readString(0));
        p.setName(fieldSet.readString(1));
        p.setGender(fieldSet.readString(2));

        // TODO split address as needed
        p.setAddresses(Arrays.asList(fieldSet.readString(3).split(",")));
        p.setCountry(fieldSet.readString(4));
        return p;
    }
}
