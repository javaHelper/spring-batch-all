package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class FlowLastConfig {
	@Autowired
	public JobRepository jobRepository;

	@Autowired
	public PlatformTransactionManager manager;
	
	@Bean
	public Step myStep1() {
		return new StepBuilder("myStep1", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("myStep was executed");
					return RepeatStatus.FINISHED;
				}, manager).build();
	}
	
	@Bean
	public Job flowLastJob(Flow flow) {
		return new JobBuilder("flowLastJob", jobRepository)
				.start(flow)
					.on("COMPLETED").to(flow)
				.end()
				.build();
	}
}