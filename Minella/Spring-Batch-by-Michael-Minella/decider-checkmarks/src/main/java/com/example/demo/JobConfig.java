package com.example.demo;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step validateCsvHeaderStepStep(){
        return stepBuilderFactory.get("validateCsvHeaderStepStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("validateCsvHeaderStep");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step processCsvStep(){
        return stepBuilderFactory.get("processCsvStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("processCsvStep");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step markCsvAsFailedStep(){
        return stepBuilderFactory.get("markCsvAsFailedStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("markCsvAsFailedStep");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step moveFailedCsvStep(){
        return stepBuilderFactory.get("moveFailedCsvStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("moveFailedCsvStep");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step moveCsvStep(){
        return stepBuilderFactory.get("moveCsvStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("moveCsvStep");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .flow(validateCsvHeaderStepStep())
                	.on(ExitStatus.FAILED.getExitCode()).to(markCsvAsFailedStep())
                .from(validateCsvHeaderStepStep())
                	.on("*").to(processCsvStep())
                .from(processCsvStep())
                	.on(ExitStatus.FAILED.getExitCode()).to(markCsvAsFailedStep())
                .from(processCsvStep())
                	.on("*").to(moveCsvStep())
                .from(markCsvAsFailedStep())
                	.on("*").to(moveFailedCsvStep())
                .from(moveFailedCsvStep())
                	.end()
                .build();
    }
}
