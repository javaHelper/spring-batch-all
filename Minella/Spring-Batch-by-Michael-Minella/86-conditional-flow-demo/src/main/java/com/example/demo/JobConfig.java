package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {
    enum Type {
        YEARLY, QUARTERLY
    }

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    public Step retrieveFileStep() {
        return new StepBuilder("retrieveFileStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Downloading file..");
                        chunkContext.getStepContext().getStepExecution().getExecutionContext().put("type", Type.YEARLY);
                        return RepeatStatus.FINISHED;
                    }
                }, manager).build();
    }

    @Bean
    public Step yearlyStep() {
        return new StepBuilder("yearlyStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("running yearlyStep");
                        return RepeatStatus.FINISHED;
                    }
                }, manager).build();
    }

    @Bean
    public Step quarterlyStep() {
        return new StepBuilder("quarterlyStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("running quarterlyStep");
                        return RepeatStatus.FINISHED;
                    }
                }, manager).build();
    }

    @Bean
    public JobExecutionDecider fileMapperDecider() {
        return (jobExecution, stepExecution) -> {
            Type type = (Type) stepExecution.getExecutionContext().get("type");
            return new FlowExecutionStatus(type == Type.YEARLY ? "yearly" : "quarterly");
        };
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(retrieveFileStep())
                .next(fileMapperDecider())
                .from(fileMapperDecider())
                .on("yearly").to(yearlyStep())
                .from(fileMapperDecider())
                .on("quarterly").to(quarterlyStep())
                .build()
                .build();
    }
}