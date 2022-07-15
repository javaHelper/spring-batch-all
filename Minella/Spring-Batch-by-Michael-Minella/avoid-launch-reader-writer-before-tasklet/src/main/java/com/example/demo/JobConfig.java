package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class JobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step validateStep(){
        return stepBuilderFactory.get("validateStep")
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
                }).build();
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
        return stepBuilderFactory.get("readAndWriteCsvFile")
                .<Integer, Integer>chunk(2)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(validateStep())
                .next(readAndWriteCsvFile())
                .build();
    }
}
