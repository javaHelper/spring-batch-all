package com.example.demo.config;

import com.example.demo.exception.CustomRetryableException;
import com.example.demo.processor.SkipItemProcessor;
import com.example.demo.writer.SkipItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JobConfiguration {

	@Autowired
	public JobRepository jobRepository;

	@Autowired
	public PlatformTransactionManager manager;

	@Bean
	@StepScope
	public ListItemReader<String> reader() {
		List<String> items = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			items.add(String.valueOf(i));
		}
		return new ListItemReader<>(items);
	}

	@Bean
	@StepScope
	public SkipItemProcessor processor(@Value("#{jobParameters['skip']}")String skip) {
		SkipItemProcessor processor = new SkipItemProcessor();

		processor.setSkip(StringUtils.hasText(skip) && skip.equalsIgnoreCase("processor"));

		return processor;
	}

	@Bean
	@StepScope
	public SkipItemWriter writer(@Value("#{jobParameters['skip']}")String skip) {
		SkipItemWriter writer = new SkipItemWriter();

		writer.setSkip(StringUtils.hasText(skip) && skip.equalsIgnoreCase("writer"));

		return writer;
	}

	@Bean
	public Step step1() {
		return new StepBuilder("step", jobRepository)
				.<String, String>chunk(10, manager)
				.reader(reader())
				.processor(processor(null))
				.writer(writer(null))
				.faultTolerant()
				.skip(CustomRetryableException.class)
				.skipLimit(15)
				.build();
	}

	@Bean
	public Job job() {
		return new JobBuilder("job", jobRepository)
				.start(step1())
				.build();
	}
}