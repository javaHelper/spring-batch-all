package com.example.demo;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

public class MyItemProcessor implements ItemProcessor<Integer, Integer> {
    private StepExecution stepExecution;

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public Integer process(Integer item) throws Exception {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + ": processing item " + item + " as part of step " + stepExecution.getStepName());
        return item + 1;
    }
}
