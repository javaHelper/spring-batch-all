package com.example.demo;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@EnableBatchProcessing
@Configuration
public class MyJob {
	
	@Autowired
	private JobBuilderFactory jobs;
	
	@Autowired
	private StepBuilderFactory steps;

	@Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Bean
    public ItemProcessor<Integer, Integer> itemProcessor() {
        return item -> {
            if (item.equals(3)) {
                throw new IllegalArgumentException("no 3 here!");
            }
            if (item.equals(7)) {
                throw new IllegalArgumentException("no 7 here!");
            }
            return item;
        };
    }

    @Bean
    public ItemWriter<Integer> itemWriter() {
        return new FlatFileItemWriterBuilder<Integer>()
                .name("itemWriter")
                .resource(new FileSystemResource("output.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }
    
    @Bean
    public FlatFileItemWriter<Integer> skippedItemWriter() {
        return new FlatFileItemWriterBuilder<Integer>()
                .name("skippedItemWriter")
                .resource(new FileSystemResource("skipped.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }
    
    
    @Bean
    public Job job() {
        return jobs.get("job")
                .start(step1())
                .build();
    }

    @Bean
	public Step step1() {
		return steps.get("step")
		        .<Integer, Integer>chunk(5)
		        .reader(itemReader())
		        .processor(itemProcessor())
		        .writer(itemWriter())
		        .faultTolerant()
		        .skip(IllegalArgumentException.class)
		        .skipLimit(3)
		        .listener(new SkippedItemsListener(skippedItemWriter()))
		        .stream(skippedItemWriter()) // ensure open/close are called properly
		        .build();
	}
}
