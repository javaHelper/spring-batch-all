package com.example.scriptitemprocessorjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.ScriptItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class ScriptItemProcessorJobApplication {
    @Autowired
    public JobRepository jobRepository;

    @Autowired
    public PlatformTransactionManager manager;

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> customerItemReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {

        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .delimited()
                .names("firstName", "middleInitial", "lastName", "address", "city", "state", "zip")
                .targetType(Customer.class)
                .resource(inputFile)
                .build();
    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return (items) -> items.forEach(System.out::println);
    }

    @Bean
    @StepScope
    public ScriptItemProcessor<Customer, Customer> itemProcessor(@Value("#{jobParameters['script']}") Resource script) {
        ScriptItemProcessor<Customer, Customer> itemProcessor = new ScriptItemProcessor<>();
        itemProcessor.setScript(script);
        return itemProcessor;
    }

    @Bean
    public Step copyFileStep() {
        return new StepBuilder("copyFileStep", jobRepository)
                .<Customer, Customer>chunk(5, manager)
                .reader(customerItemReader(null))
                .processor(itemProcessor(null))
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() throws Exception {
        return new JobBuilder("job", jobRepository)
                .start(copyFileStep())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ScriptItemProcessorJobApplication.class, "customerFile=/input/customer.csv", "script=/upperCase.js");
    }
}
