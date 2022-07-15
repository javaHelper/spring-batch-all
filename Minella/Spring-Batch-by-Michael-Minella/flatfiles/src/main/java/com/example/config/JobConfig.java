package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.domain.Customer;
import com.example.mapper.CustomerFieldSetMapper;

@Configuration
public class JobConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public FlatFileItemReader<Customer> customerItemReader(){
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("id", "firstName", "lastName", "birthdate");

		DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();
		customerLineMapper.setLineTokenizer(tokenizer);
		customerLineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
		customerLineMapper.afterPropertiesSet();

		return new FlatFileItemReaderBuilder<Customer>()
				.name("customerItemReader")
				.linesToSkip(1)
				.resource(new ClassPathResource("/data/customer.csv"))
				.lineMapper(customerLineMapper)
				.build();
	}
	
	@Bean
	public ItemWriter<Customer> customerItemWriter(){
		return items -> {
			for (Customer customer : items) {
				System.out.println(customer.toString());
			}
		};
	}
	
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer>chunk(10)
				.reader(customerItemReader())
				.writer(customerItemWriter())
				.build();
	}
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
}
