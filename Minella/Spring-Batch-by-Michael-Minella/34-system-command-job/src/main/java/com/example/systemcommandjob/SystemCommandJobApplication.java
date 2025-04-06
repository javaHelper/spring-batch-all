package com.example.systemcommandjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class SystemCommandJobApplication {
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager manager;

	@Bean
	public Job job() {
		return new JobBuilder("systemCommandJob", jobRepository)
				.start(systemCommandStep())
				.build();
	}

	@Bean
	public Step systemCommandStep() {
		return new StepBuilder("systemCommandStep", jobRepository)
				.tasklet(systemCommandTasklet(), manager)
				.build();
	}

	@Bean
	public SystemCommandTasklet systemCommandTasklet() {
		SystemCommandTasklet systemCommandTasklet = new SystemCommandTasklet();
		systemCommandTasklet.setCommand("rm -rf /tmp.txt");
		systemCommandTasklet.setTimeout(5000);
		systemCommandTasklet.setInterruptOnCancel(true);
		return systemCommandTasklet;
	}

	public static void main(String[] args) {
		SpringApplication.run(SystemCommandJobApplication.class, args);
	}

}