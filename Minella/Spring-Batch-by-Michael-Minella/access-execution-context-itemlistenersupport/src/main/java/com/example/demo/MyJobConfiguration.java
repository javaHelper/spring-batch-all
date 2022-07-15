package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MyJobConfiguration {
    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public ListItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Bean
    public ItemWriter<Integer> itemWriter() {
        return items -> items.forEach(System.out::println);
    }

    @Bean
    public ItemProcessor<Integer, Integer> itemProcessor() {
        return item -> {
            if (item % 2 != 0) {
                throw new Exception("no odd numbers here!");
            }
            return item;
        };
    }
    
    @Bean
    public MyItemProcessListener itemListenerSupport() {
        return new MyItemProcessListener();
    }

    @Bean
    public Job job(JobBuilderFactory jobs, StepBuilderFactory steps) {
        return jobs.get("job")
                .start(step1())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step step1() {
        return steps.get("step1")
                .<Integer, Integer>chunk(5)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(10)
                .listener(itemListenerSupport())
                .stream(itemListenerSupport())
                .build();
    }
}