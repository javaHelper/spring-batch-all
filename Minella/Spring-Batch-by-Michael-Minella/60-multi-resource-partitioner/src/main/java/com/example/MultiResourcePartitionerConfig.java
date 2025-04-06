package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MultiResourcePartitionerConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Value("file:input/userdata*.txt")
    private Resource[] resources;

    @Bean
    public MultiResourcePartitioner partitioner(){
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setResources(resources);
        return partitioner;
    }

    @StepScope
    @Bean
    public FlatFileItemReader<String> personFileReader(
            @Value("#{stepExecutionContext['fileName']}") Resource resource) {
        return new FlatFileItemReaderBuilder<String>()
                .name("itemReader")
                .resource(resource)
                .lineMapper(new PassThroughLineMapper())
                .build();
    }

    @Bean
    public Step managerStep() {
        Step workerStep = workerStep();
        return new StepBuilder("managerStep", jobRepository)
                .partitioner(workerStep.getName(), partitioner())
                .step(workerStep)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step workerStep() {
        return new StepBuilder("workerStep", jobRepository)
                .<String, String>chunk(5, manager)
                .reader(personFileReader(null))
                .writer(items -> items.forEach(System.out::println))
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(managerStep())
                .build();
    }
}