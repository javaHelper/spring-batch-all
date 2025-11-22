package com.example.job;

import com.example.model.Employee;
import com.example.rowmapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Configuration
@Slf4j
public class EmployeeJob {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager manager;

	@Bean
	public JdbcCursorItemReader<Employee> itemReader(DataSource dataSource) {
		String sql = "SELECT * FROM employee where age in (:ageList)";
		
		Map<String, List<Integer>> namedParameters = new HashMap<>();
		namedParameters.put("ageList", Arrays.asList(22,44));
		
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		MapSqlParameterSource paramSource = new MapSqlParameterSource(namedParameters);
		
		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
		List<SqlParameter> declaredParams = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
		
		Object[] parameters = NamedParameterUtils.buildValueArray(parsedSql, paramSource, declaredParams);
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, declaredParams);
		PreparedStatementSetter preparedStatementSetter = factory.newPreparedStatementSetter(parameters);
		
		log.info("sql: {}", sqlToUse);
        log.info("parameters: {}", parameters);
		
		return new JdbcCursorItemReaderBuilder<Employee>()
				.name("employeeItemReader")
				.dataSource(dataSource)
				.rowMapper(new EmployeeMapper())
				.sql(sqlToUse)
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
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(new StepBuilder("step", jobRepository)
						.<Employee, Employee>chunk(5, manager)
						.reader(itemReader(dataSource))
						.writer(itemWriter())
						.build())
				.build();
	}
}