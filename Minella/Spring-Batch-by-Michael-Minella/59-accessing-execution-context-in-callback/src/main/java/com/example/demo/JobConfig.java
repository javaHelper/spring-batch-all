package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class JobConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Bean
    public ItemWriter<Integer> itemWriter() {
        return new FlatFileItemWriterBuilder<Integer>()
                .name("fileWriter")
                .resource(new FileSystemResource("numbers.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .footerCallback(footerCallback(null))
                .build();
    }

    @Bean
    @StepScope
    public FlatFileFooterCallback footerCallback(@Value("#{stepExecutionContext['fileWriter.written']}") Integer writeCount) {
        System.out.println("writeCount "+ writeCount);
        return writer -> writer.write("total items: " + writeCount);
    }

    @Bean
    public Step step() {
        return new StepBuilder("step", jobRepository)
                .<Integer, Integer>chunk(5, manager)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(step())
                .build();
    }
}