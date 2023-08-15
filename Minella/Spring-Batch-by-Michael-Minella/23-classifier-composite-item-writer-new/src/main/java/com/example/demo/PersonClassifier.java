package com.example.demo;

import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

public class PersonClassifier implements Classifier<Person, ItemWriter<? super Person>> {

    private final ItemWriter<Person> fooItemWriter;
    private final ItemWriter<Person> barItemWriter;

    public PersonClassifier(ItemWriter<Person> fooItemWriter, ItemWriter<Person> barItemWriter) {
        this.fooItemWriter = fooItemWriter;
        this.barItemWriter = barItemWriter;
    }

    @Override
    public ItemWriter<? super Person> classify(Person person) {
        if (person.getName().startsWith("foo")) {
            return fooItemWriter;
        } else {
            return barItemWriter;
        }
    }
}