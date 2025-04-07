package com.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;


@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	private Person() {
	}

	public Person(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Person person = (Person) o;
		return Objects.equals(id, person.id) && Objects.equals(name, person.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}