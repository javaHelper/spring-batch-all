package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MyConfig {

	private static final String READ_SQL = "select * from address where personId = ?";

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager manager;

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
            @Override
            public Person process(Person person) throws Exception {
                Address address = jdbcTemplate.queryForObject(READ_SQL, new Object[]{person.getId()},
                        new BeanPropertyRowMapper<>(Address.class));
                person.setAddress(address);
                return person;
            }
        };
	}

	@Bean
	public ItemWriter<Person> itemWriter() {
		return new ItemWriter<Person>() {
            @Override
            public void write(Chunk<? extends Person> items) throws Exception {
                for (Person item : items) {
                    System.out.println("item = " + item);
                }
            }
        };
	}

	@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(new StepBuilder("step", jobRepository)
						.<Person, Person>chunk(2, manager)
						.reader(itemReader())
						.processor(itemProcessor())
						.writer(itemWriter())
						.build())
				.build();
	}
}