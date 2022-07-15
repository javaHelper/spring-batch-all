package com.example.classifiercompositeitemwriterjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableBatchProcessing
public class ClassifierCompositeItemWriterJobApplication {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> classifierCompositeWriterItemReader(@Value("#{jobParameters['customerFile']}") Resource inputFile) {

        return new FlatFileItemReaderBuilder<Customer>()
                .name("classifierCompositeWriterItemReader")
                .resource(inputFile)
                .delimited()
                .names("firstName", "middleInitial", "lastName", "address", "city", "state", "zip", "email")
                .targetType(Customer.class)
                .build();
    }

    @SuppressWarnings("rawtypes")
	@Bean(destroyMethod = "")
    public StaxEventItemWriter<Customer> xmlDelegate() throws Exception {
        String customerOutputPath = File.createTempFile("customerOutput", ".out").getAbsolutePath();
        System.out.println(">> Output Path = " + customerOutputPath);

        Map<String, Class> aliases = new HashMap<>();
        aliases.put("customer", Customer.class);

        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(aliases);
        marshaller.afterPropertiesSet();

        return new StaxEventItemWriterBuilder<Customer>()
                .name("customerItemWriter")
                .resource(new FileSystemResource(customerOutputPath))
                .marshaller(marshaller)
                .rootTagName("customers")
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Customer> jdbcDelgate(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .namedParametersJdbcTemplate(new NamedParameterJdbcTemplate(dataSource))
                .sql("INSERT INTO CUSTOMER (first_name, middle_initial, last_name, address, city,state, zip, email) " +
                        "VALUES(:firstName, :middleInitial, :lastName, :address, :city, :state, :zip, :email)")
                .beanMapped()
                .build();
    }

    @Bean
    public ClassifierCompositeItemWriter<Customer> classifierCompositeItemWriter() throws Exception {
        Classifier<Customer, ItemWriter<? super Customer>> classifier =
                new CustomerClassifier(xmlDelegate(), jdbcDelgate(null));
        return new ClassifierCompositeItemWriterBuilder<Customer>()
                .classifier(classifier)
                .build();
    }


    @Bean
    public Step classifierCompositeWriterStep() throws Exception {
        return this.stepBuilderFactory.get("classifierCompositeWriterStep")
                .<Customer, Customer> chunk(10)
                .reader(classifierCompositeWriterItemReader(null))
                .writer(classifierCompositeItemWriter())
                .stream(xmlDelegate())
                .build();
    }

    @Bean
    public Job classifierCompositeWriterJob() throws Exception {
        return this.jobBuilderFactory.get("classifierCompositeWriterJob")
                .start(classifierCompositeWriterStep())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClassifierCompositeItemWriterJobApplication.class, "customerFile=/data/customer.csv");
    }
}
