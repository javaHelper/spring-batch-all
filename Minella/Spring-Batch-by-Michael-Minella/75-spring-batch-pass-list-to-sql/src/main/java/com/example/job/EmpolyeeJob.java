package com.example.job;

import com.example.model.Employee;
import com.example.rowmapper.EmployeeMapper;
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
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class EmpolyeeJob {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    public JdbcCursorItemReader<Employee> itemReader(DataSource dataSource) {
        String sql = "select * from employee where age = :age and firstName = :firstName";
        Map<String, Object> namedParameters = new HashMap<>() {
            private static final long serialVersionUID = 1L;

            {
                put("age", 22);
                put("firstName", "John");
            }
        };

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