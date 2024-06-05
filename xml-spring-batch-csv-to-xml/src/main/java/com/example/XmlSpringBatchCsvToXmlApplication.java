package com.example;

import com.example.model.Employee;
import com.github.javafaker.Faker;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class XmlSpringBatchCsvToXmlApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(XmlSpringBatchCsvToXmlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		extracted();

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-batch-context.xml");

		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("employeeJob");

		try {
			JobExecution execution = jobLauncher.run(job, new JobParameters());
			System.out.println("Job Exit Status : "+ execution.getStatus());

		} catch (JobExecutionException e) {
			System.out.println("Job ExamResult failed");
			e.printStackTrace();
		}
	}

	private void extracted() {
		Faker faker = Faker.instance();

		LongStream.rangeClosed(1,101)
				.forEach(index -> {
					System.out.println(Employee.create(index, faker.name().firstName(), faker.name().lastName(),
							ThreadLocalRandom.current().nextInt(1,80),
							faker.name().firstName()+"."+ faker.name().lastName()+"@gmail.com"));
				});
	}
}