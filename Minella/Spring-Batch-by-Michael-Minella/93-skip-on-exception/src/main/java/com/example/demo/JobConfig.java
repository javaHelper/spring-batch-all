package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class JobConfig {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Bean
    public ItemProcessor<Integer, Integer> itemProcessor() {
        return new ItemProcessor<Integer, Integer>() {

            @Override
            public Integer process(Integer item) throws Exception {
                if (item.equals(3)) {
                    throw new IllegalArgumentException("No 3!");
                }
                if (item.equals(9)) {
                    throw new NullPointerException("Fata at 9!");
                }
                return item;
            }
        };
    }

    @Bean
    public ItemWriter<Integer> itemWriter() {
        return items -> {
            items.forEach(System.out::println);
        };
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Integer, Integer>chunk(2, manager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant()
                .skip(IllegalArgumentException.class)
                .skip(NullPointerException.class)
                .skipLimit(3)
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(step1())
                .build();
    }
}
