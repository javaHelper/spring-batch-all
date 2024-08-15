package com.example;

import java.util.List;

import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.core.annotation.OnWriteError;

public class StepExecutionListener {
	@OnSkipInRead
	public void onSkipInRead(Throwable t) {
		System.err.println("-- On Skip in Read Error : " + t.getMessage());
	}

	@OnSkipInWrite
	public void onSkipInWrite(Integer item, Throwable t) {
		System.err.println("-- Skipped in write due to : " + t.getMessage());
	}

	@OnSkipInProcess
	public void onSkipInProcess(Integer item, Throwable t) {
		System.err.println("-- Skipped in process due to: " + t.getMessage());
	}

	@OnWriteError
	public void onWriteError(Exception exception, List<? extends Integer> items) {
		System.err.println("-- Error on write on " + items + " : " + exception.getMessage());
	}
}