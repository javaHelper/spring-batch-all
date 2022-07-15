package com.example.classifiercompositeitemwriterjob;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.classify.Classifier;

public class CustomerClassifier implements Classifier<Customer, ItemWriter<? super Customer>> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItemWriter<Customer> fileItemWriter;
    private ItemWriter<Customer> jdbcItemWriter;

    public CustomerClassifier(StaxEventItemWriter<Customer> fileItemWriter, JdbcBatchItemWriter<Customer> jdbcItemWriter) {
        this.fileItemWriter = fileItemWriter;
        this.jdbcItemWriter = jdbcItemWriter;
    }

    @Override
    public ItemWriter<Customer> classify(Customer customer) {
        if (customer.getState().matches("^[A-M].*")) {
            return fileItemWriter;
        } else {
            return jdbcItemWriter;
        }
    }
}