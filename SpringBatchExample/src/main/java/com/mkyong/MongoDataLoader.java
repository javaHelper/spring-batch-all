package com.mkyong;

import com.mkyong.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MongoDataLoader implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MongoDataLoader.class, args);
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {
        IntStream.rangeClosed(1,5)
                .forEach(index -> {
                    Report report = Report.builder()
                            .id(index)
                            .date(LocalDate.now())
                            .clicks(ThreadLocalRandom.current().nextInt(30, 100))
                            .earning(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1000, 10000)))
                            .impression(ThreadLocalRandom.current().nextLong(100, 200))
                            .build();
                    mongoTemplate.save(report);
                });
    }
}