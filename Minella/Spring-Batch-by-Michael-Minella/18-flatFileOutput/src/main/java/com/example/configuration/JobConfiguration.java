package com.example.configuration;

import com.example.lineAggregator.CustomerLineAggregator;
import com.example.mapper.CustomerRowMapper;
import com.example.model.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
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
	public JdbcPagingItemReader<Customer> customerPagingItemReader(){
		// Sort Keys
		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("id", Order.ASCENDING);

		// MySQL implementation of a PagingQueryProvider using database specific features.
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");
		queryProvider.setSortKeys(sortKeys);

		// reading database records using JDBC in a paging fashion
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(this.dataSource);
		reader.setFetchSize(1000);
		reader.setRowMapper(new CustomerRowMapper());
		reader.setQueryProvider(queryProvider);
		return reader;
	}
	
	
	@Bean
	public FlatFileItemWriter<Customer> customerItemWriter() throws Exception{
		String customerOutputPath = File.createTempFile("customerOutput", ".out").getAbsolutePath();
		System.out.println(">> Output Path = "+customerOutputPath);
		
		FlatFileItemWriter<Customer> itemWriter = new FlatFileItemWriter<>();
		
		//A LineAggregator implementation that simply calls Object.toString() on the given object
		//itemWriter.setLineAggregator(new PassThroughLineAggregator<>());
		
		//Alternate ways
		itemWriter.setLineAggregator(new CustomerLineAggregator());
		itemWriter.setResource(new FileSystemResource(customerOutputPath));
		itemWriter.afterPropertiesSet();
		return itemWriter;
	}
	
	
	@Bean
	public Step step1() throws Exception {
		return new StepBuilder("step1", jobRepository)
				.<Customer, Customer>chunk(100, manager)
				.reader(customerPagingItemReader())
				.writer(customerItemWriter())
				.build();
	}
	
	@Bean
	public Job job() throws Exception {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.build();
	}
}