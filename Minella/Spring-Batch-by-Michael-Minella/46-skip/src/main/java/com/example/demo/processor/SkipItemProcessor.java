package com.example.demo.processor;

import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;

import com.example.demo.exception.CustomRetryableException;

public class SkipItemProcessor implements ItemProcessor<String, String> {

	@Setter
    private boolean skip = false;
	private int attemptCount = 0;

	@Override
	public String process(String item) throws Exception {
		System.out.println("processing item " + item);
		if(skip && item.equalsIgnoreCase("42")) {
			attemptCount++;

			System.out.println("Processing of item " + item + " failed");
			throw new CustomRetryableException("Process failed.  Attempt:" + attemptCount);
		}
		else {
			return String.valueOf(Integer.parseInt(item) * -1);
		}
	}
}