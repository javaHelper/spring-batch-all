package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DeciderConditionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeciderConditionalApplication.class, args);
	}

	
	@Configuration
	static class MyJobConfig{
		@Autowired
	    private JobBuilderFactory jobs;

	    @Autowired
	    private StepBuilderFactory steps;

	    @Bean
	    public Step firstStep() {
	        return steps.get("firstStep")
	                .tasklet((contribution, chunkContext) -> {
	                    System.out.println("## firstStep");
	                    return RepeatStatus.FINISHED;
	                })
	                .build();
	    }

	    @Bean
	    public JobExecutionDecider decider() {
	        return (jobExecution, stepExecution) -> new FlowExecutionStatus("TYPE1"); // or TYPE2
	    }

	    @Bean
	    public Step stepType1() {
	        return steps.get("stepType1")
	                .tasklet((contribution, chunkContext) -> {
	                    System.out.println("## stepType1");
	                    return RepeatStatus.FINISHED;
	                })
	                .build();
	    }

	    @Bean
	    public Step stepType2() {
	        return steps.get("stepType2")
	                .tasklet((contribution, chunkContext) -> {
	                    System.out.println("##@@ stepType2");
	                    return RepeatStatus.FINISHED;
	                })
	                .build();
	    }

	    @Bean
	    public Step lastStep() {
	        return steps.get("lastStep")
	                .tasklet((contribution, chunkContext) -> {
	                    System.out.println("## lastStep");
	                    return RepeatStatus.FINISHED;
	                })
	                .build();
	    }

	    @Bean
	    public Job job() {
	        return jobs.get("job")
	                .start(firstStep())
	                .next(decider())
	                    .on("TYPE1").to(stepType1())
	                    .from(decider()).on("TYPE2").to(stepType2())
	                    .from(stepType1()).on("*").to(lastStep())
	                    .from(stepType2()).on("*").to(lastStep())
	                    .build()
	                .build();
	    }
	}
}
