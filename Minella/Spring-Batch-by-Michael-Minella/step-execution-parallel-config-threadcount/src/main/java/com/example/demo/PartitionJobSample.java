package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class PartitionJobSample {
    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Step managerStep() {
        return steps.get("masterStep")
                .partitioner(workerStep().getName(), partitioner(null))
                .step(workerStep())
                .gridSize(4)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public SimpleAsyncTaskExecutor taskExecutor() {
        // TODO useful for testing, use a more robust task executor in production
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    @StepScope
    public Partitioner partitioner(@Value("#{jobParameters['rootFolder']}") String rootFolder) {
        List<String> subFolders = getSubFolders(rootFolder);
        return new Partitioner() {
            @Override
            public Map<String, ExecutionContext> partition(int gridSize) {
                Map<String, ExecutionContext> map = new HashMap<>(gridSize);
                for (String folder : subFolders) {
                    ExecutionContext executionContext = new ExecutionContext();
                    executionContext.put("filePath", folder);
                    map.put("partition-for-" + folder, executionContext);
                }
                return map;
            }
        };
    }

    private List<String> getSubFolders(String rootFolder) {
        // TODO implement this
        return Arrays.asList("/data/folder1", "/data/folder2");
    }

    @Bean
    public Step workerStep() {
        return steps.get("workerStep")
                .tasklet(getTasklet(null))
                .build();
    }

    @Bean
    @StepScope
    public Tasklet getTasklet(@Value("#{stepExecutionContext['filePath']}") String filePath) {
        return new TraverseJobTasklet(filePath);
    }

    @Bean
    public Job job() {
        return jobs.get("job")
                .start(managerStep())
                .build();
    }
}