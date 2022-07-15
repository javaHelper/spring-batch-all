package com.example.demo;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;

@Configuration
public class JobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("hello");
                    throw new Exception("Boom!");
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
                        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
                        for (StepExecution execution : stepExecutions) {
                            List<Throwable> failureExceptions = execution.getFailureExceptions();
                            if (!failureExceptions.isEmpty()) {
                                Throwable throwable = failureExceptions.get(0);
                                System.out.println("Looks like step1 has thrown an exception: " + throwable.getMessage());
                            }
                        }
                        System.out.println("Step2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Job job(){
        // Obviously, you need to make sure step1 flows to step2 in all cases (Hence the flow definition).
        return jobBuilderFactory.get("job")
                .flow(step1())
                    .on("*").to(step2())
                    .build()
                .build();
    }
}
