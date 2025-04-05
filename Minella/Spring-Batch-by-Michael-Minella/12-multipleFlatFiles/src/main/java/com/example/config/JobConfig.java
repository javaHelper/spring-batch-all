package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.example.domain.Customer;
import com.example.mapper.CustomerFieldSetMapper;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager manager;

	@Value("classpath*:/data/customer*.csv")
	private Resource[] inputFiles;

	@Bean
	public MultiResourceItemReader<Customer> multiResourceItemReader() {
		return new MultiResourceItemReaderBuilder<Customer>()
				.name("multiResourceItemReader")
				.delegate(customerItemReader())
				.resources(inputFiles)
				.build();
	}

	@Bean
	public FlatFileItemReader<Customer> customerItemReader() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("id", "firstName", "lastName", "birthdate");

		DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();
		customerLineMapper.setLineTokenizer(tokenizer);
		customerLineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
		customerLineMapper.afterPropertiesSet();

		FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
		reader.setLineMapper(customerLineMapper);

		return reader;
	}

	@Bean
	public ItemWriter<Customer> customerItemWriter(){
		return items -> {
			for (Customer customer : items) {
				System.out.println(customer.toString());
			}
		};
	}
	
	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.<Customer, Customer>chunk(10, manager)
				.reader(multiResourceItemReader())
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