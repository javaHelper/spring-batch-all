package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class ExistingServiceJobApplication {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Autowired
    private ExistingServiceJobListener existingServiceJobListener;

    @Bean
    public ItemReaderAdapter<Customer> customerItemReader(CustomerService customerService) {
        ItemReaderAdapter<Customer> adapter = new ItemReaderAdapter<>();
        adapter.setTargetObject(customerService);
        adapter.setTargetMethod("getCustomer");
        return adapter;
    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return (items) -> items.forEach(System.out::println);
    }

    @Bean
    public Step copyFileStep() {
        return new StepBuilder("copyFileStep", jobRepository)
                .<Customer, Customer>chunk(10, manager)
                .reader(customerItemReader(null))
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(copyFileStep())
                .listener(existingServiceJobListener)
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ExistingServiceJobApplication.class, args);
    }
}