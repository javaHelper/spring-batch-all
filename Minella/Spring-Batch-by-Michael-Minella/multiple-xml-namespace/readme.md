#

```java
package com.example;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class MyJobConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Bean
	public ItemReader<Person> itemReader() {
		return new ListItemReader<>(Arrays.asList(
				new Person(1, "foo"),
				new Person(2, "bar"))
				);
	}

	@Bean
	public ItemWriter<Person> itemWriter() {
		HashMap<String, String> rootElementAttributes = new HashMap<>();
		rootElementAttributes.put("xmlns:batch", "http://www.springframework.org/schema/batch");
		rootElementAttributes.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		rootElementAttributes.put("xsi:schemaLocation", "http://www.springframework.org/schema/batch");
		
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Person.class);

		return new StaxEventItemWriterBuilder<Person>()
				.name("personWriter")
				.resource(new FileSystemResource("persons.xml"))
				.marshaller(marshaller)
				.rootTagName("persons")
				.rootElementAttributes(rootElementAttributes)
				.build();
	}

	@Bean
	public Step step() {
		return steps.get("step")
				.<Person, Person>chunk(5)
				.reader(itemReader())
				.writer(itemWriter())
				.build();
	}

	@Bean
	public Job job() {
		return jobs.get("job")
				.start(step())
				.build();
	}
}

```