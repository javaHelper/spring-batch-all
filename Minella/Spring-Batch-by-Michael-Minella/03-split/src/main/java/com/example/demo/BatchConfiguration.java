package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class BatchConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Tasklet tasklet() {
		return new CountingTasklet();
	}

	@Bean
	public Flow flow1() {
		return new FlowBuilder<Flow>("flow1")
				.start(stepBuilderFactory.get("step1")
						.tasklet(tasklet()).build())
				.build();
	}

	@Bean
	public Flow flow2() {
		return new FlowBuilder<Flow>("flow2")
				.start(stepBuilderFactory.get("step2")
						.tasklet(tasklet()).build())
				.next(stepBuilderFactory.get("step3")
						.tasklet(tasklet()).build())
				.build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(flow1())
				.split(new SimpleAsyncTaskExecutor()).add(flow2())
				.end()
				.build();
	}

	
	
	public static class CountingTasklet implements Tasklet {

		@Override
		public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
			System.out.println(String.format("%s has been executed on thread %s", 
					chunkContext.getStepContext().getStepName(), Thread.currentThread().getName()));
			return RepeatStatus.FINISHED;
		}
	}

}
