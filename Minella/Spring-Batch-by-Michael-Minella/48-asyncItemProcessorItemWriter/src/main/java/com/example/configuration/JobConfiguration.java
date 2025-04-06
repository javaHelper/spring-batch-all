package com.example.configuration;

import com.example.domain.Customer;
import com.example.mapper.CustomerRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@SuppressWarnings({"rawtypes","unchecked"})
@Configuration
public class JobConfiguration {
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager manager;
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public JdbcPagingItemReader<Customer> customerPagingItemReader(){
		// Sort Keys
		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("id", Order.ASCENDING);
		
		// MySQL implementation of a PagingQueryProvider using database specific features.
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");
		queryProvider.setSortKeys(sortKeys);

		return new JdbcPagingItemReaderBuilder<Customer>()
				.name("customerPagingItemReader")
				.dataSource(this.dataSource)
				.fetchSize(1000)
				.rowMapper(new CustomerRowMapper())
				.queryProvider(queryProvider)
				.build();
	}
	
	@Bean
	public ItemProcessor itemProcessor(){
		return (ItemProcessor<Customer, Customer>) item -> {
			Thread.sleep(new Random().nextInt(10));
			return Customer.builder()
					.id(item.getId())
					.firstName(item.getFirstName())
					.lastName(item.getLastName())
					.birthdate(item.getBirthdate())
					.build();
		};
	}
	
	@Bean
	public AsyncItemProcessor asyncItemProcessor() throws Exception{
		AsyncItemProcessor<Customer, Customer> asyncItemProcessor = new AsyncItemProcessor<>();
		asyncItemProcessor.setDelegate(itemProcessor());
		asyncItemProcessor.setTaskExecutor(new SimpleAsyncTaskExecutor());
		asyncItemProcessor.afterPropertiesSet();
		return asyncItemProcessor;
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
	public AsyncItemWriter<Customer> asyncItemWriter() throws Exception{
		AsyncItemWriter<Customer> asyncItemWriter = new AsyncItemWriter<>();
		asyncItemWriter.setDelegate(customerItemWriter());
		asyncItemWriter.afterPropertiesSet();
		return asyncItemWriter;  
	}
	
	@Bean
	public Step step1() throws Exception {
		return new StepBuilder("step1", jobRepository)
				.chunk(1000, manager)
				.reader(customerPagingItemReader())
				.processor(asyncItemProcessor())
				.writer(asyncItemWriter())
				.build();
	}
	
	@Bean
	public Job job() throws Exception {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.build();
	}
}