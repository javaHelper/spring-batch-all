package com.example;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@XmlRootElement(name = "ExamResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExamResult {
	@XmlElement(name = "studentName")
	private String studentName;

	@XmlElement(name = "dob")
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	private LocalDate dob;

	@XmlElement(name = "percentage")
	private double percentage;
}