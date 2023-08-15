package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class JobConfiguration {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4));
    }

    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[] {"count"});
        return listener;
    }

    @Bean
    public MyItemWriter itemWriter(){
        return new MyItemWriter();
    }

    @Bean
    public Step step1(){
        return steps.get("step1")
                .<Integer, Integer> chunk(2)
                .reader(itemReader())
                .writer(itemWriter())
                .listener(promotionListener())
                .build();
    }

    @Bean
    public Step step2(){
        return steps.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        // retrieve the key from the job execution context
                        Integer count = (Integer) chunkContext.getStepContext().getJobExecutionContext().get("count");
                        System.out.println("In step 2: step 1 wrote " + count + " items");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Job job(){
        return jobs.get("job")
                .start(step1())
                .next(step2())
                .build();
    }
}
