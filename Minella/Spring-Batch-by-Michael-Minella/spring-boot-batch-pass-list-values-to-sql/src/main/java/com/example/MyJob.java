package com.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MyJob {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;

	@Bean
	public JdbcCursorItemReader<Person> itemReader() {
		String sql = "select * from test.person where id in (:value)";
		Map<String, Object> namedParameters = new HashMap<>() {
			private static final long serialVersionUID = 1L;

		{
			put("value", Arrays.asList(2, 3, 4,5));
		}};

		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource(namedParameters);
		
		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
		
		List<SqlParameter> declaredParams = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
		
		Object[] parameters = NamedParameterUtils.buildValueArray(parsedSql, paramSource, declaredParams);
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, declaredParams);
		PreparedStatementSetter preparedStatementSetter = factory.newPreparedStatementSetter(parameters);

		log.info("sql: {}", sqlToUse);
        log.info("parameters: {}", parameters);
		
		return new JdbcCursorItemReaderBuilder<Person>()
				.name("personItemReader")
				.dataSource(dataSource)
				.rowMapper(new PersonRowMapper())
				.sql(sqlToUse)
				.preparedStatementSetter(preparedStatementSetter)
				.build();
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
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Person, Person>chunk(5)
				.reader(itemReader())
				.writer(itemWriter())
				.build();
	}
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
}
