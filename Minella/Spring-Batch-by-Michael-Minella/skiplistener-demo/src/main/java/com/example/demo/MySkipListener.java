package com.example.demo;

import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class MySkipListener implements SkipListener<Integer, Integer> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("@@@MySkipListener| On Skip in Read Error : " + t.getMessage());
    }

    @Override
    public void onSkipInWrite(Integer item, Throwable t) {
        System.out.println("@@@MySkipListener | Skipped in write due to : " + t.getMessage()+", Item ="+item);

    }

    @Override
    public void onSkipInProcess(Integer item, Throwable t) {
        System.out.println("@@@MySkipListener | Skipped in process due to: " + t.getMessage()+", Item ="+item);
        jdbcTemplate.update("INSERT INTO `test`.`mytest`(`name`) VALUES ('2')");
    }
}