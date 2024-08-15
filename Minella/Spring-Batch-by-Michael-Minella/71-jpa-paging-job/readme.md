# Jpa Paging Job

```java
package com.example.jpajob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;


@EnableBatchProcessing
@SpringBootApplication
public class JpaJobApplication {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public JpaPagingItemReader<Customer> customerItemReader(EntityManagerFactory entityManagerFactory,
                                                            @Value("#{jobParameters['city']}") String city) {

        CustomerByCityQueryProvider queryProvider = new CustomerByCityQueryProvider();
        queryProvider.setCityName(city);

        return new JpaPagingItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryProvider(queryProvider)
                .parameterValues(Collections.singletonMap("city", city))
                .build();
    }

    // Alternate way
    @Bean
    @StepScope
    public JpaPagingItemReader<Customer> customerItemReader2(EntityManagerFactory entityManagerFactory,
                                                             @Value("#{jobParameters['city']}") String city) {

        return new JpaPagingItemReaderBuilder<Customer>()
                .name("customerItemReader2")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Customer c where c.city = :city")
                .parameterValues(Collections.singletonMap("city", city))
                .build();
    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return (items) -> items.forEach(System.out::println);
    }

    @Bean
    public Step copyFileStep() {
        return this.stepBuilderFactory.get("copyFileStep")
                .<Customer, Customer>chunk(10)
                .reader(customerItemReader(null, null))
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job")
                .start(copyFileStep())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaJobApplication.class, "city=Juneau");
    }
}

```


```

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::               (v2.7.15)

2024-08-15 17:14:30.770  INFO 25291 --- [           main] com.example.jpajob.JpaJobApplication     : Starting JpaJobApplication using Java 11.0.22 on Prateeks-MBP with PID 25291 (/Users/prats/Documents/Prateek/spring-batch-all/Minella/Spring-Batch-by-Michael-Minella/71-jpa-paging-job/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-all/Minella/Spring-Batch-by-Michael-Minella/71-jpa-paging-job)
2024-08-15 17:14:30.771  INFO 25291 --- [           main] com.example.jpajob.JpaJobApplication     : No active profile set, falling back to 1 default profile: "default"
2024-08-15 17:14:31.010  INFO 25291 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2024-08-15 17:14:31.018  INFO 25291 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 3 ms. Found 0 JPA repository interfaces.
2024-08-15 17:14:31.162  INFO 25291 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2024-08-15 17:14:31.354  INFO 25291 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2024-08-15 17:14:31.498  INFO 25291 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2024-08-15 17:14:31.529  INFO 25291 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.6.15.Final
2024-08-15 17:14:31.612  INFO 25291 --- [           main] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
2024-08-15 17:14:31.678  INFO 25291 --- [           main] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.MySQL8Dialect
2024-08-15 17:14:31.943  INFO 25291 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2024-08-15 17:14:31.948  INFO 25291 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2024-08-15 17:14:32.032  WARN 25291 --- [           main] o.s.b.a.batch.JpaBatchConfigurer         : JPA does not support custom isolation levels, so locks may not be taken when launching Jobs. To silence this warning, set 'spring.batch.jdbc.isolation-level-for-create' to 'default'.
2024-08-15 17:14:32.034  INFO 25291 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: MYSQL
2024-08-15 17:14:32.124  INFO 25291 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2024-08-15 17:14:32.173  INFO 25291 --- [           main] com.example.jpajob.JpaJobApplication     : Started JpaJobApplication in 1.562 seconds (JVM running for 6.922)
2024-08-15 17:14:32.174  INFO 25291 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: [city=Juneau]
2024-08-15 17:14:32.259  INFO 25291 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{city=Juneau}]
2024-08-15 17:14:32.289  INFO 25291 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [copyFileStep]
Hibernate: 
    select
        customer0_.id as id1_0_,
        customer0_.address as address2_0_,
        customer0_.city as city3_0_,
        customer0_.firstName as firstnam4_0_,
        customer0_.lastName as lastname5_0_,
        customer0_.middleInitial as middlein6_0_,
        customer0_.state as state7_0_,
        customer0_.zipCode as zipcode8_0_ 
    from
        customer customer0_ 
    where
        customer0_.city=? limit ?
Customer(id=1, firstName=Melinda, middleInitial=A, lastName=Frank, address=P.O. Box 290, 520 Hendrerit. Ave, city=Juneau, state=AK, zipCode=99658)
Customer(id=91, firstName=Halee, middleInitial=M, lastName=Garrison, address=6478 Nunc Street, city=Juneau, state=AK, zipCode=99581)
Customer(id=166, firstName=Griffin, middleInitial=J, lastName=Fitzpatrick, address=884-3002 At Street, city=Juneau, state=AK, zipCode=99585)
Customer(id=388, firstName=Nigel, middleInitial=X, lastName=Miles, address=1085 Morbi Road, city=Juneau, state=AK, zipCode=99899)
2024-08-15 17:14:32.402  INFO 25291 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [copyFileStep] executed in 113ms
2024-08-15 17:14:32.413  INFO 25291 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{city=Juneau}] and the following status: [COMPLETED] in 139ms
2024-08-15 17:14:32.417  INFO 25291 --- [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2024-08-15 17:14:32.418  INFO 25291 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2024-08-15 17:14:32.426  INFO 25291 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

```