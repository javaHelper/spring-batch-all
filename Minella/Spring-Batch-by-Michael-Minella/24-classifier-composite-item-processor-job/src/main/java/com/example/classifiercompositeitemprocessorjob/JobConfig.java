package com.example.classifiercompositeitemprocessorjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.ScriptItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@SuppressWarnings("unchecked")
@Configuration
public class JobConfig {
    @Autowired
	public JobRepository jobRepository;

	@Autowired
	public PlatformTransactionManager manager;

	@Autowired
	private UpperCaseNameService service;

	@Bean
	@StepScope
	public FlatFileItemReader<Customer> customerItemReader() {
		return new FlatFileItemReaderBuilder<Customer>()
				.name("customerItemReader")
				.delimited()
				.names("firstName", "middleInitial", "lastName", "address", "city", "state", "zip")
				.targetType(Customer.class)
				.resource(new ClassPathResource("/input/customer.csv"))
				.build();
	}

	@Bean
	public ItemWriter<Customer> itemWriter() {
		return (items) -> items.forEach(System.out::println);
	}

	@Bean
	public ItemProcessorAdapter<Customer, Customer> upperCaseItemProcessor() {
		ItemProcessorAdapter<Customer, Customer> adapter = new ItemProcessorAdapter<>();
		adapter.setTargetObject(service);
		adapter.setTargetMethod("upperCase");
		return adapter;
	}

	@Bean
	@StepScope
	public ScriptItemProcessor<Customer, Customer> lowerCaseItemProcessor() {
		ScriptItemProcessor<Customer, Customer> itemProcessor = new ScriptItemProcessor<>();
		itemProcessor.setScript(new ClassPathResource("/input/lowerCase.js"));
		return itemProcessor;
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public Classifier classifier() {
		return new ZipCodeClassifier(upperCaseItemProcessor(), lowerCaseItemProcessor());
	}

	@Bean
	public ClassifierCompositeItemProcessor<Customer, Customer> itemProcessor() {
		ClassifierCompositeItemProcessor<Customer, Customer> itemProcessor = new ClassifierCompositeItemProcessor<>();
		itemProcessor.setClassifier(classifier());
		return itemProcessor;
	}

	@Bean
	public Step copyFileStep() {
		return new StepBuilder("copyFileStep", jobRepository)
				.<Customer, Customer>chunk(5, manager)
				.reader(customerItemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return new JobBuilder("job", jobRepository)
				.start(copyFileStep())
				.build();
	}
}