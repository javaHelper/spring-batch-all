package com.example.demo.config;

import com.example.demo.mapper.CustomerFieldSetMapper;
import com.example.demo.model.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Autowired
    private KafkaTemplate<Long, Customer> kafkaTemplate;

    @Bean
    public FlatFileItemReader<Customer> customerItemReader() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "firstName", "lastName", "birthdate");

        DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();
        customerLineMapper.setLineTokenizer(tokenizer);
        customerLineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
        customerLineMapper.afterPropertiesSet();

        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("/data/customer.csv"))
                .lineMapper(customerLineMapper)
                .build();
    }

    @Bean
    public KafkaItemWriter<Long, Customer> kafkaItemWriter() throws Exception {
        return new KafkaItemWriterBuilder<Long, Customer>()
                .kafkaTemplate(kafkaTemplate)
                .itemKeyMapper(Customer::getId)
                .delete(false)
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return new StepBuilder("step1", jobRepository)
                .<Customer, Customer>chunk(10, manager)
                .reader(customerItemReader())
                .writer(kafkaItemWriter())
                .build();
    }

    @Bean
    public Job job() throws Exception {
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }
}
