package com.example.demo;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class MyJob {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public ItemReader<Integer> itemReader(){
		return new ListItemReader<>(Arrays.asList(1, 2, 3, 4));
	}
	
	@Bean
	public Step step1() {
		SimpleStepBuilder<Integer, Person> builder = stepBuilderFactory.get("step")
				.allowStartIfComplete(true)
				.<Integer, Person>chunk(2)
				.reader(new ListItemReader<>(Arrays.asList(1, 2, 3, 4)))
				.listener(new ItemReadLogListener())
				.processor((ItemProcessor<Integer, Person>) item -> new Person("foo" + item))
//				.processor(new FunctionItemProcessor<>(item -> new Person("foo" + item)))
				.listener(new ItemProcessLogListener())
				.writer(items -> items.forEach(System.out::println))
				.listener(new ItemWriteLogListener())
				.faultTolerant()
				.skip(Exception.class)
				.skipLimit(3);

		builder.listener(new LogChunkListener())
				.listener(new LogStepExecutionListener());
		
		return builder.build();
	}
	
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
	
}
