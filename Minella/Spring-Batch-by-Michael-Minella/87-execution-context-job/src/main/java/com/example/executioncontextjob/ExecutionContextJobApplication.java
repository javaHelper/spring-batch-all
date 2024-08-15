package com.example.executioncontextjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class ExecutionContextJobApplication {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job helloWorldBatchJob() {
		return this.jobBuilderFactory.get("helloWorldBatchJob")
				.incrementer(new RunIdIncrementer())
				.start(helloWorldStep())
				.build();
	}

	@Bean
	public Step helloWorldStep() {
		return this.stepBuilderFactory.get("helloWorldStep")
				.tasklet(tasklet())
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
