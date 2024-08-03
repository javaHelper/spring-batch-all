package com.example.demo;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;

class MyJobListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
        // we know there is a single step here. But in a real world scenario, you would get the execution context of the step you need, 
    	// or use an ExecutionContextPromotionListener and promote the key in the job execution context
    	
        ExecutionContext executionContext = jobExecution.getStepExecutions().iterator().next().getExecutionContext();
        System.out.println("Sending email with error items: " + executionContext.get("errorItems"));
    }
}