package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class JobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public StaxEventItemReader<ExamResult> staxEventItemReader(){
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(ExamResult.class);
		
		return new StaxEventItemReaderBuilder<ExamResult>()
				.name("readXml")
				.resource(new ClassPathResource("examResult.xml"))
				.addFragmentRootElements("ExamResult")
				.unmarshaller(jaxb2Marshaller)
				.build();
	}
	
	@Bean
	public FlatFileItemWriter<ExamResult> flatFileItemWriter(){
		BeanWrapperFieldExtractor<ExamResult> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] {"studentName", "percentage", "dob"});
		
		DelimitedLineAggregator<ExamResult> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter("|");
		lineAggregator.setFieldExtractor(fieldExtractor);
		
		return new FlatFileItemWriterBuilder<ExamResult>()
				.name("csvFlat")
				.resource(new FileSystemResource("file:csv/examResult.txt"))
				.lineAggregator(lineAggregator)
				.append(true)
				.build();
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<ExamResult, ExamResult>chunk(20)
				.reader(staxEventItemReader())
				.writer(flatFileItemWriter())
				.build();
	}
	
	@Bean
	public Job xmlToCsvJob() {
		return jobBuilderFactory.get("xmlToCsvJob")
				.start(step1())
				.build();
	}
}
