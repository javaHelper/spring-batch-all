package com.example.callabletasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Callable;

@SpringBootApplication
@EnableBatchProcessing
public class CallableTaskletApplication {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job callableJob() {
        return this.jobBuilderFactory.get("callableJob")
                .start(callableStep())
                .build();
    }

    @Bean
    public Step callableStep() {
        return this.stepBuilderFactory.get("callableStep")
                .tasklet(tasklet())
                .build();
    }

    @Bean
    public Callable<RepeatStatus> callableObject() {
        return () -> {
            System.out.println(">> This was executed in another thread");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public CallableTaskletAdapter tasklet() {
        CallableTaskletAdapter callableTaskletAdapter = new CallableTaskletAdapter();
        callableTaskletAdapter.setCallable(callableObject());
        return callableTaskletAdapter;
    }

    public static void main(String[] args) {
        SpringApplication.run(CallableTaskletApplication.class, args);
    }
}
