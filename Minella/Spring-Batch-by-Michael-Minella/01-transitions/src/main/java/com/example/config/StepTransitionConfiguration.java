package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class StepTransitionConfiguration {

	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager ptm) {
		return new StepBuilder("step1", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println(">> This is step-1");
					return RepeatStatus.FINISHED;
				}, ptm).build();
	}


	@Bean
	public Step step2(JobRepository jobRepository, PlatformTransactionManager ptm) {
		return new StepBuilder("step2", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println(">> This is step-2");
					return RepeatStatus.FINISHED;
				}, ptm).build();
	}
	
	@Bean
	public Step step3(JobRepository jobRepository, PlatformTransactionManager ptm) {
		return new StepBuilder("step3", jobRepository)
				.tasklet(new Tasklet() {

					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						System.out.println(">> This is step-3");
						return RepeatStatus.FINISHED;
					}
				}, ptm).build();
	}
	
	// This also works, an alternative shown below
	/*@Bean
	public Job transitionJobSimpleNext() {
		return jobBuilderFactory.get("transitionJobSimpleNext")
				.start(step1())
				.next(step2())
				.next(step3())
				.build();
	}*/
	
	
	@Bean
	public Job transitionJobSimpleNext(JobRepository jobRepository, PlatformTransactionManager ptm) {
		return new JobBuilder("transitionJobSimpleNext", jobRepository)
				.start(step1(jobRepository, ptm))
					.on("COMPLETED").to(step2(jobRepository, ptm))
				.from(step2(jobRepository, ptm))
					.on("COMPLETED").to(step3(jobRepository, ptm))
				.from(step3(jobRepository, ptm))
					.end()
				.build();
	}
	
	
	/*@Bean
	public Job transitionJobSimpleNext() {
		return jobBuilderFactory.get("transitionJobSimpleNext")
				.start(step1())
				.on("COMPLETED").to(step2())
				.from(step2()).on("COMPLETED").stopAndRestart(step3())
				.from(step3()).end()
				.build();
	}*/
}