package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {
    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private StepBuilderFactory steps;

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
        return steps.get("step")
                .tasklet(tasklet())
                .build();
    }

    @Bean
    public Job job(){
        return jobs.get("job")
                .start(step())
                .listener(jobExecutionListener())
                .build();
    }
}
