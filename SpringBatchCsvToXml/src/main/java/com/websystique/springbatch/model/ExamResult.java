package com.websystique.springbatch.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@XmlRootElement(name = "ExamResult")
@XmlAccessorType (XmlAccessType.FIELD)
public class ExamResult {

	@XmlElement(name = "studentName")
	private String studentName;

	@XmlElement(name = "dob")
	@XmlJavaTypeAdapter(type = LocalDate.class, value = com.websystique.springbatch.LocalDateAdapter.class)
	private LocalDate dob;

	@XmlElement(name = "percentage")
	private double percentage;
}
