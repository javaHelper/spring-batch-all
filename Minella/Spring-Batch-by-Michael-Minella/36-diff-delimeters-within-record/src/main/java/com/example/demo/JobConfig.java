package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    public FlatFileItemReader<Person> flatFileItemReader(){
        DefaultLineMapper<Person> lineMapper =new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer("|"));
        lineMapper.setFieldSetMapper(new PersonMapper());

        return new FlatFileItemReaderBuilder<Person>()
                .name("flatFileItemReader")
                .resource(new ClassPathResource("persons.csv"))
                .lineMapper(lineMapper)
                .build();
    }

    @Bean
    public ItemWriter<Person> itemWriter(){
        return items -> {
           items.forEach(System.out::println);
        };
    }

    @Bean
    public Step step1(){
        return new StepBuilder("step1", jobRepository)
                .<Person, Person>chunk(2, manager)
                .reader(flatFileItemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job(){
        return new JobBuilder("job", jobRepository)
                .start(step1())
                .build();
    }
}