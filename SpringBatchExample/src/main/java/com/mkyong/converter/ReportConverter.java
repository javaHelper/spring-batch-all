package com.mkyong.converter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mkyong.model.Report;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

//http://xstream.codehaus.org/converter-tutorial.html
public class ReportConverter implements Converter {
	private static final Logger logger = LoggerFactory.getLogger(ReportConverter.class);
	private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Override
	public boolean canConvert(Class type) {
		//we only need "Report" object
		return type.equals(Report.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		
		Report report = new Report();
		//get attribute
		report.setId(Integer.valueOf(reader.getAttribute("id")));
		
		reader.moveDown(); //get impression
		report.setImpression(Long.parseLong(reader.getValue()));

		reader.moveUp();
		reader.moveDown();
		report.setClicks(Integer.parseInt(reader.getValue()));
		
		reader.moveUp();
		reader.moveDown();
		report.setEarning(new BigDecimal(reader.getValue()));
		
		reader.moveUp();
		reader.moveDown();
		
		try {
			report.setDate(LocalDate.parse(reader.getValue(), DT_FORMATTER));
		} catch (Exception e) {
			logger.error("Exception while parsing date {}", e.getMessage());
		}
		logger.info("ReportConverter | unmarshal {}", report);
        return report;
	}
}