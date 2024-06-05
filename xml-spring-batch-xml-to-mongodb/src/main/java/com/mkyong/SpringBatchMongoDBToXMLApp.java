package com.mkyong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SpringBatchMongoDBToXMLApp implements CommandLineRunner{
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBatchMongoDBToXMLApp.class);
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBatchMongoDBToXMLApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("job-report.xml");
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("reportJob");

        try {
            JobExecution execution = jobLauncher.run(job, new JobParameters());
            LOGGER.debug("Exit Status : {}", execution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.debug("Done !");
    }
}