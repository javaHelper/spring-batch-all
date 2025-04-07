package com.example.mongodbjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoPagingItemReader;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class MongodbJobApplication {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Bean
    @StepScope
    public MongoPagingItemReader<Map> tweetsItemReader(MongoOperations mongoTemplate,
                                                                  @Value("#{jobParameters['hashTag']}") String hashtag) {

        return new MongoPagingItemReaderBuilder<Map>()
                .name("tweetsItemReader")
                .targetType(Map.class)
                .jsonQuery("{ \"entities.hashtags.text\": { $eq: ?0 }}")
                .collection("tweets_collection")
                .parameterValues(Collections.singletonList(hashtag))
                .pageSize(10)
                .sorts(Collections.singletonMap("created_at", Sort.Direction.ASC))
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public ItemWriter<Map> itemWriter() {
        return (items) -> items.forEach(System.out::println);
    }

    @Bean
    public Step copyFileStep() {
        return new StepBuilder("copyFileStep", jobRepository)
                .<Map, Map>chunk(10, manager)
                .reader(tweetsItemReader(null, null))
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(copyFileStep())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(MongodbJobApplication.class, "hashTag=nodejs");
    }
}