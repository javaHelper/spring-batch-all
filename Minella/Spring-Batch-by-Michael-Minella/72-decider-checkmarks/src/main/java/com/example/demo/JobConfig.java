package com.example.demo;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
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

@Configuration
public class JobConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    public Step validateCsvHeaderStepStep(){
        return new StepBuilder("validateCsvHeaderStepStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("validateCsvHeaderStep");
                        return RepeatStatus.FINISHED;
                    }
                }, manager).build();
    }

    @Bean
    public Step processCsvStep(){
        return new StepBuilder("processCsvStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("processCsvStep");
                        return RepeatStatus.FINISHED;
                    }
                }, manager).build();
    }

    @Bean
    public Step markCsvAsFailedStep(){
        return new StepBuilder("markCsvAsFailedStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("markCsvAsFailedStep");
                        return RepeatStatus.FINISHED;
                    }
                }, manager).build();
    }

    @Bean
    public Step moveFailedCsvStep(){
        return new StepBuilder("moveFailedCsvStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("moveFailedCsvStep");
                        return RepeatStatus.FINISHED;
                    }
                }, manager).build();
    }

    @Bean
    public Step moveCsvStep(){
        return new StepBuilder("moveCsvStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("moveCsvStep");
                        return RepeatStatus.FINISHED;
                    }
                }, manager).build();
    }

    @Bean
    public Job job(){
        return new JobBuilder("job", jobRepository)
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