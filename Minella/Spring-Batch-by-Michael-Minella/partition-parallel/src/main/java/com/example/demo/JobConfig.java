package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public SimpleAsyncTaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public Partitioner partitioner(){
        return gridSize -> {
            Map<String, ExecutionContext> map = new HashMap<>(gridSize);
            for (int i = 0; i < gridSize; i++) {
                ExecutionContext executionContext = new ExecutionContext();
                executionContext.put("data", "data" + i);
                String key = "partition" + i;
                map.put(key, executionContext);
            }
            return map;
        };
    }

    @Bean
    public Step workerStep() {
        return steps.get("workerStep")
                .tasklet(getTasklet(null))
                .build();
    }

    @Bean
    public Step step1() {
        return steps.get("step1")
                .partitioner(workerStep().getName(), partitioner())
                .step(workerStep())
                .gridSize(3)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet getTasklet(@Value("#{stepExecutionContext['data']}") String partitionData) {
        return (contribution, chunkContext) -> {
//            if (partitionData.equals("data2")) {
//                throw new Exception("Boom!");
//            }
            System.out.println(Thread.currentThread().getName() + " processing partitionData = " + partitionData);
            Files.createFile(Paths.get(partitionData + ".txt"));
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step moveFilesStep() {
        return steps.get("moveFilesStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("moveFilesStep");
                    // add code to move files where needed
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step cleanupFilesStep() {
        return steps.get("cleanupFilesStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("cleaning up..");
                    deleteFiles();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    private static void deleteFiles() throws IOException {
        for (int i = 0; i <= 2; i++) {
            Files.deleteIfExists(Paths.get("data" + i + ".txt"));
        }
    }

    @Bean
    public Job job() {
        return jobs.get("job")
                .flow(step1()).on("FAILED").to(cleanupFilesStep())
                .from(step1()).on("*").to(moveFilesStep())
                .from(moveFilesStep()).on("*").end()
                .from(cleanupFilesStep()).on("*").fail()
                .build()
                .build();
    }
}
