package com.mkyong.model;

import java.math.BigDecimal;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@XStreamAlias("report")
public class Report {
	private int id;
	private Date date;
	private long impression;
	private int clicks;
	private BigDecimal earning;
}