package com.example.demo.writer;

import com.example.demo.exception.CustomRetryableException;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class SkipItemWriter implements ItemWriter<String> {

	private boolean skip = false;
	private int attemptCount = 0;

	@Override
	public void write(Chunk<? extends String> items) throws Exception {
		for (String item : items) {
			System.out.println("writing item " + item);
			if(skip && item.equalsIgnoreCase("-84")) {
				attemptCount++;

				System.out.println("Writing of item " + item + " failed");
				throw new CustomRetryableException("Write failed.  Attempt:" + attemptCount);
			}
			else {
				System.out.println(item);
			}
		}
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
}