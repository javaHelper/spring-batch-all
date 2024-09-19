package com.example.demo;

import org.springframework.batch.core.ItemProcessListener;

public class ItemProcessLogListener implements ItemProcessListener<Integer, Person> {

	@Override
	public void beforeProcess(Integer item) {
		System.out.println("### ItemProcessLogListener.beforeProcess " + item);
	}

	@Override
	public void afterProcess(Integer item, Person result) {
		System.out.println("### ItemProcessLogListener.afterProcess " + item + " |" + result);
	}

	@Override
	public void onProcessError(Integer item, Exception e) {
		System.out.println("### ItemProcessLogListener.onProcessError" + e.getMessage());
	}
}