package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {
    enum Type {
        YEARLY, QUARTERLY
    }

    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Step retrieveFileStep(){
        return steps.get("retrieveFileStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Downloading file..");
                        chunkContext.getStepContext().getStepExecution().getExecutionContext().put("type", Type.YEARLY);
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step yearlyStep(){
        return steps.get("yearlyStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("running yearlyStep");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step quarterlyStep(){
        return steps.get("quarterlyStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("running quarterlyStep");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public JobExecutionDecider fileMapperDecider(){
        return (jobExecution, stepExecution) -> {
            Type type = (Type) stepExecution.getExecutionContext().get("type");
            return new FlowExecutionStatus(type == Type.YEARLY ? "yearly" : "quarterly");
        };
    }

    @Bean
    public Job job() {
        return jobs.get("job")
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

