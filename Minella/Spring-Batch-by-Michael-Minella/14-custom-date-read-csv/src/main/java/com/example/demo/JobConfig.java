package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class JobConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Bean
	public FlatFileItemReader<PersonDto> flatFileItemReader(){	
		return new FlatFileItemReaderBuilder<PersonDto>()
				.name("person")
				.delimited()
				.names(new String[]{"id","firstName","lastName","dob"})
				.linesToSkip(1)
				.resource(new ClassPathResource("person.csv"))
				.fieldSetMapper(new PersonDtoFieldSetMapper())
				.build();
	}

	@Bean
	public ItemWriter<PersonDto> itemWriter(){
		return items -> {
			items.forEach(System.out::println);
		};
	}

	@Bean
	public Step step1(){
		return stepBuilderFactory.get("step1")
				.<PersonDto, PersonDto> chunk(2)
				.reader(flatFileItemReader())
				.writer(itemWriter())
				.build();
	}

	@Bean
	public Job job(){
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}


}
