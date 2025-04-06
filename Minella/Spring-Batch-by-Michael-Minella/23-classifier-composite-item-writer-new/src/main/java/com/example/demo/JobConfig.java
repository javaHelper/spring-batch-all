package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ItemReader<Person> itemReader() {
        Person foo1 = new Person();foo1.setId(1);foo1.setName("foo1");
        Person foo2 = new Person();foo2.setId(2);foo2.setName("foo2");
        Person bar1 = new Person();bar1.setId(3);bar1.setName("bar1");
        Person bar2 = new Person();bar2.setId(4);bar2.setName("bar2");

        return new ListItemReader<>(Arrays.asList(foo1, foo2, bar1, bar2));
    }

    @Bean
    public ClassifierCompositeItemWriter<Person> classifierCompositeItemWriter(ItemWriter<Person> fooItemWriter, 
    																		   ItemWriter<Person> barItemWriter) {
        ClassifierCompositeItemWriter<Person> classifierCompositeItemWriter = new ClassifierCompositeItemWriter<>();
        classifierCompositeItemWriter.setClassifier(new PersonClassifier(fooItemWriter(), barItemWriter()));
        return classifierCompositeItemWriter;
    }

    @Bean
    public FlatFileItemWriter<Person> fooItemWriter() {
        return new FlatFileItemWriterBuilder<Person>()
                .name("fooItemWriter")
                .resource(new FileSystemResource("foos.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    @Bean
    public FlatFileItemWriter<Person> barItemWriter() {
        return new FlatFileItemWriterBuilder<Person>()
                .name("barItemWriter")
                .resource(new FileSystemResource("bars.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    @Bean
    public Step dataExtractionStep() {
        return new StepBuilder("dataExtractionStep", jobRepository)
                .<Person, Person>chunk(2, manager)
                .reader(itemReader())
                .writer(classifierCompositeItemWriter(fooItemWriter(), barItemWriter()))
                .stream(fooItemWriter())
                .stream(barItemWriter())
                .build();
    }

    @Bean
    public Job dataExtractionJob() {
        return new JobBuilder("dataExtractionJob", jobRepository)
                .start(dataExtractionStep())
                .build();
    }
}