package com.example.demo.listeners;

import com.example.demo.Person;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;

public class ItemWriteLogListener implements ItemWriteListener<Person> {

	@Override
	public void beforeWrite(Chunk<? extends Person> items) {
		System.out.println(">>> ItemWriteLogListener.beforeWrite" + items);
	}

	@Override
	public void afterWrite(Chunk<? extends Person> items) {
		System.out.println(">>> ItemWriteLogListener.afterWrite" + items);
	}

	@Override
	public void onWriteError(Exception exception, Chunk<? extends Person> items) {
		System.out.println(">>> ItemWriteLogListener.onWriteError" + exception.getMessage());
	}
}