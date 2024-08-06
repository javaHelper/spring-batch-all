package com.example;

import io.micrometer.core.lang.Nullable;
import lombok.Setter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.core.io.Resource;

@Setter
public class MyItemProcessor implements ItemProcessor<String, String> {

    private Resource[] resources;
    private StepExecution stepExecution;

    public MyItemProcessor(Resource[] resources){
        this.resources = resources;
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Nullable
    @Override
    public String process(String item) {
        ExecutionContext executionContext = stepExecution.getExecutionContext();
        int resourceIndex = executionContext.getInt("MultiResourceItemReader.resourceIndex");
        System.out.println("processing item = " + item + " coming from resource = " + resources[resourceIndex + 1]);
        return item;
    }
}