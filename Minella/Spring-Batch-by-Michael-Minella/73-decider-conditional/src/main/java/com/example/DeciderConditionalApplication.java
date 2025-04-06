package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class DeciderConditionalApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeciderConditionalApplication.class, args);
    }


    @Configuration
    static class MyJobConfig {
        @Autowired
        private JobRepository jobRepository;

        @Autowired
        private PlatformTransactionManager manager;

        @Bean
        public Step firstStep() {
            return new StepBuilder("firstStep", jobRepository)
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("## firstStep");
                        return RepeatStatus.FINISHED;
                    }, manager)
                    .build();
        }

        @Bean
        public JobExecutionDecider decider() {
            return (jobExecution, stepExecution) -> new FlowExecutionStatus("TYPE1"); // or TYPE2
        }

        @Bean
        public Step stepType1() {
            return new StepBuilder("stepType1", jobRepository)
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("## stepType1");
                        return RepeatStatus.FINISHED;
                    }, manager)
                    .build();
        }

        @Bean
        public Step stepType2() {
            return new StepBuilder("stepType2", jobRepository)
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("##@@ stepType2");
                        return RepeatStatus.FINISHED;
                    }, manager)
                    .build();
        }

        @Bean
        public Step lastStep() {
            return new StepBuilder("lastStep", jobRepository)
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("## lastStep");
                        return RepeatStatus.FINISHED;
                    }, manager)
                    .build();
        }

        @Bean
        public Job job() {
            return new JobBuilder("job", jobRepository)
                    .start(firstStep())
                    .next(decider())
                        .on("TYPE1").to(stepType1())
                    .from(decider())
                        .on("TYPE2").to(stepType2())
                    .from(stepType1())
                        .on("*").to(lastStep())
                    .from(stepType2())
                        .on("*").to(lastStep())
                    .build()
                    .build();
        }
    }
}