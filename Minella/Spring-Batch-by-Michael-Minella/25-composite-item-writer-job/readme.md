#

```java
package com.example.compositeitemwriterjob;


import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.oxm.xstream.XStreamMarshaller;


@SpringBootApplication
@EnableBatchProcessing
public class CompositeItemWriterJobApplication {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> compositeWriterItemReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {

        return new FlatFileItemReaderBuilder<Customer>()
                .name("compositeWriterItemReader")
                .resource(inputFile)
                .delimited()
                .names("firstName", "middleInitial", "lastName", "address", "city", "state", "zip", "email")
                .targetType(Customer.class)
                .build();
    }

    @SuppressWarnings("rawtypes")
	@Bean(destroyMethod = "")
    @StepScope
    public StaxEventItemWriter<Customer> xmlDelegateItemWriter() throws Exception {
        Map<String, Class> aliases = new HashMap<>();
        aliases.put("customer", Customer.class);

        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(aliases);
        marshaller.afterPropertiesSet();

        String customerOutputPath = File.createTempFile("customerOutput", ".out").getAbsolutePath();
        System.out.println(">> Output Path = "+customerOutputPath);

        return new StaxEventItemWriterBuilder<Customer>()
                .name("customerItemWriter")
                .resource(new FileSystemResource(customerOutputPath))
                .marshaller(marshaller)
                .rootTagName("customers")
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Customer> jdbcDelegateItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .namedParametersJdbcTemplate(new NamedParameterJdbcTemplate(dataSource))
                .sql("INSERT INTO CUSTOMER (first_name, middle_initial, last_name, address, city, state, zip, email) " +
                                    "VALUES(:firstName, :middleInitial, :lastName, :address, :city, :state, :zip, :email)")
                .beanMapped()
                .build();
    }

    @Bean(destroyMethod = "")
    public CompositeItemWriter<Customer> compositeItemWriter() throws Exception {
        return new CompositeItemWriterBuilder<Customer>()
                .delegates(Arrays.asList(xmlDelegateItemWriter(), jdbcDelegateItemWriter(null)))
                .build();
    }


    @Bean
    public Step compositeWriterStep() throws Exception {
        return this.stepBuilderFactory.get("compositeWriterStep")
                .<Customer, Customer>chunk(10)
                .reader(compositeWriterItemReader(null))
                .writer(compositeItemWriter())
                .build();
    }

    @Bean
    public Job compositeWriterJob() throws Exception {
        return this.jobBuilderFactory.get("compositeWriterJob")
                .incrementer(new RunIdIncrementer())
                .start(compositeWriterStep())
                .build();
    }


    public static void main(String[] args) {
        SpringApplication.run(CompositeItemWriterJobApplication.class,
                "customerFile=/data/customer.csv",
                "outputFile=/output/customer.xml");
    }
}

```