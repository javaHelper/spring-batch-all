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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.example.aggregator.CustomLineAggregator;
import com.example.mapper.CustomerRowMapper;
import com.example.model.Customer;


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

		return new JdbcPagingItemReaderBuilder<Customer>()
				.name("customerPagingItemReader")
				.dataSource(this.dataSource)
				.fetchSize(1000)
				.rowMapper(new CustomerRowMapper())
				.queryProvider(queryProvider)
				.build();
	}
	
	
	@Bean
	public FlatFileItemWriter<Customer> jsonItemWriter() throws Exception{
		String customerOutputPath = File.createTempFile("customerOutput", ".out").getAbsolutePath();
		System.out.println(">> Output Path = "+customerOutputPath);
				
		return new FlatFileItemWriterBuilder<Customer>()
				.name("jsonItemWriter")
				.lineAggregator(new CustomLineAggregator())
				.resource(new FileSystemResource(customerOutputPath))
				.build();
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public StaxEventItemWriter<Customer> xmlItemWriter() throws Exception{
		String customerOutputPath = File.createTempFile("customerOutput", ".out").getAbsolutePath();
		System.out.println(">> Output Path = "+customerOutputPath);
		
		Map<String, Class> aliases = new HashMap<>();
		aliases.put("customer", Customer.class);
		
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliases);
		
		// StAX and Marshaller for serializing object to XML.
		return new StaxEventItemWriterBuilder<Customer>()
				.name("xmlItemWriter")
				.rootTagName("customers")
				.marshaller(marshaller)
				.resource(new FileSystemResource(customerOutputPath))
				.build();
	}
	
	
	@Bean
	public CompositeItemWriter<Customer> itemWriter() throws Exception{	
		List<ItemWriter<? super Customer>> writers = new ArrayList<>();
		writers.add(xmlItemWriter());
		writers.add(jsonItemWriter());

		return new CompositeItemWriterBuilder<Customer>()
				.delegates(writers)
				.build();
	}
	
	
	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer> chunk(10)
				.reader(customerPagingItemReader())
				.writer(itemWriter())
				.build();
	}
	
	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
}
