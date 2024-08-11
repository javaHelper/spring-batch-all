# multipleFlatFiles

```java
package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.example.domain.Customer;
import com.example.mapper.CustomerFieldSetMapper;

@Configuration
public class JobConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Value("classpath*:/data/customer*.csv")
	private Resource[] inputFiles;

	@Bean
	public MultiResourceItemReader<Customer> multiResourceItemReader() {
		return new MultiResourceItemReaderBuilder<Customer>()
				.name("multiResourceItemReader")
				.delegate(customerItemReader())
				.resources(inputFiles)
				.build();
	}

	@Bean
	public FlatFileItemReader<Customer> customerItemReader() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("id", "firstName", "lastName", "birthdate");

		DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();
		customerLineMapper.setLineTokenizer(tokenizer);
		customerLineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
		customerLineMapper.afterPropertiesSet();

		FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
		reader.setLineMapper(customerLineMapper);

		return reader;
	}

	@Bean
	public ItemWriter<Customer> customerItemWriter(){
		return items -> {
			for (Customer customer : items) {
				System.out.println(customer.toString());
			}
		};
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Customer, Customer>chunk(10)
				.reader(multiResourceItemReader())
				.writer(customerItemWriter())
				.build();
	}
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
}

```


````
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 23:02:54.571  INFO 69678 --- [           main] c.example.MultipleFlatFilesApplication   : Starting MultipleFlatFilesApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 69678 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles)
2023-08-13 23:02:54.578  INFO 69678 --- [           main] c.example.MultipleFlatFilesApplication   : No active profile set, falling back to 1 default profile: "default"
2023-08-13 23:02:56.509  INFO 69678 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-08-13 23:02:56.841  INFO 69678 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-08-13 23:02:57.242  INFO 69678 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: H2
2023-08-13 23:02:57.402  INFO 69678 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 23:02:57.618  INFO 69678 --- [           main] c.example.MultipleFlatFilesApplication   : Started MultipleFlatFilesApplication in 4.031 seconds (JVM running for 4.773)
2023-08-13 23:02:57.624  INFO 69678 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-13 23:02:57.683  INFO 69678 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{}]
2023-08-13 23:02:57.812  INFO 69678 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
Customer(id=1, firstName=John, lastName=Doe, birthdate=1952-10-10T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer1.csv])
Customer(id=2, firstName=Amy, lastName=Eugene, birthdate=1985-07-05T17:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer1.csv])
Customer(id=3, firstName=Laverne, lastName=Mann, birthdate=1988-12-11T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer1.csv])
Customer(id=4, firstName=Janice, lastName=Preston, birthdate=1960-02-19T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer1.csv])
Customer(id=5, firstName=Pauline, lastName=Rios, birthdate=1977-08-29T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer1.csv])
Customer(id=6, firstName=Perry, lastName=Burnside, birthdate=1981-03-10T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer1.csv])
Customer(id=7, firstName=Todd, lastName=Kinsey, birthdate=1998-12-14T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer1.csv])
Customer(id=8, firstName=Jacqueline, lastName=Hyde, birthdate=1983-03-20T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer1.csv])
Customer(id=9, firstName=Rico, lastName=Hale, birthdate=2000-10-10T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer1.csv])
Customer(id=10, firstName=Samuel, lastName=Lamm, birthdate=1999-11-11T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer1.csv])
Customer(id=11, firstName=Robert, lastName=Coster, birthdate=1972-10-10T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer2.csv])
Customer(id=12, firstName=Tamara, lastName=Soler, birthdate=1978-01-02T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer2.csv])
Customer(id=13, firstName=Justin, lastName=Kramer, birthdate=1951-11-19T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer2.csv])
Customer(id=14, firstName=Andrea, lastName=Law, birthdate=1959-10-14T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer2.csv])
Customer(id=15, firstName=Laura, lastName=Porter, birthdate=2010-12-12T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer2.csv])
Customer(id=16, firstName=Michael, lastName=Cantu, birthdate=1999-04-11T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer2.csv])
Customer(id=17, firstName=Andrew, lastName=Thomas, birthdate=1967-05-04T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer2.csv])
Customer(id=18, firstName=Jose, lastName=Hannah, birthdate=1950-09-16T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer2.csv])
Customer(id=19, firstName=Valerie, lastName=Hilbert, birthdate=1966-06-13T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer2.csv])
Customer(id=20, firstName=Patrick, lastName=Durham, birthdate=1978-10-12T10:10:10, resource=file [/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/12-multipleFlatFiles/target/classes/data/customer2.csv])
2023-08-13 23:02:57.906  INFO 69678 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 92ms
2023-08-13 23:02:57.921  INFO 69678 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 145ms
2023-08-13 23:02:57.946  INFO 69678 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2023-08-13 23:02:57.958  INFO 69678 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

Process finished with exit code 0

````