package com.example.demo;

import java.util.Arrays;

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
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MyJob {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(1, 2, 3));
    }

    @Bean
    public ItemWriter<Integer> itemWriter() {
        return items -> {
            for (Integer item : items) {
                if (item.equals(1)) {
                    throw new Exception("No 1 here!");
                }
                System.out.println("item = " + item);
            }
        };
    }

    @Bean
    public Step step() {
        return steps.get("step")
                .<Integer, Integer>chunk(5)
                .reader(itemReader())
                .writer(itemWriter())
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(10)
                .listener(mySkipListener())
                .listener(myStepListener())
                .build();
    }

    @Bean
    public Job job() {
        return jobs.get("job")
                .start(step())
                .build();
    }
    
    @Bean
    public MySkipListener mySkipListener() {
        return new MySkipListener();
    }
    
    @Bean
    public MyStepListener myStepListener() {
        return new MyStepListener();
    }

}