package com.example.demo.config;

import com.example.demo.lineAggregator.CustomerLineAggregator;
import com.example.demo.model.Customer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.util.Properties;

@Configuration
public class JobConfiguration {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager manager;

    @Autowired
    private KafkaProperties properties;

    @Bean
    public KafkaItemReader<Long, Customer> kafkaItemReader() {
        return new KafkaItemReaderBuilder<Long, Customer>()
                .partitions(0)
                .consumerProperties(getProperties())
                .name("customers-reader")
                .saveState(true)
                .topic("customers")
                .build();
    }

    @Bean
    public Properties getProperties() {
        Properties props = new Properties();
        props.putAll(this.properties.buildConsumerProperties());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }


    @Bean
    public FlatFileItemWriter<Customer> customerItemWriter() throws Exception {
        String customerOutputPath = File.createTempFile("customerOutput", ".out").getAbsolutePath();
        System.out.println(">> Output Path = " + customerOutputPath);

        FlatFileItemWriter<Customer> itemWriter = new FlatFileItemWriter<>();
        //A LineAggregator implementation that simply calls Object.toString() on the given object
        //itemWriter.setLineAggregator(new PassThroughLineAggregator<>());

        //Alternate ways
        itemWriter.setLineAggregator(new CustomerLineAggregator());
        itemWriter.setResource(new FileSystemResource(customerOutputPath));
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    @Bean
    public ItemWriter<Customer> customerSysItemWriter(){
        return new ItemWriter<Customer>() {
            @Override
            public void write(Chunk<? extends Customer> chunk) throws Exception {
                System.out.println(chunk);
            }
        };
    }


    @Bean
    public Step step1() throws Exception {
        return new StepBuilder("step1", jobRepository)
                .<Customer, Customer>chunk(100, manager)
                .reader(kafkaItemReader())
                .writer(customerSysItemWriter())
                .build();
    }

    @Bean
    public Job job() throws Exception {
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }
}
