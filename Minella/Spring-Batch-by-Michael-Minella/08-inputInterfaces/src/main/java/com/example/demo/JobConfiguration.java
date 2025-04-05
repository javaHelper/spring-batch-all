package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JobConfiguration {

	@Autowired
	public JobRepository jobRepository;

	@Autowired
	public PlatformTransactionManager manager;

	@Bean
	public StatelessItemReader statelessItemReader() {
		List<String> data = new ArrayList<>(3);
		data.add("Foo");
		data.add("Bar");
		data.add("Baz");
		return new StatelessItemReader(data);
	}

	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.<String, String>chunk(2, manager)
				.reader(statelessItemReader())
				.writer(list -> {
					for (String curItem : list) {
						System.out.println("curItem = " + curItem);
					}
				}).build();
	}

	@Bean
	public Job interfacesJob() {
		return new JobBuilder("interfacesJob", jobRepository)
				.start(step1())
				.build();
	}
}