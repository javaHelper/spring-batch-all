package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableBatchProcessing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StepExecutionParallelConfigThreadcountApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StepExecutionParallelConfigThreadcountApplication.class, args);
	}

	@Autowired
	private Job job;
	@Autowired
	private JobLauncher jobLauncher;

	@Override
	public void run(String... args) throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("time", String.valueOf(System.currentTimeMillis()))
				.addString("rootFolder", "/data")
				.toJobParameters();
		jobLauncher.run(job, jobParameters);
	}
}
