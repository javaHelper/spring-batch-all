package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ChildJobConfiguration {
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;


	@Bean
	public Step step1a() {
		return new StepBuilder("step1a", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("\t>>This is step 1a");

					return RepeatStatus.FINISHED;
				}, transactionManager).build();
	}

	@Bean
	public Job childJob() {
		return new JobBuilder("childJob", jobRepository)
				.start(step1a())
				.build();
	}
}