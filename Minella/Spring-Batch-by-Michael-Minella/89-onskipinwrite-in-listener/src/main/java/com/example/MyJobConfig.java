package com.example;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyJobConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

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
		return stepBuilderFactory.get("step")
				.<Integer, Integer>chunk(5)
				.reader(itemReader())
				.writer(itemWriter())
				.faultTolerant()
				.skip(Exception.class)
				.skipLimit(10)
				.listener(new StepExecutionListener())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
}