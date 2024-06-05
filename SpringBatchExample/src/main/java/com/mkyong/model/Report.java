package com.mkyong.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {
	private Integer id;
	private LocalDate date;
	private Long impression;
	private Integer clicks;
	private BigDecimal earning;
}