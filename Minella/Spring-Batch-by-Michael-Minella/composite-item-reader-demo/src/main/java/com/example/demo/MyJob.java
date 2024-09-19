package com.example.demo;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class MyJob {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public FlatFileItemReader<Person> itemReader1() {
		return new FlatFileItemReaderBuilder<Person>()
				.name("personItemReader")
				.resource(new FileSystemResource("persons1.csv"))
				.delimited()
				.names("id", "name")
				.targetType(Person.class)
				.build();
	}

	@Bean
	public FlatFileItemReader<Person> itemReader2() {
		return new FlatFileItemReaderBuilder<Person>()
				.name("personItemReader")
				.resource(new FileSystemResource("persons2.csv"))
				.delimited()
				.names("id", "name")
				.targetType(Person.class)
				.build();
	}
	
	
	@Bean
	public JdbcCursorItemReader<Person> itemReader3() {
		String sql = "select * from person_source";
		return new JdbcCursorItemReaderBuilder<Person>()
				.name("personItemReader")
				.dataSource(dataSource)
				.sql(sql)
				.beanRowMapper(Person.class)
				.build();
	}
	
	@Bean
	public JdbcBatchItemWriter<Person> itemWriter() {
		String sql = "insert into person_target (id, name) values (:id, :name)";
		return new JdbcBatchItemWriterBuilder<Person>()
				.dataSource(dataSource)
				.sql(sql)
				.beanMapped()
				.build();
	}
	
	
	@Bean
	public CompositeItemReader<Person> itemReader() {
		return new CompositeItemReader<>(Arrays.asList(itemReader1(), itemReader2(), itemReader3()));
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Person, Person>chunk(5)
				.reader(itemReader())
				.writer(itemWriter())
				.build();
	}
	
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("job1")
				.start(step1())
				.build();
	}
}
