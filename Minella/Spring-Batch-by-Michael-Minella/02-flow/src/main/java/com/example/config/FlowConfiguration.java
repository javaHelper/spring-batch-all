package com.example.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class FlowConfiguration {
	@Autowired
	public JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager manager;
	
	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("Step 1 from inside flow too");
					return RepeatStatus.FINISHED;
				}, manager).build();
	}
	
	@Bean
	public Step step2() {
		return new StepBuilder("step2", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("Step 2 from inside flow too");
					return RepeatStatus.FINISHED;
				}, manager).build();
	}
	
	@Bean
	public Flow foo() {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("foo");
		flowBuilder.start(step1())
			.next(step2())
			.end();
		
		return flowBuilder.build();
	}
}