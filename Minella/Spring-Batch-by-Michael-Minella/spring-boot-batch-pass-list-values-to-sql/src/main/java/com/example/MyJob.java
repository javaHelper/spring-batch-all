package com.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
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
@EnableBatchProcessing
@Slf4j
public class MyJob {

	@Bean
	public JdbcCursorItemReader<Person> itemReader(DataSource dataSource) {
		String sql = "select * from person where id in (:value)";
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
	public Job job(JobBuilderFactory jobs, StepBuilderFactory steps, DataSource dataSource) {
		return jobs.get("job")
				.start(steps.get("step")
						.<Person, Person>chunk(5)
						.reader(itemReader(dataSource))
						.writer(itemWriter())
						.build())
				.build();
	}
}
