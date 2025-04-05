package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;


@Configuration
public class ListenerJobConfiguration {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager manager;

	@Bean
	public ItemReader<String> reader() {
		return new ListItemReader<>(Arrays.asList("one", "two", "three"));
	}

	@Bean
	public ItemWriter<String> writer() {
		return new ItemWriter<String>() {
			@Override
			public void write(Chunk<? extends String> chunk) throws Exception {
				for (String item : chunk) {
					System.out.println("Writing item " + item);
				}
			}
		};
	}

	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.<String, String>chunk(2, manager)
				.faultTolerant()
				.listener(new ChunkListener())
				.reader(reader())
				.writer(writer())
				.build();
	}

	@Bean
	public Job listenerJob() {
		return new JobBuilder("listenerJob", jobRepository)
				.start(step1())
				.listener(new JobListener())
				.build();
	}
}