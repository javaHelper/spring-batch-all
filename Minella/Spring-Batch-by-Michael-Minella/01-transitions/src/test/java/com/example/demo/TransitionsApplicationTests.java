package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBatchTest
public class TransitionsApplicationTests {

	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    @Test
    void contextLoads() throws Exception {
        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        
        Collection<StepExecution> stepExecutions = jobExecution.getStepExecutions();
        

        // then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        
        assertThat(stepExecutions)
        	.isNotNull()
        	.extracting(e -> e.getStepName())
        	.containsExactly("step1","step2","step3");
        
        
        assertThat(stepExecutions)
    	.isNotNull()
    	.extracting(e -> e.getStepName(), e -> e.getExitStatus(), e -> e.getJobExecution())
    	.containsExactlyInAnyOrder(tuple("step1", ExitStatus.COMPLETED), tuple("step2", ExitStatus.COMPLETED), tuple("step3", ExitStatus.COMPLETED));
    	
    }

}
