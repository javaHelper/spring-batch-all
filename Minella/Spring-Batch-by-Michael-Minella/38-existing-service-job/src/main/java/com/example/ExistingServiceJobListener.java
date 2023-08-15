package com.example;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ExistingServiceJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("===== ExistingServiceJobListener | beforeJob executed | JobId = " + jobExecution.getJobId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("==== ExistingServiceJobListener | afterJob executed | JobId = " + jobExecution.getJobId());
        System.out.println("-----------------------");
        System.out.println("STATUS     : " + jobExecution.getStatus());
        System.out.println("START_TIME : " + jobExecution.getStartTime());
        System.out.println("END_TIME   : " + jobExecution.getEndTime());
        System.out.println("JOB_NAME   : " + jobExecution.getJobInstance().getJobName());
    }
}