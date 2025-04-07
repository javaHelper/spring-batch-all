package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.concurrent.Future;

@Configuration
public class JobConfig {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager manager;

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
    public Job job() {
        return new JobBuilder("myJob", jobRepository)
                .start(getMyStep())
                .build();
    }

    private TaskletStep getMyStep() {
        return new StepBuilder("myStep", jobRepository)
                .<Integer, Future<Integer>>chunk(5, manager)
                .reader(itemReader())
                .processor(asyncItemProcessor())
                .writer(asyncItemWriter())
                .listener(itemProcessor())
                .build();
    }
}