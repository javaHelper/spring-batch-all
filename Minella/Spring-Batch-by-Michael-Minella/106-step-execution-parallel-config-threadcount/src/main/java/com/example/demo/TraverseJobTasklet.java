package com.example.demo;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class TraverseJobTasklet implements Tasklet {
    private String filePath;

    public TraverseJobTasklet(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // TODO call traversePath for filePath which is a sub-folder here
        System.out.println(Thread.currentThread().getName() + " processing sub-folder " + filePath);
        return RepeatStatus.FINISHED;
    }
}
