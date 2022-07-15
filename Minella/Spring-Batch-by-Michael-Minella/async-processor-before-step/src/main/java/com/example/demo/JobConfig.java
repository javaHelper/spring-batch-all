package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Future;

@Configuration
public class JobConfig {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Bean
    public ItemProcessor<Integer, Integer> itemProcessor() {
        return new MyItemProcessor();
    }

    @Bean
    public AsyncItemProcessor<Integer, Integer> asyncItemProcessor(){
        AsyncItemProcessor<Integer, Integer> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(itemProcessor());
        asyncItemProcessor.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return asyncItemProcessor;
    }

    @Bean
    public AsyncItemWriter<Integer> asyncItemWriter(){
        AsyncItemWriter<Integer> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(itemWriter());
        return asyncItemWriter;
    }

    @Bean
    public ItemWriter<Integer> itemWriter(){
        return items -> {
            for (Integer item: items) {
                System.out.println(Thread.currentThread().getName() + ": item = " + item);
            }
        };
    }

    @Bean
    public Job job(JobBuilderFactory jobs, StepBuilderFactory steps) {
        return jobs.get("myJob")
                .start(getMyStep(steps))
                .build();
    }

    private TaskletStep getMyStep(StepBuilderFactory steps) {
        return steps.get("myStep")
                .<Integer, Future<Integer>>chunk(5)
                .reader(itemReader())
                .processor(asyncItemProcessor())
                .writer(asyncItemWriter())
                .listener(itemProcessor())
                .build();
    }
}
