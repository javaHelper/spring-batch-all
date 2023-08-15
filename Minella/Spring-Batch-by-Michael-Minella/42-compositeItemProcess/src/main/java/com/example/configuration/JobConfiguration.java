package com.example.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.aggregator.CustomLineAggregator;
import com.example.mapper.CustomerRowMapper;
import com.example.model.Customer;
import com.example.processor.FilteringItemProcessor;
import com.example.processor.UpperCaseItemProcessor;

@Configuration
public class JobConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
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
		return new JdbcPagingItemReaderBuilder<Customer>()
				.name("customerPagingItemReader")
				.dataSource(this.dataSource)
				.fetchSize(1000)
				.rowMapper(new CustomerRowMapper())
				.queryProvider(queryProvider)
				.build();
	}
	
	
	@Bean
	public FlatFileItemWriter<Customer> customerItemWriter() throws Exception{
		String customerOutputPath = File.createTempFile("customerOutput", ".out").getAbsolutePath();
		System.out.println(">> Output Path = "+customerOutputPath);

		return new FlatFileItemWriterBuilder<Customer>()
				.name("customerItemWriter")
				.lineAggregator(new CustomLineAggregator())
				.resource(new FileSystemResource(customerOutputPath))
				.build();
	}
	
	@Bean
	public CompositeItemProcessor<Customer, Customer> compositeItemProcessor() throws Exception{
		List<ItemProcessor<Customer, Customer>> delegates = new ArrayList<>();
		delegates.add(new FilteringItemProcessor());
		delegates.add(new UpperCaseItemProcessor());

		// Establishes the ItemProcessor delegates that will work on the item to be processed.
		return new CompositeItemProcessorBuilder<Customer, Customer>()
				.delegates(delegates)
				.build();
	}
	
	
	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer>chunk(100)
				.reader(customerPagingItemReader())
				.processor(compositeItemProcessor())
				.writer(customerItemWriter())
				.build();
	}
	
	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("job")
				.incrementer(new RunIdIncrementer())
				.start(step1())
				.build();
	}
}
