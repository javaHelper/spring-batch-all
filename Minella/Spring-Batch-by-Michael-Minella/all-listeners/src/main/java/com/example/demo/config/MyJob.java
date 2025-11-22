package com.example.demo.config;

import com.example.demo.Person;
import com.example.demo.listeners.ItemProcessLogListener;
import com.example.demo.listeners.ItemReadLogListener;
import com.example.demo.listeners.ItemWriteLogListener;
import com.example.demo.listeners.LogChunkListener;
import com.example.demo.listeners.LogStepExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class MyJob {
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager manager;
	
	@Bean
	public ItemReader<Integer> itemReader(){
		return new ListItemReader<>(Arrays.asList(1, 2, 3, 4));
	}
	
	@Bean
	public Step step1() {
		SimpleStepBuilder<Integer, Person> builder = new StepBuilder("step", jobRepository)
				.allowStartIfComplete(true)
				.<Integer, Person>chunk(2, manager)
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
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.build();
	}
}
