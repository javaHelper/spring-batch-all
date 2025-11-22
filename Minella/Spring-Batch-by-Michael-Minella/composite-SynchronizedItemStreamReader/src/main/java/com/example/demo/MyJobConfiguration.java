package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.SynchronizedItemStreamWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class MyJobConfiguration {

    @Autowired
    private PlatformTransactionManager manager;
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private DataSource dataSource;

    @Bean
    public SynchronizedItemStreamReader<Person> itemReader() {
        JdbcCursorItemReader<Person> personItemReader = new JdbcCursorItemReaderBuilder<Person>()
                .name("personItemReader")
                .dataSource(dataSource)
                .sql("select * from person")
                .beanRowMapper(Person.class)
                .build();
        SynchronizedItemStreamReader<Person> synchronizedItemStreamReader = new SynchronizedItemStreamReader<>();
        synchronizedItemStreamReader.setDelegate(personItemReader);
        return synchronizedItemStreamReader;
    }

    @Bean(destroyMethod = "")
    public SynchronizedItemStreamWriter<Person> itemWriter() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Person.class);

        StaxEventItemWriter<Person> personStaxEventItemWriter = new StaxEventItemWriterBuilder<Person>()
                .name("personItemWriter")
                .resource(new FileSystemResource("persons.xml"))
                .marshaller(marshaller)
                .rootTagName("persons")
                .build();

        SynchronizedItemStreamWriter<Person> synchronizedItemStreamWriter = new SynchronizedItemStreamWriter<>();
        synchronizedItemStreamWriter.setDelegate(personStaxEventItemWriter);
        return synchronizedItemStreamWriter;
    }

    // WITH THIS BEAN CODE WILL NOT WORK
    //    @Bean
    //    public StaxEventItemWriter<Person> itemWriter() {
    //        Jaxb2Marshaller marchaller = new Jaxb2Marshaller();
    //        marchaller.setClassesToBeBound(Person.class);
    //        return new StaxEventItemWriterBuilder<Person>()
    //                .name("personItemWriter")
    //                .resource(new FileSystemResource("persons.xml"))
    //                .marshaller(marchaller)
    //                .rootTagName("persons")
    //                .build();
    //    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(step1())
                .build();
    }

    @Bean
    public TaskletStep step1() {
        return new StepBuilder("step", jobRepository)
                .<Person, Person>chunk(5, manager)
                .reader(itemReader())
                .writer(itemWriter())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    //    @Bean
    //    public DataSource dataSource() {
    //        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
    //                .setType(EmbeddedDatabaseType.HSQL)
    //                .addScript("/org/springframework/batch/core/schema-hsqldb.sql")
    //                .build();
    //        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
    //        jdbcTemplate.execute("create table person (id int primary key, name varchar(20));");
    //        for (int i = 1; i <= 10; i++) {
    //            jdbcTemplate.execute(String.format("insert into person values (%s, 'foo%s');", i, i));
    //        }
    //        return embeddedDatabase;
    //    }
}
