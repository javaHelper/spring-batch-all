package com.example;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableBatchProcessing
public class JsonJobApplication {
    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        return objectMapper;
    }

    @Bean
    @StepScope
    public JsonItemReader<Customer> customerFileReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {
        JacksonJsonObjectReader<Customer> jsonObjectReader = new JacksonJsonObjectReader<>(Customer.class);
        jsonObjectReader.setMapper(getObjectMapper());

        return new JsonItemReaderBuilder<Customer>()
                .name("customerFileReader")
                .jsonObjectReader(jsonObjectReader)
                .resource(inputFile)
                .build();
    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return (items) -> {
        	System.out.println("-----------------------");
        	items.forEach(System.out::println);
        };
    }

    @Bean
    public Step copyFileStep() {
        return this.stepBuilderFactory.get("copyFileStep")
                .<Customer, Customer>chunk(10)
                .reader(customerFileReader(null))
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job")
                .start(copyFileStep())
                .build();
    }

    public static void main(String[] args) {
        List<String> realArgs = Collections.singletonList("customerFile=/input/customer.json");
        SpringApplication.run(JsonJobApplication.class, realArgs.toArray(new String[1]));
    }
}
