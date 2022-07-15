package com.example;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MyJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public ItemReader<Person> itemReader() {
		Person foo = new Person(1, "foo");
		Person bar = new Person(2, "bar");
		Person bazz = new Person(3, "bazz");
		return new ListItemReader<>(Arrays.asList(foo, bar, bazz));
	}

	@Bean
	public ItemWriter<Person> updateItemWriter() {
		return new UpdateItemWriter(dataSource);
	}

	@Bean
	public ItemWriter<Person> insertItemWriter() {
		return new InsertItemWriter(dataSource);
	}
	
	@Bean
	public CompositeItemWriter<Person> compositeItemWriter(){
		CompositeItemWriter<Person> compositeItemWriter = new CompositeItemWriter<>();
		compositeItemWriter.setDelegates(Arrays.asList(updateItemWriter(), insertItemWriter()));
		return compositeItemWriter;
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory
                .get("step").<Person, Person>chunk(2)
                .reader(itemReader())
                .writer(compositeItemWriter())
                .faultTolerant()
                .skip(IllegalStateException.class)
                .skipLimit(10)
                .build();
	}
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}

}
