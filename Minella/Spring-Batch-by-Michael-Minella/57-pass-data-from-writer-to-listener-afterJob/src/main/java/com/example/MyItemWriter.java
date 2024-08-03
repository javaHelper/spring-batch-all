package com.example;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MyItemWriter implements ItemWriter<Integer> {
    private StepExecution stepExecution;

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(List<? extends Integer> items) throws Exception {
        for (Integer item : items) {
            System.out.println("item = " + item);
        }

        stepExecution.getJobExecution().getExecutionContext().put("data", "fooo");
    }
}