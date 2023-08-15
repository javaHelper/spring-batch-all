package com.example.mapper;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.model.Customer;

@Configuration
public class JobConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public FlatFileItemReader<Customer> customerItemReader(){
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] {"id", "firstName", "lastName", "birthdate"});

		DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();
		customerLineMapper.setLineTokenizer(tokenizer);
		customerLineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
		customerLineMapper.afterPropertiesSet();

		return new FlatFileItemReaderBuilder<Customer>()
				.name("customerItemReader")
				.linesToSkip(1)
				.resource(new ClassPathResource("/data/customer.csv"))
				.lineTokenizer(tokenizer)
				.lineMapper(customerLineMapper)
				.build();
	}
	
	@Bean
	public JdbcBatchItemWriter<Customer> customerItemWriter(){
		return new JdbcBatchItemWriterBuilder<Customer>()
				.dataSource(this.dataSource)
				.sql("INSERT INTO CUSTOMER VALUES (:id, :firstName, :lastName, :birthdate)")
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.build();
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer> chunk(10)
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
