package com.example.config;

import com.example.decider.MyJobExecutionDecider;
import com.example.tasklet.Step1Tasklet;
import com.example.tasklet.Step2Tasklet;
import com.example.tasklet.Step3Tasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MyJob {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    public Step1Tasklet step1Tasklet() {
        return new Step1Tasklet();
    }

    @Bean
    public Step2Tasklet step2Tasklet() {
        return new Step2Tasklet();
    }

    @Bean
    public Step3Tasklet step3Tasklet() {
        return new Step3Tasklet();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .tasklet(step1Tasklet(), manager)
                .build();
    }

    @Bean
    public MyJobExecutionDecider decider() {
        return new MyJobExecutionDecider();
    }

    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository)
                .tasklet(step2Tasklet(), manager)
                .build();
    }

    @Bean
    public Step step3() {
        return new StepBuilder("step3", jobRepository)
                .tasklet(step3Tasklet(), manager)
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(step1())
                .next(decider())
                .on("YES").to(step2())
                .from(decider())
                .on("NO").to(step3()).end()
                .build();
    }
}