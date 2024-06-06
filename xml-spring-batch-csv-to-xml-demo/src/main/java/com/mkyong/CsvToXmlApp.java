package com.mkyong;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CsvToXmlApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CsvToXmlApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] springConfig = {"spring/batch/jobs/job-hello-world.xml"};

        ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("helloWorldJob");

        try {
            JobExecution execution = jobLauncher.run(job, new JobParameters());
            System.out.println("Exit Status : " + execution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done");
    }
}