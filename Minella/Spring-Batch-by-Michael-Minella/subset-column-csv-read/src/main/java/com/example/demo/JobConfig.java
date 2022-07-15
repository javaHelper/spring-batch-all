package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class JobConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public FlatFileItemReader<Person> itemReader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("persons.csv"))
                .delimited()
                .includedFields(new Integer[] {0, 2})
                .names("id", "lastName")
                .targetType(Person.class)
                .build();
    }

    @Bean
    public ItemWriter<Person> itemWriter() {
        return items -> {
            for (Person item : items) {
                System.out.println("person = " + item);
            }
        };
    }

    @Bean
    public Step step() {
        return steps.get("step")
                .<Person, Person>chunk(1)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return jobs.get("job")
                .start(step())
                .build();
    }
}
