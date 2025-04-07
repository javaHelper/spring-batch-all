package com.example.configuration;

import com.example.domain.Customer;
import com.example.mapper.CustomerRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	public ColumnRangePartitioner partitioner() {
		ColumnRangePartitioner columnRangePartitioner = new ColumnRangePartitioner();
		columnRangePartitioner.setColumn("id");
		columnRangePartitioner.setDataSource(dataSource);
		columnRangePartitioner.setTable("customer");
		return columnRangePartitioner;
	}

	@Bean
	@StepScope
	public JdbcPagingItemReader<Customer> pagingItemReader(
			@Value("#{stepExecutionContext['minValue']}") Long minValue,
			@Value("#{stepExecutionContext['maxValue']}") Long maxValue) {

		System.out.println("reading " + minValue + " to " + maxValue);

		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("id", Order.ASCENDING);
		
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");
		queryProvider.setWhereClause("where id >= " + minValue + " and id < " + maxValue);
		queryProvider.setSortKeys(sortKeys);
		
		return new JdbcPagingItemReaderBuilder<Customer>()
				.name("pagingItemReader")
				.dataSource(this.dataSource)
				.fetchSize(1000)
				.rowMapper(new CustomerRowMapper())
				.queryProvider(queryProvider)
				.build();
	}
	
	
	@Bean
	@StepScope
	public JdbcBatchItemWriter<Customer> customerItemWriter(){
		return new JdbcBatchItemWriterBuilder<Customer>()
				.dataSource(this.dataSource)
				.sql("INSERT INTO new_customer VALUES (:id, :firstName, :lastName, :birthdate)")
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.build();
	}
	
	// Master
	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.partitioner(slaveStep().getName(), partitioner())
				.step(slaveStep())
				.gridSize(4)
				.taskExecutor(new SimpleAsyncTaskExecutor())
				.build();
	}
	
	// slave step
	@Bean
	public Step slaveStep() {
		return new StepBuilder("slaveStep", jobRepository)
				.<Customer, Customer>chunk(1000, manager)
				.reader(pagingItemReader(null, null))
				.writer(customerItemWriter())
				.build();
	}
	
	@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.build();
	}
}