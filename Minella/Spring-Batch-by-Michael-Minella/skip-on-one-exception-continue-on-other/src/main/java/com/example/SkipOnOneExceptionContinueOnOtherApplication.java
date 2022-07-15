package com.example;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.lang.Nullable;


@EnableBatchProcessing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SkipOnOneExceptionContinueOnOtherApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkipOnOneExceptionContinueOnOtherApplication.class, args);
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
	    public ItemProcessor<Integer, Integer> itemProcessor() {
	        return new ItemProcessor<Integer, Integer>() {
	            @Nullable
	            @Override
	            public Integer process(Integer item) throws Exception {
	                if (item.equals(3)) {
	                	System.out.println("We loose 3");
	                    throw new IllegalArgumentException("No 3!");
	                }
	                if (item.equals(9)) {
	                    throw new NullPointerException("Boom at 9!");
	                }
	                return item;
	            }
	        };
	    }
	    
	    @Bean
	    public ItemWriter<Integer> itemWriter() {
	        return items -> {
	            for (Integer item : items) {
	                System.out.println("item = " + item);
	            }
	        };
	    }

	    @Bean
	    public Step step() {
	        return steps.get("step")
	                .<Integer, Integer>chunk(1)
	                .reader(itemReader())
	                .processor(itemProcessor())
	                .writer(itemWriter())
	                .faultTolerant()
	                .skip(IllegalArgumentException.class)
	                .skipLimit(3)
	                .build();
	    }

	    @Bean
	    public Job job() {
	        return jobs.get("job")
	                .start(step())
	                .build();
	    }
	}
}
