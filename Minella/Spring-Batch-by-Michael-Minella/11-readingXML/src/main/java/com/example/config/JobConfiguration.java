package com.example.config;

import com.example.domain.Customer;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
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

	@SuppressWarnings("rawtypes")
	@Bean
	public StaxEventItemReader<Customer> customerItemReader() {
		Map<String, Class> aliases = new HashMap<>();
		aliases.put("customer", Customer.class);
//		aliases.put("id", Long.class);
//		aliases.put("firstName", String.class);
//		aliases.put("lastName", String.class);
//		aliases.put("birthdate", LocalDate.class);

		XStreamMarshaller unmarshall = new XStreamMarshaller();
		unmarshall.setAliases(aliases);
		unmarshall.setConverters(new CustomerConverter());
		unmarshall.setTypePermissions(AnyTypePermission.ANY);

		// XML Reader
		StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
		reader.setResource(new ClassPathResource("/data/customer.xml"));
		reader.setFragmentRootElementName("customer");
		reader.setUnmarshaller(unmarshall);

		return reader;
	}

	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.<Customer, Customer>chunk(200, manager)
				.reader(customerItemReader())
				.writer(writer())
				.build();
	}

	@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.build();
	}

	@Bean
	public JdbcBatchItemWriter<Customer> writer() {
		JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(this.dataSource);
		writer.setSql("INSERT INTO test.customer (id, birthdate, first_name, last_name) VALUES (?,?,?,?)");
		writer.setItemPreparedStatementSetter(new CustomerItemPreparedStmSetter());
		return writer;
	}
}