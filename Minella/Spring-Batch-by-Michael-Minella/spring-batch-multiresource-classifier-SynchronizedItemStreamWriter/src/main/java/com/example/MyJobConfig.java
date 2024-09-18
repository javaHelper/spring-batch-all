package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class MyJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public TaskExecutor taskExecutor() {
    	ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(4);
		taskExecutor.setMaxPoolSize(4);
		taskExecutor.afterPropertiesSet();
		return taskExecutor;
    }
    
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
        Classifier<Employee, ItemWriter<? super Employee>> classifier = new EmployeeClassifier(
                javaDeveloperItemWriter(), 
                pythonDeveloperItemWriter(), 
                cloudDeveloperItemWriter());
        
        return new ClassifierCompositeItemWriterBuilder<Employee>()
                .classifier(classifier)
                .build();
    }

    @Bean
    public ItemWriter<Employee> javaDeveloperItemWriter() {
        FlatFileItemWriter<Employee> itemWriter = new FlatFileItemWriterBuilder<Employee>()
                .lineAggregator(new PassThroughLineAggregator<>())
                .name("iw1")
                .build();

        MultiResourceItemWriter<Employee> multiResourceItemWriter = new MultiResourceItemWriterBuilder<Employee>()
                .name("javaDeveloperItemWriter")
                .delegate(itemWriter)
                .resource(new FileSystemResource("javaDeveloper-employee.csv"))
                .itemCountLimitPerResource(5)
                .resourceSuffixCreator(index -> "-" + index)
                .build();
        
        
        return new SynchronizedItemStreamWriterBuilder<Employee>()
    			.delegate(multiResourceItemWriter)
    			.build();
    }

    @Bean
    public ItemWriter<Employee> pythonDeveloperItemWriter() {
        FlatFileItemWriter<Employee> itemWriter = new FlatFileItemWriterBuilder<Employee>()
                .lineAggregator(new PassThroughLineAggregator<>())
                .name("iw2")
                .build();

        MultiResourceItemWriter<Employee> multiResourceItemWriter = new MultiResourceItemWriterBuilder<Employee>()
                .name("pythonDeveloperItemWriter")
                .delegate(itemWriter)
                .resource(new FileSystemResource("pythonDeveloper-employee.csv"))
                .itemCountLimitPerResource(5)
                .resourceSuffixCreator(index -> "-" + index)
                .build();
        
        return new SynchronizedItemStreamWriterBuilder<Employee>()
    			.delegate(multiResourceItemWriter)
    			.build();
    }

    @Bean
    public ItemWriter<Employee> cloudDeveloperItemWriter() {
        FlatFileItemWriter<Employee> itemWriter = new FlatFileItemWriterBuilder<Employee>()
                .lineAggregator(new PassThroughLineAggregator<>())
                .name("iw3")
                .build();

        MultiResourceItemWriter<Employee> multiResourceItemWriter = new MultiResourceItemWriterBuilder<Employee>()
                .name("cloudDeveloperItemWriter")
                .delegate(itemWriter)
                .resource(new FileSystemResource("cloudDeveloper-employee.csv"))
                .itemCountLimitPerResource(5)
                .resourceSuffixCreator(index -> "-" + index)
                .build();
        
        return new SynchronizedItemStreamWriterBuilder<Employee>()
    			.delegate(multiResourceItemWriter)
    			.build();
    }
        

    @Bean
    public Step step() throws Exception {
        return stepBuilderFactory.get("step")
                .<Employee, Employee>chunk(3)
                .reader(itemReader())
                .writer(classifierCompositeItemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("job")
                .start(step())
                .build();
    }
}