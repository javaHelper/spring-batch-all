package com.example.demo.config;

import com.example.demo.model.Customer;
import com.example.demo.processor.UpperCaseItemProcessor;
import com.example.demo.rowmapper.CustomerRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobConfiguration {

	@Autowired
	public JobRepository jobRepository;

	@Autowired
	public PlatformTransactionManager manager;

	@Autowired
	public DataSource dataSource;

	@Bean
	public JdbcPagingItemReader<Customer> pagingItemReader() {
		Map<String, Order> sortKeys = new HashMap<>(1);
		sortKeys.put("id", Order.ASCENDING);

		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("id, firstName, lastName, birthdate");
		queryProvider.setFromClause("from customer");
		queryProvider.setSortKeys(sortKeys);

		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(this.dataSource);
		reader.setFetchSize(10);
		reader.setRowMapper(new CustomerRowMapper());
		reader.setQueryProvider(queryProvider);
		return reader;
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public StaxEventItemWriter<Customer> customerItemWriter() throws Exception {
		XStreamMarshaller marshaller = new XStreamMarshaller();

		Map<String, Class> aliases = new HashMap<>();
		aliases.put("customer", Customer.class);

		marshaller.setAliases(aliases);

		StaxEventItemWriter<Customer> itemWriter = new StaxEventItemWriter<>();
		itemWriter.setRootTagName("customers");
		itemWriter.setMarshaller(marshaller);

		String customerOutputPath = File.createTempFile("customerOutput", ".xml").getAbsolutePath();
		System.out.println(">> Output Path: " + customerOutputPath);
		itemWriter.setResource(new FileSystemResource(customerOutputPath));

		itemWriter.afterPropertiesSet();

		return itemWriter;
	}

	@Bean
	public UpperCaseItemProcessor itemProcessor() {
		return new UpperCaseItemProcessor();
	}

	@Bean
	public Step step1() throws Exception {
		return new StepBuilder("step1", jobRepository)
				.<Customer, Customer>chunk(10, manager)
				.reader(pagingItemReader())
				.processor(itemProcessor())
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