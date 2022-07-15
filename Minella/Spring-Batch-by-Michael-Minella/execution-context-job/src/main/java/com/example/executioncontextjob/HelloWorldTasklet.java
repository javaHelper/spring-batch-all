package com.example.executioncontextjob;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

public class HelloWorldTasklet implements Tasklet {

    private static final String HELLO_WORLD = "Hello, %s";

    @Value("#{jobParameters['name']}")
    private String name;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        ExecutionContext jobExecutionContext = chunkContext.getStepContext()
                .getStepExecution()
//				.getJobExecution()
                .getExecutionContext();

        jobExecutionContext.put("user.name", name);
        System.out.println(String.format(HELLO_WORLD, name));
        return RepeatStatus.FINISHED;
    }
}