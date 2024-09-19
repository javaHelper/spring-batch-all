package com.example.demo;

import java.util.Collections;

import org.springframework.batch.core.listener.SkipListenerSupport;
import org.springframework.batch.item.file.FlatFileItemWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SkippedItemsListener extends SkipListenerSupport<Integer, Integer> {
	private FlatFileItemWriter<Integer> skippedItemsWriter;

	public SkippedItemsListener(FlatFileItemWriter<Integer> skippedItemsWriter) {
		this.skippedItemsWriter = skippedItemsWriter;
	}

	@Override
	public void onSkipInProcess(Integer item, Throwable t) {
		try {
			skippedItemsWriter.write(Collections.singletonList(item));
		} catch (Exception e) {
			log.error("Unable to write skipped item " + item);
		}
	}
}