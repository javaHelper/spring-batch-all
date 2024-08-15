package com.example.demo;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.cache.CacheManager;

public class MyTasklet implements Tasklet {
    private CacheManager cacheManager;

    public MyTasklet(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String name = (String) cacheManager.getCache("referenceData").get("foo").get();
        System.out.println("Hello, Cache Data is = " + name);
        return RepeatStatus.FINISHED;
    }
}
