package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfiguration {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(); // return the implementation you want
    }

    @Bean
    public Tasklet tasklet() {
        return new MyTasklet(cacheManager());
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new CachingJobExecutionListener(cacheManager());
    }

    @Bean
    public Step step() {
        return new StepBuilder("step", jobRepository)
                .tasklet(tasklet(), manager)
                .build();
    }

    @Bean
    public Job job(){
        return new JobBuilder("job", jobRepository)
                .start(step())
                .listener(jobExecutionListener())
                .build();
    }
}