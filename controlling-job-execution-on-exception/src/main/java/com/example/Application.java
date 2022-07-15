package com.example;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableBatchProcessing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
	@Configuration
	static class MyJobConfig{
		@Autowired
	    private JobBuilderFactory jobs;

	    @Autowired
	    private StepBuilderFactory steps;

	    @Bean
	    public ItemReader<Integer> itemReader() {
	        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	    }

	    @Bean
	    public ItemWriter<Integer> itemWriter() {
	        return items -> {
	            for (Integer item : items) {
	                if (items.contains(3)) {
	                    throw new IllegalArgumentException("no 3!");
	                }
	                System.out.println("item = " + item);
	            }
	        };
	    }

	    @Bean
	    public Step step1() {
	        return steps.get("step1")
	                .<Integer, Integer>chunk(5)
	                .reader(itemReader())
	                .writer(itemWriter())
	                .build();
	    }

	    @Bean
	    public Step step2() {
	        return steps.get("step2")
	                .tasklet((contribution, chunkContext) -> {
	                    System.out.println("==== step2");
	                    return RepeatStatus.FINISHED;
	                })
	                .build();
	    }

	    @Bean
	    public Step step3() {
	        return steps.get("step3")
	                .tasklet((contribution, chunkContext) -> {
	                    System.out.println("=== step3");
	                    return RepeatStatus.FINISHED;
	                })
	                .build();
	    }
	    
	    @Bean
	    public JobExecutionDecider jobExecutionDecider() {
	    	return new JobExecutionDecider() {
				
				@Override
				public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
					int rollbackCount = stepExecution.getRollbackCount();
					
					List<Throwable> failureExceptions = stepExecution.getFailureExceptions();
					System.out.println("----------------------------------");
					System.out.println("rollbackCount = " + rollbackCount);
		            System.out.println("failureExceptions = " + failureExceptions);
		            System.out.println("----------------------------------");
		            
					
		            // make the decision based on rollbackCount and/or failureExceptions and return status accordingly
		            if(rollbackCount > 0) {
		            	return FlowExecutionStatus.STOPPED;
		            }else {
		            	return FlowExecutionStatus.COMPLETED;
		            }
				}
			};
	    }
	    
	    @Bean
	    public Job job() {
	        return jobs.get("job")
	                .start(step1())
	                .on("*").to(jobExecutionDecider())
	                .from(jobExecutionDecider()).on("COMPLETED").to(step2())
	                .from(jobExecutionDecider()).on("FAILED").to(step3())
	                .build()
	                .build();
	    }
	}
}
