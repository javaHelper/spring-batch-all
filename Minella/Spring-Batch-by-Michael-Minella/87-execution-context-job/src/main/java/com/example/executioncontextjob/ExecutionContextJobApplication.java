package com.example.executioncontextjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class ExecutionContextJobApplication {
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager manager;

	@Bean
	public Job helloWorldBatchJob() {
		return new JobBuilder("helloWorldBatchJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(helloWorldStep())
				.build();
	}

	@Bean
	public Step helloWorldStep() {
		return new StepBuilder("helloWorldStep", jobRepository)
				.tasklet(tasklet(), manager)
				.build();
	}

	@StepScope
	@Bean
	public HelloWorldTasklet tasklet() {
		return new HelloWorldTasklet();
	}

	public static void main(String[] args) {
		SpringApplication.run(ExecutionContextJobApplication.class, "name=Harshita Dekate");
	}
}