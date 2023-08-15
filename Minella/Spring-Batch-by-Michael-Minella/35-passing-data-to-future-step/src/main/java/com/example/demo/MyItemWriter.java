package com.example.demo;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MyItemWriter implements ItemWriter<Integer> {
    private StepExecution stepExecution;

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution){
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(List<? extends Integer> items) throws Exception {
        System.out.println("Items Writer called, Size is : "+items.size());
        for (Integer item: items) {
            System.out.println("item = " + item);
        }
        ExecutionContext executionContext = this.stepExecution.getExecutionContext();
        int count = executionContext.containsKey("count") ? executionContext.getInt("count") : 0;
        executionContext.put("count", count+items.size());
    }
}
