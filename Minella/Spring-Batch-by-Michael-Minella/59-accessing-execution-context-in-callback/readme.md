#


```java
package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
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

import java.util.Arrays;

@Configuration
public class JobConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

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
        return writer -> writer.write("total items: " + writeCount);
    }

    @Bean
    public Step step() {
        return steps.get("step")
                .<Integer, Integer>chunk(5)
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

```