package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.StoredProcedureItemReader;
import org.springframework.batch.item.database.builder.StoredProcedureItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Types;

@SpringBootApplication
public class StoredProcedureJobApplication {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager manager;

	@Bean
	@StepScope
	public StoredProcedureItemReader<Customer> customerItemReader(DataSource dataSource,
																  @Value("#{jobParameters['city']}") String city) {

		return new StoredProcedureItemReaderBuilder<Customer>()
				.name("customerItemReader")
				.dataSource(dataSource)
				.procedureName("customer_list")
				.parameters(new SqlParameter[]{new SqlParameter("cityOption", Types.VARCHAR)})
				.preparedStatementSetter(new ArgumentPreparedStatementSetter(new Object[]{city}))
				.rowMapper(new CustomerRowMapper())
				.build();
	}

	@Bean
	public ItemWriter<Customer> itemWriter() {
		return (items) -> items.forEach(System.out::println);
	}

	@Bean
	public Step copyFileStep() {
		return new StepBuilder("copyFileStep", jobRepository)
				.<Customer, Customer>chunk(10, manager)
				.reader(customerItemReader(null, null))
				.writer(itemWriter())
				.build();
	}

	@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(copyFileStep())
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(StoredProcedureJobApplication.class, "city=Philadelphia");
	}
}