package com.example;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class BatchConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    private final JdbcTemplate jdbcTemplate;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager manager;

    public BatchConfiguration(EntityManagerFactory entityManagerFactory, JdbcTemplate jdbcTemplate, JobRepository jobRepository, PlatformTransactionManager manager) {
        this.entityManagerFactory = entityManagerFactory;
        this.jdbcTemplate = jdbcTemplate;
        this.jobRepository = jobRepository;
        this.manager = manager;
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(step1())
                .next(step2())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Person, Person>chunk(1000, manager)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ListItemReader<Person> itemReader() {
        List<Person> items = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            items.add(new Person("foo" + i));
        }
        return new ListItemReader<>(items);
    }

    @Bean
    public JpaItemWriter<Person> itemWriter() {
        JpaItemWriter<Person> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(this.entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    String countQuery = "select count(id) from person";
                    Integer nbPersonsPersisted = this.jdbcTemplate.queryForObject(countQuery, Integer.class);
                    System.out.println(String.format("%s persons have been persisted", nbPersonsPersisted));
                    return RepeatStatus.FINISHED;
                }, manager).build();
    }

}