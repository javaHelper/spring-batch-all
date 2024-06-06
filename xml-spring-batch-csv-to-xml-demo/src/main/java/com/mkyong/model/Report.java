package com.mkyong.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class Report {
	@XmlAttribute(name = "id")
	private int id;
	
	@XmlElement(name = "sales")
	private BigDecimal sales;
	
	@XmlElement(name = "qty")
	private int qty;
	
	@XmlElement(name = "staffName")
	private String staffName;
	
	private Date date;
}