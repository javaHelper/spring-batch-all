package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class JobConfig {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    public Step validateStep(){
        return new StepBuilder("validateStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        if(!isValid()){
                            throw new Exception("Invalid File !!");
                        }
                        return RepeatStatus.FINISHED;
                    }

                    private boolean isValid(){
                        // TODO implement validation logic
                        return false;
                    }
                }, manager).build();
    }

    @Bean
    public ListItemReader<Integer> itemReader(){
        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4));
    }

    @Bean
    public ItemWriter<Integer> itemWriter(){
        return items -> {
            items.forEach(System.out::println);
        };
    }

    @Bean
    public Step readAndWriteCsvFile(){
        return new StepBuilder("readAndWriteCsvFile", jobRepository)
                .<Integer, Integer>chunk(2, manager)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job(){
        return new JobBuilder("job", jobRepository)
                .start(validateStep())
                .next(readAndWriteCsvFile())
                .build();
    }
}