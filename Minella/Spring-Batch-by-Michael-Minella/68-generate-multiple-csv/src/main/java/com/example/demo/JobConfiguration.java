package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class JobConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Bean
	public JdbcPagingItemReader<Person> itemReader() {
		return new JdbcPagingItemReaderBuilder<Person>()
				.name("personItemReader")
				.dataSource(this.dataSource)
				.beanRowMapper(Person.class)
				.selectClause("select *")
				.fromClause("from person")
				.sortKeys(new HashMap<String, Order>() {
					private static final long serialVersionUID = 1L;
					{ 
						put("id", Order.DESCENDING);
					}})
				.build();
	}

	@Bean
	public Step step1(){
		return stepBuilderFactory.get("step1")
				.tasklet(new MyTasklet(itemReader()))
				.build();
	}

	@Bean
	public Job job(){
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
}
