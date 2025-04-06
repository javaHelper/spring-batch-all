package com.example.configuration;

import com.example.domain.Customer;
import com.example.mapper.CustomerRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobConfiguration {
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager manager;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public JdbcPagingItemReader<Customer> pagingItemReader(){
		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("id", Order.ASCENDING);
		
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("select id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");
		queryProvider.setSortKeys(sortKeys);

		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(this.dataSource);
		reader.setFetchSize(1000);
		reader.setRowMapper(new CustomerRowMapper());
		reader.setQueryProvider(queryProvider);
		return reader;
	}
	
	@Bean
	public JdbcBatchItemWriter<Customer> customerItemWriter(){
		return new JdbcBatchItemWriterBuilder<Customer>()
				.dataSource(this.dataSource)
				.sql("INSERT INTO new_customer VALUES (:id, :firstName, :lastName, :birthdate)")
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.build();
	}
	
	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.<Customer, Customer>chunk(1000, manager)
				.reader(pagingItemReader())
				.writer(customerItemWriter())
				.taskExecutor(new SimpleAsyncTaskExecutor())
				.build();
	}
	
	@Bean
	public Job job() {
		return new JobBuilder("job",jobRepository)
				.start(step1())
				.build();
	}
}