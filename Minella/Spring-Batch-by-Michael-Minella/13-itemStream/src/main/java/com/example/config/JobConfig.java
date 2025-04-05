package com.example.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    //@StepScope - No need, this will not save state
    public StatefullItemReader itemReader() {
        List<String> items = new ArrayList<>(100);

        for (int i = 1; i <= 100; i++) {
            items.add(String.valueOf(i));
        }
        return new StatefullItemReader(items);
    }


    @Bean
    public ItemWriter<String> itemWriter() {
        return chunk -> chunk.forEach(System.out::println);
    }


    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<String, String>chunk(10, manager)
                .reader(itemReader())
                .writer(itemWriter())
                .stream(itemReader())
                .build();
    }

    @Bean
    public Job statefulJob() {
        return new JobBuilder("statefulJob", jobRepository)
                .start(step1())
                .build();
    }
}