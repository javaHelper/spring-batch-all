package com.example.fixedwidthjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@SpringBootApplication
public class FixedWidthJobApplication {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> customerItemReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {

        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .resource(inputFile)
                .fixedLength()
                .columns(new Range[]{new Range(1, 11), new Range(12, 12), new Range(13, 22),
                        new Range(23, 26), new Range(27, 46), new Range(47, 62), new Range(63, 64),
                        new Range(65, 69)})
                .names("firstName", "middleInitial", "lastName", "addressNumber", "street", "city", "state", "zipCode")
                .targetType(Customer.class)
                .build();
    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return (items) -> items.forEach(System.out::println);
    }

    @Bean
    public Step copyFileStep() {
        return new StepBuilder("copyFileStep", jobRepository)
                .<Customer, Customer>chunk(100, manager)
                .reader(customerItemReader(null))
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(copyFileStep())
                .build();
    }

    public static void main(String[] args) {
        List<String> realArgs = List.of("customerFile=/input/customerFixedWidth.txt");
        SpringApplication.run(FixedWidthJobApplication.class, realArgs.toArray(new String[1]));
    }
}