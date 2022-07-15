package com.example;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyJobConfig {

	@Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public ItemReader<String> itemReader() {
        return new ListItemReader<>(Arrays.asList(
                "\"1;Paris;13/4/1992;16/7/2006\"",
                "\"2;Lyon;31/5/1993;1/8/2009\"",
                "\"3;Metz;21/4/1990;27/4/2010\"",
                "\"4;Lille;21/4/1980;27/4/2011\""
                ));
    }

    @Bean
    public ItemProcessor<String, String> itemProcessor1() {
        return item -> item.substring(1, item.length() - 1);
    }

    @Bean
    public ItemProcessor<String, Record> itemProcessor2() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("id", "ville");
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        
        BeanWrapperFieldSetMapper<Record> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Record.class);
        
        return item -> {
            FieldSet tokens = lineTokenizer.tokenize(item);
            return fieldSetMapper.mapFieldSet(tokens);
        };
    }

    @Bean
    public ItemWriter<Record> itemWriter() {
        return items -> {
            for (Record item : items) {
                System.out.println(item);
            }
        };
    }

    @Bean
    public CompositeItemProcessor<String, Record> compositeItemProcessor() {
        CompositeItemProcessor<String, Record> compositeItemProcessor = new CompositeItemProcessor<>();
        compositeItemProcessor.setDelegates(Arrays.asList(itemProcessor1(), itemProcessor2()));
        return compositeItemProcessor;
    }

    @Bean
    public Step step() {
        return steps.get("step")
                .<String, Record>chunk(2)
                .reader(itemReader())
                .processor(compositeItemProcessor())
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
