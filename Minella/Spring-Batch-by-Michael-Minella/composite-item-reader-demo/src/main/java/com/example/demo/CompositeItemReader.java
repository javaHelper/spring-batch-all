package com.example.demo;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class CompositeItemReader<T> implements ItemStreamReader<T> {

	private final List<ItemStreamReader<T>> delegates;

	private final Iterator<ItemStreamReader<T>> delegatesIterator;

	private ItemStreamReader<T> currentDelegate;


	public CompositeItemReader(List<ItemStreamReader<T>> delegates) {
		this.delegates = delegates;
		this.delegatesIterator = this.delegates.iterator();
		this.currentDelegate = this.delegatesIterator.hasNext() ? this.delegatesIterator.next() : null;
	}


	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		for (ItemStreamReader<T> delegate : delegates) {
			delegate.open(executionContext);
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws ItemStreamException {
		for (ItemStreamReader<T> delegate : delegates) {
			delegate.close();
		}
	}

	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (this.currentDelegate == null) {
			return null;
		}
		T item = currentDelegate.read();
		if (item == null) {
			currentDelegate = this.delegatesIterator.hasNext() ? this.delegatesIterator.next() : null;
			return read();
		}
		return item;
	}

}
