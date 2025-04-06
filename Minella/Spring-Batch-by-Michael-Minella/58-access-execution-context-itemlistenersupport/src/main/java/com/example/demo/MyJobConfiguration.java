package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class MyJobConfiguration {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

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
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(step1())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Integer, Integer>chunk(5, manager)
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