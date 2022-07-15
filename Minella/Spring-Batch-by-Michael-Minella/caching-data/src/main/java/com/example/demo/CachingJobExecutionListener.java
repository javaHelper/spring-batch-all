package com.example.demo;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.cache.CacheManager;

public class CachingJobExecutionListener implements JobExecutionListener {
    private CacheManager cacheManager;

    public CachingJobExecutionListener(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // populate cache as needed. Can use a jdbcTemplate to query the db here and populate the cache
        cacheManager.getCache("referenceData").put("foo", "bar");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // clear cache when the job is finished
        cacheManager.getCache("referenceData").clear();
    }
}
