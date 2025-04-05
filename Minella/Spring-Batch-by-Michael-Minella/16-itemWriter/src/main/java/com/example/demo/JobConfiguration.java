package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class JobConfiguration {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager manager;

	@Bean
	public ListItemReader<String> itemReader() {
		List<String> items = new ArrayList<>(100);

		for(int i = 1; i <= 100; i++) {
			items.add(String.valueOf(i));
		}

		return new ListItemReader<>(items);
	}

	@Bean
	public SysOutItemWriter itemWriter() {
		return new SysOutItemWriter();
	}

	@Bean
	public Step step() {
		return new StepBuilder("step", jobRepository)
				.<String, String>chunk(10, manager)
				.reader(itemReader())
				.writer(itemWriter())
				.build();
	}

	@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(step())
				.build();
	}
}