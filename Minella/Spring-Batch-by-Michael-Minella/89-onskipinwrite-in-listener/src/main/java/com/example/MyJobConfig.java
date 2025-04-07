package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class MyJobConfig {
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager manager;

	@Bean
	public ItemReader<Integer> itemReader() {
		return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	}

	@Bean
	public ItemWriter<Integer> itemWriter() {
		return items -> {
			for (Integer item : items) {
				if (item.equals(3)) {
					throw new Exception("No 3 here!");
				}
				System.out.println("item = " + item);
			}
		};
	}

	@Bean
	public Step step1() {
		return new StepBuilder("step", jobRepository)
				.<Integer, Integer>chunk(5, manager)
				.reader(itemReader())
				.writer(itemWriter())
				.faultTolerant()
				.skip(Exception.class)
				.skipLimit(10)
				.listener(new MyStepExecutionListener())
				.build();
	}

	@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.build();
	}
}