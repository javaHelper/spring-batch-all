package com.example.config;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.example.domain.Customer;
import com.example.writer.CustomerWriter;
import com.thoughtworks.xstream.security.AnyTypePermission;

@Configuration
public class JobConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	

	@SuppressWarnings("rawtypes")
	@Bean
	public StaxEventItemReader<Customer> customerItemReader(){
		Map<String, Class> aliases = new HashMap<>();
		aliases.put("customer", Customer.class);
		aliases.put("id", Long.class);
        aliases.put("firstName", String.class);
        aliases.put("lastName", String.class);
        aliases.put("name", String.class);
        aliases.put("birthdate",LocalDate.class);
		
		CustomerConverter converter = new CustomerConverter();

		XStreamMarshaller unmarshaller = new XStreamMarshaller();
		unmarshaller.setAliases(aliases);
		unmarshaller.setConverters(converter);
		unmarshaller.setTypePermissions(AnyTypePermission.ANY);
		
		StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
		reader.setResource(new ClassPathResource("/data/customer.xml"));
		reader.setFragmentRootElementName("customer");
		reader.setUnmarshaller(unmarshaller);
		
		return reader;
	}
	
	@Bean
	public CustomerWriter customerWriter() {
		return new CustomerWriter();
	}
	
	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer>chunk(200)
				.reader(customerItemReader())
				.writer(customerWriter())
				.build();
	}
	
	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
}