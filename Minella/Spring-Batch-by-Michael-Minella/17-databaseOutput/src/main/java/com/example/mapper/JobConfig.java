package com.example.mapper;


import com.example.model.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JobConfig {
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager manager;
	
	@Autowired
	private DataSource dataSource;
	
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
				.lineTokenizer(tokenizer)
				.lineMapper(customerLineMapper)
				.build();
	}
	
	@Bean
	public JdbcBatchItemWriter<Customer> customerItemWriter(){
		return new JdbcBatchItemWriterBuilder<Customer>()
				.dataSource(this.dataSource)
				.sql("INSERT INTO test.customer VALUES (:id, :firstName, :lastName, :birthdate)")
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.build();
	}
	
	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.<Customer, Customer> chunk(10, manager)
				.reader(customerItemReader())
				.writer(customerItemWriter())
				.build();
	}
	
	@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.build();
	}	
}