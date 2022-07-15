package com.example;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyJobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;
	
	
	@Bean
    public ItemReader<Person> itemReader() {
        Person foo = new Person(1, "foo");
        Person bar = new Person(2, "bar");
        Person baz = new Person(3, "baz");
        return new ListItemReader<>(Arrays.asList(foo, bar, baz));
    }

    @Bean
    public ItemWriter<Person> itemWriter() {
        return new PersonItemWriter(this.dataSource);
    }
    
    @Bean
    public Step step1() {
    	return stepBuilderFactory.get("step")
	        .<Person, Person>chunk(3)
	        .reader(itemReader())
	        .writer(itemWriter())
	        .faultTolerant()
	        .skip(IllegalStateException.class)
	        .skipLimit(10)
	        .build();
    }
    
    @Bean
    public Job job() {
    	return jobBuilderFactory.get("job")
    			.start(step1())
    			.build();
    }
    
}
