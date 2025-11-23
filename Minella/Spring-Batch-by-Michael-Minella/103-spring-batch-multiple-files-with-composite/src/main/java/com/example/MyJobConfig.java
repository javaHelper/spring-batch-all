package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MyJobConfig {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    public FlatFileItemReader<Employee> itemReader() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("empId", "firstName", "lastName", "role");

        DefaultLineMapper<Employee> employeeLineMapper = new DefaultLineMapper<>();
        employeeLineMapper.setLineTokenizer(tokenizer);
        employeeLineMapper.setFieldSetMapper(new EmployeeFieldSetMapper());
        employeeLineMapper.afterPropertiesSet();

        return new FlatFileItemReaderBuilder<Employee>()
                .name("flatFileReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("employee.csv"))
                .lineMapper(employeeLineMapper)
                .build();
    }

    @Bean
    public ClassifierCompositeItemWriter<Employee> classifierCompositeItemWriter() throws Exception {
        Classifier<Employee, ItemWriter<? super Employee>> classifier =
                new EmployeeClassifier(javaDeveloperItemWriter(), pythonDeveloperItemWriter(), cloudDeveloperItemWriter());
        return new ClassifierCompositeItemWriterBuilder<Employee>()
                .classifier(classifier)
                .build();
    }

    @Bean
    public ItemWriter<Employee> javaDeveloperItemWriter() {
        FlatFileItemWriter<Employee> itemWriter = new FlatFileItemWriterBuilder<Employee>()
                .lineAggregator(new PassThroughLineAggregator<>())
                .name("itemsWriter")
                .build();

        return new MultiResourceItemWriterBuilder<Employee>()
                .name("javaDeveloperItemWriter")
                .delegate(itemWriter)
                .resource(new FileSystemResource("javaDeveloper-employee.csv"))
                .itemCountLimitPerResource(2)
                .resourceSuffixCreator(index -> "-" + index)
                .build();
    }

    @Bean
    public ItemWriter<Employee> pythonDeveloperItemWriter() {
        FlatFileItemWriter<Employee> itemWriter = new FlatFileItemWriterBuilder<Employee>()
                .lineAggregator(new PassThroughLineAggregator<>())
                .name("itemsWriter")
                .build();

        return new MultiResourceItemWriterBuilder<Employee>()
                .name("pythonDeveloperItemWriter")
                .delegate(itemWriter)
                .resource(new FileSystemResource("pythonDeveloper-employee.csv"))
                .itemCountLimitPerResource(2)
                .resourceSuffixCreator(index -> "-" + index)
                .build();
    }

    @Bean
    public ItemWriter<Employee> cloudDeveloperItemWriter() {
        FlatFileItemWriter<Employee> itemWriter = new FlatFileItemWriterBuilder<Employee>()
                .lineAggregator(new PassThroughLineAggregator<>())
                .name("itemsWriter")
                .build();

        return new MultiResourceItemWriterBuilder<Employee>()
                .name("cloudDeveloperItemWriter")
                .delegate(itemWriter)
                .resource(new FileSystemResource("cloudDeveloper-employee.csv"))
                .itemCountLimitPerResource(2)
                .resourceSuffixCreator(index -> "-" + index)
                .build();
    }

    @Bean
    public Step step() throws Exception {
        return new StepBuilder("step", jobRepository)
                .<Employee, Employee>chunk(1, manager)
                .reader(itemReader())
                .writer(classifierCompositeItemWriter())
                .build();
    }

    @Bean
    public Job job() throws Exception {
        return new JobBuilder("job", jobRepository)
                .start(step())
                .build();
    }
}