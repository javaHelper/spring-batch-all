package com.example;

import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ChunkScanningTest.JobConfiguration.class)
public class ChunkScanningTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate.update("CREATE TABLE people (id INT IDENTITY NOT NULL PRIMARY KEY, name VARCHAR(20));");
    }

    @Test
    public void testChunkScanningWhenSkippableExceptionInWrite() throws Exception {
        // given
        int peopleCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "people");
        Assert.assertEquals(0, peopleCount);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // then
        peopleCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "people");
        int fooCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "people", "id = 1 and name = 'foo'");
        int bazCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "people", "id = 3 and name = 'baz'");
        Assert.assertEquals(1, fooCount); // foo is inserted
        Assert.assertEquals(1, bazCount); // baz is inserted
        Assert.assertEquals(2, peopleCount); // bar is not inserted

        Assert.assertEquals(ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
        Assert.assertEquals(3, stepExecution.getCommitCount()); // one commit for foo + one commit for baz + one commit for the last (empty) chunk
        Assert.assertEquals(2, stepExecution.getRollbackCount()); // initial rollback for whole chunk + one rollback for bar
        Assert.assertEquals(2, stepExecution.getWriteCount()); // only foo and baz have been written
    }

    @Configuration
    @EnableBatchProcessing
    public static class JobConfiguration {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.HSQL)
                    .addScript("/org/springframework/batch/core/schema-drop-hsqldb.sql")
                    .addScript("/org/springframework/batch/core/schema-hsqldb.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public ItemReader<Person> itemReader() {
            Person foo = new Person(1, "foo");
            Person bar = new Person(2, "bar");
            Person baz = new Person(3, "baz");
            return new ListItemReader<>(Arrays.asList(foo, bar, baz));
        }

        @Bean
        public ItemWriter<Person> itemWriter() {
            return new PersonItemWriter(dataSource());
        }

        @Bean
        public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
            return jobBuilderFactory.get("job")
                    .start(stepBuilderFactory.get("step")
                            .<Person, Person>chunk(3)
                            .reader(itemReader())
                            .writer(itemWriter())
                            .faultTolerant()
                            .skip(IllegalStateException.class)
                            .skipLimit(10)
                            .build())
                    .build();
        }

        @Bean
        public JobLauncherTestUtils jobLauncherTestUtils() {
            return new JobLauncherTestUtils();
        }
    }

    public static class PersonItemWriter implements ItemWriter<Person> {

        private JdbcTemplate jdbcTemplate;

        PersonItemWriter(DataSource dataSource) {
            this.jdbcTemplate = new JdbcTemplate(dataSource);
        }

        @Override
        public void write(List<? extends Person> items) {
            System.out.println("Writing items: "); items.forEach(System.out::println);
            for (Person person : items) {
                if ("bar".equalsIgnoreCase(person.getName())) {
                    System.out.println("Throwing exception: No bars here!");
                    throw new IllegalStateException("No bars here!");
                }
                jdbcTemplate.update("INSERT INTO people (id, name) VALUES (?, ?)", person.getId(), person.getName());
            }
        }
    }

    public static class Person {

        private long id;

        private String name;

        public Person() {
        }

        Person(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
