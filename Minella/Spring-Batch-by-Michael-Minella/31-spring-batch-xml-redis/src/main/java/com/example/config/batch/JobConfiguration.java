package com.example.config.batch;

import com.example.domain.Customer;
import com.example.writer.CustomerWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobConfiguration {
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager manager;

	@Bean
	public StaxEventItemReader<Customer> customerItemReader(){
		Map<String, Class> aliases = new HashMap<>();
		aliases.put("customer", Customer.class);
		
		CustomerConverter converter = new CustomerConverter();

		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliases);
		marshaller.setConverters(converter);
		marshaller.setAnnotatedClasses(Customer.class);
		marshaller.getXStream().allowTypes(new Class[]{Customer.class});

		StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
		reader.setResource(new ClassPathResource("/data/customer.xml"));
		reader.setFragmentRootElementName("customer");
		reader.setUnmarshaller(marshaller);
		
		return reader;
	}
	
	@Bean
	public CustomerWriter customerWriter() {
		return new CustomerWriter();
	}
	
	@Bean
	public Step step1() throws Exception {
		return new StepBuilder("step1", jobRepository)
				.<Customer, Customer>chunk(200, manager)
				.reader(customerItemReader())
				.writer(customerWriter())
				.build();
	}
	
	@Bean
	public Job job() throws Exception {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.build();
	}
}