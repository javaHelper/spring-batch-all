package com.example.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.example.domain.Customer;
import com.example.processor.CustomerProcessor;
import com.thoughtworks.xstream.security.AnyTypePermission;

@Configuration
public class JobConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

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

		CustomerConverter converter = new CustomerConverter();

		XStreamMarshaller unmarshaller = new XStreamMarshaller();
		unmarshaller.setAliases(aliases);
		unmarshaller.setConverters(converter);
		unmarshaller.setTypePermissions(AnyTypePermission.ANY);

		StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
		reader.setResource(new ClassPathResource("/data/customer.xml"));
		reader.setFragmentRootElementName("customer");
		reader.setUnmarshaller(unmarshaller);

		return reader;
	}

	@Bean
	public CustomerProcessor customerProcessor() {
		return new CustomerProcessor();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer>chunk(200)
				.reader(customerItemReader())
				.processor(customerProcessor())
				.writer(writer())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job").start(step1()).build();
	}

	@Bean
	public JdbcBatchItemWriter<Customer> writer() {
		JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(this.dataSource);
		writer.setSql("INSERT INTO test.customer (id, birthdate, first_name, last_name) VALUES (?,?,?,?)");
		writer.setItemPreparedStatementSetter(new CustomerItemPreparedStmSetter());
		return writer;
	}

	private class CustomerItemPreparedStmSetter implements ItemPreparedStatementSetter<Customer> {

		public void setValues(Customer result, PreparedStatement ps) throws SQLException {
			ps.setLong(1, result.getId());
			ps.setDate(2, java.sql.Date.valueOf(result.getBirthdate()));
			ps.setString(3, result.getFirstName());
			ps.setString(4, result.getLastName());
		}
	}
}
