package com.example.job;

import java.util.HashMap;
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
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;

import com.example.model.Employee;
import com.example.rowmapper.EmployeeMapper;

@Configuration
@EnableBatchProcessing
public class EmpoloyeeJob {

	@Bean
	public JdbcCursorItemReader<Employee> itemReader(DataSource dataSource) {
		String sql = "select * from employee where age = :age and firstName = :firstName";
		Map<String, Object> namedParameters = new HashMap<String, Object>() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
			put("age", 22);
			put("firstName", "John");
		}};

		String preparedSql = NamedParameterUtils.substituteNamedParameters(sql, new MapSqlParameterSource(namedParameters));
		PreparedStatementSetter preparedStatementSetter = new ArgumentPreparedStatementSetter(NamedParameterUtils.buildValueArray(sql, namedParameters));

		return new JdbcCursorItemReaderBuilder<Employee>()
				.name("personItemReader")
				.dataSource(dataSource)
				.rowMapper(new EmployeeMapper())
				.sql(preparedSql)
				.preparedStatementSetter(preparedStatementSetter)
				.build();
	}
	
	@Bean
	public ItemWriter<Employee> itemWriter() {
		return items -> {
			for (Employee item : items) {
				System.out.println("item = " + item);
			}
		};
	}

	@Bean
	public Job job(JobBuilderFactory jobs, StepBuilderFactory steps, DataSource dataSource) {
		return jobs.get("job")
				.start(steps.get("step")
						.<Employee, Employee>chunk(5)
						.reader(itemReader(dataSource))
						.writer(itemWriter())
						.build())
				.build();
	}
}
