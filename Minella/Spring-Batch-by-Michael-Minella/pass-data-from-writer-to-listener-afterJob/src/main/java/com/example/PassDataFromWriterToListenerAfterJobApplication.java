package com.example;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;


@EnableBatchProcessing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PassDataFromWriterToListenerAfterJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassDataFromWriterToListenerAfterJobApplication.class, args);
	}
	
	
	static class MyJobExecutionListener implements JobExecutionListener{

		@Override
		public void beforeJob(JobExecution jobExecution) {
			System.out.println("## beforeJob");
		}

		@Override
		public void afterJob(JobExecution jobExecution) {
			ExecutionContext executionContext = jobExecution.getExecutionContext();
			String data = executionContext.getString("data");
			System.out.println("####  data from writer = " + data);
		}		
	}
	
	@Bean
	public static MyJobExecutionListener jobExecutionListener() {
		return new MyJobExecutionListener();
	}

	
	static class MyJobConfig{
		@Autowired
	    private JobBuilderFactory jobs;

	    @Autowired
	    private StepBuilderFactory steps;

	    @Bean
	    public ItemReader<Integer> itemReader() {
	        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4));
	    }

	    
	    @Bean
	    public ItemWriter<Integer> itemWriter(){
	    	return new ItemWriter<Integer>() {
	    		
	    		private StepExecution stepExecution;
	    		@BeforeStep
	            public void saveStepExecution(StepExecution stepExecution) {
	                this.stepExecution = stepExecution;
	            }

				@Override
				public void write(List<? extends Integer> items) throws Exception {
					for (Integer item : items) {
	                    System.out.println("item = " + item);
	                }
					
					stepExecution.getJobExecution().getExecutionContext().put("data", "fooo");
				}
			};
	    }
	    
	    @Bean
	    public Step step1() {
	        return steps.get("step1")
	                .<Integer, Integer>chunk(2)
	                .reader(itemReader())
	                .writer(itemWriter())
	                .build();
	    }
	    
	    @Bean
	    public Job job() {
	    	return jobs.get("job")
	    			.start(step1())
	    			.listener(jobExecutionListener())
	    			.build();
	    }
	    
	}
}
