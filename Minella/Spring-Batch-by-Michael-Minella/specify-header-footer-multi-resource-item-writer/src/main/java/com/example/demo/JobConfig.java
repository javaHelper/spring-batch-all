package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Arrays;

@Configuration
public class JobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }
    
    @Bean
    public ItemWriter<Integer> integerItemWriter(){
        FlatFileItemWriter<Integer> itemWriter = new FlatFileItemWriterBuilder<Integer>()
                .lineAggregator(new PassThroughLineAggregator<>())
                .name("itemsWriter")
                .headerCallback(writer -> writer.write("header"))
                .footerCallback(writer -> writer.write("footer"))
                .build();

        return new MultiResourceItemWriterBuilder<Integer>()
                .name("multiResourcesWriter")
                .delegate(itemWriter)
                .resource(new FileSystemResource("items"))
                .itemCountLimitPerResource(5)
                .resourceSuffixCreator(index -> "-" + index +".txt")
                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .<Integer, Integer> chunk(5)
                .reader(itemReader())
                .writer(integerItemWriter())
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }
}
