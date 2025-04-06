package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;

@Configuration
public class MyJobConfig {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager manager;
	
	@Bean
    public FlatFileItemReader<String> itemReader() {
        return new FlatFileItemReaderBuilder<String>()
                .name("flatFileReader")
                .resource(new FileSystemResource("foos.txt"))
                .lineMapper(new PassThroughLineMapper())
                .build();
    }
	
	@Bean
	public ItemWriter<String> itemWriter(){
		return new MyWriter();
	}
	
	// Alternate way
//	@Bean
//    public ItemWriter<String> itemWriter() {
//        final FlatFileItemWriter<String> writer = new FlatFileItemWriter<>();
//        writer.setLineAggregator(new PassThroughLineAggregator<>());
//        writer.setName("chunkFileItemWriter");
//        
//        return new ItemWriter<String>() {
//            @Override
//            public void write(List<? extends String> items) throws Exception {
//                writer.setResource(new FileSystemResource("foos-" + MyJobConfig.this.getTimestamp() + ".txt"));
//                writer.open(new ExecutionContext());
//                writer.write(items);
//                writer.close();
//            }
//        };
//    }

    private String getTimestamp() {
        // TODO tested on unix/linux systems, update as needed to not contain illegal characters for a file name on MS windows
        return LocalDateTime.now().toString();
    }

    @Bean
    public Step step() {
        return new StepBuilder("step", jobRepository)
                .<String, String>chunk(3, manager)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(step())
                .build();
    }
}