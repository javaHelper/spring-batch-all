package com.example;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MyConfig {

	private static final String READ_SQL = "select * from address where personId = ?";

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Bean
	public JdbcCursorItemReader<Person> itemReader() {
		return new JdbcCursorItemReaderBuilder<Person>()
				.name("personItemReader")
				.dataSource(dataSource)
				.sql("select id, name from person")
				.beanRowMapper(Person.class)
				.build();
	}

	@Bean
	public ItemProcessor<Person, Person> itemProcessor() {
		return new ItemProcessor<Person, Person>() {

			@SuppressWarnings("deprecation")
			@Override
			public Person process(Person person) {
				Address address = jdbcTemplate.queryForObject(READ_SQL, new Object[]{person.getId()}, 
						new BeanPropertyRowMapper<>(Address.class));
				person.setAddress(address);
				return person;
			}
		};
	}

	@Bean
	public ItemWriter<Person> itemWriter() {
		return items -> {
			for (Person item : items) {
				System.out.println("item = " + item);
			}
		};
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(stepBuilderFactory.get("step")
						.<Person, Person>chunk(2)
						.reader(itemReader())
						.processor(itemProcessor())
						.writer(itemWriter())
						.build())
				.build();
	}
}
