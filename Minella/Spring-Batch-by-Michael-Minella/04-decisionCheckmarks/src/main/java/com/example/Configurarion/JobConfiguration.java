package com.example.Configurarion;

import com.example.decider.OddDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfiguration {
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	public Step startStep() {
		return new StepBuilder("startStep", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("This is the start Tasklet");
					return RepeatStatus.FINISHED;
				}, transactionManager).build();
	}
	
	@Bean
	public Step evenStep() {
		return new StepBuilder("evenStep", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("This is the even Tasklet");
					return RepeatStatus.FINISHED;
				},transactionManager).build();
	}
	
	@Bean
	public Step oddStep() {
		return new StepBuilder("oddStep", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("This is the odd Tasklet");
					return RepeatStatus.FINISHED;
				}, transactionManager).build();
	}
	
	@Bean
	public JobExecutionDecider decider() {
		return new OddDecider();
	}
	
	@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(startStep())
				.next(decider())
				.from(decider())
					.on("ODD").to(oddStep())
				.from(decider())
					.on("EVEN").to(evenStep())
				.from(oddStep())
					.on("*").to(decider())
//				.from(decider()).on("ODD").to(oddStep())
//				.from(decider()).on("EVEN").to(evenStep())
				.end()
				.build();
				
	}
}