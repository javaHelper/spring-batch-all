package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class JobConfig {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Bean
    public ItemProcessor<Integer, Integer> itemProcessor(){
        return new ItemProcessor<Integer, Integer>() {

            @Override
            public Integer process(Integer item) throws Exception {
                if(item.equals(3)){
                    throw new IllegalArgumentException("No 3!");
                }
                if(item.equals(9)){
                    throw new NullPointerException("Fata at 9!");
                }
                return item;
            }
        };
    }

    @Bean
    public ItemWriter<Integer> itemWriter(){
        return items -> {
          items.forEach(System.out::println);
        };
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .<Integer, Integer>chunk(2)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant()
                .skip(IllegalArgumentException.class)
                .skipLimit(3)
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }
}
