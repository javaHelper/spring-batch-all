package com.example.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
public class JobConfiguration {
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager manager;
	
	@Bean
	@StepScope
	public Tasklet restartTasklet() {
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				Map<String, Object> stepExecutionContext = chunkContext.getStepContext().getStepExecutionContext();
				if(stepExecutionContext.containsKey("ran")) {
					System.out.println("This time we'll let it go !");
					return RepeatStatus.FINISHED;
				}
				else {
					System.out.println("I don't think so... ");
					chunkContext.getStepContext().getStepExecution().getExecutionContext().put("ran", true);
					throw new RuntimeException("Not this time....");
				}
			}
		};
	}
	
	@Bean
	public Step step1() {
		return new StepBuilder("step1", jobRepository)
				.tasklet(restartTasklet(), manager)
				.build();
	}
	
	@Bean
	public Step step2() {
		return new StepBuilder("step2", jobRepository)
				.tasklet(restartTasklet(), manager)
				.build();
	}
	
	@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.next(step2())
				.build();
	}
}