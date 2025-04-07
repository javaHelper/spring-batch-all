package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collection;
import java.util.List;

@Configuration
public class JobConfiguration {
    @Autowired
    private PlatformTransactionManager manager;

    @Autowired
    private JobRepository jobRepository;

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("hello");
                    throw new Exception("Boom!");
                }, manager)
                .build();
    }

    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository)
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
                }, manager).build();
    }

    @Bean
    public Job job(){
        // Obviously, you need to make sure step1 flows to step2 in all cases (Hence the flow definition).
        return new JobBuilder("job", jobRepository)
                .flow(step1())
                    .on("*").to(step2())
                    .build()
                .build();
    }
}