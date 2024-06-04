package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringXmlBatchMysqlCsvApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringXmlBatchMysqlCsvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
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

}
