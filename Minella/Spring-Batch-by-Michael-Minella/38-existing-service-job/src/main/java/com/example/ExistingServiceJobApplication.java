package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class ExistingServiceJobApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ExistingServiceJobListener existingServiceJobListener;

	@Bean
	public ItemReaderAdapter<Customer> customerItemReader(CustomerService customerService) {
		ItemReaderAdapter<Customer> adapter = new ItemReaderAdapter<>();
		adapter.setTargetObject(customerService);
		adapter.setTargetMethod("getCustomer");
		return adapter;
	}

	@Bean
	public ItemWriter<Customer> itemWriter() {
		return (items) -> items.forEach(System.out::println);
	}

	@Bean
	public Step copyFileStep() {
		return this.stepBuilderFactory.get("copyFileStep")
				.<Customer, Customer>chunk(10)
				.reader(customerItemReader(null))
				.writer(itemWriter())
				.build();
	}

	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("job")
				.start(copyFileStep())
				.listener(existingServiceJobListener)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ExistingServiceJobApplication.class, args);
	}
}
