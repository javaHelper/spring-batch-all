package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;

@Configuration
public class JobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public ListItemReader<HashMap<String, String>> itemReader(){
        HashMap<String, String> item1 = new HashMap<>();
        item1.put("id", "1");
        item1.put("name", "John");

        HashMap<String, String> item2 = new HashMap<>();
        item2.put("id", "2");
        item2.put("name", "Jane");

        HashMap<String, String> item3 = new HashMap<>();
        item3.put("id", "3");
        item3.put("name", "Mike");
        return new ListItemReader<>(Arrays.asList(item1, item2, item3));
    }

    @Bean
    public ItemProcessor<HashMap<String, String>, HashMap<String, String>> itemProcessor(){
        return item -> {
            System.out.println(item.entrySet());
            return item;
        };
    }

    @Bean
    public JdbcBatchItemWriter<HashMap<String, String>> batchItemWriter(){
        String sql = "insert into person (id, name) values (:id, :name)";
        return new JdbcBatchItemWriterBuilder<HashMap<String, String>>()
                .dataSource(dataSource)
                .sql(sql)
                .itemSqlParameterSourceProvider(item -> {
                    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
                    mapSqlParameterSource.addValues(item);
                    return mapSqlParameterSource;
                })
                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .<HashMap<String, String>, HashMap<String, String>>chunk(3)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(batchItemWriter())
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }
}
