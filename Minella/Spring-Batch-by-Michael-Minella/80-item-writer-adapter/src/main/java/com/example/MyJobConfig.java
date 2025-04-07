package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MyJobConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

	@Bean
    public ItemReader<User> itemReader() {
		List<User> users = Arrays.asList(new User("foo"), new User("bar"));
        return new ListItemReader<>(users);
    }
	
	@Bean
    public ItemWriterAdapter<User> itemWriter() {
        ItemWriterAdapter<User> writer = new ItemWriterAdapter<>();
        writer.setTargetObject(new MyService());
        writer.setTargetMethod("putUser");
        return writer;
    }
	
	@Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(new StepBuilder("step", jobRepository)
                        .<User, User>chunk(5, manager)
                        .reader(itemReader())
                        .writer(itemWriter())
                        .build())
                .build();
    }
}