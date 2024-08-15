package com.example;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.core.io.FileSystemResource;

public class MyWriter implements ItemWriter<String>{
	
	final FlatFileItemWriter<String> writer = new FlatFileItemWriter<>();
    

	@Override
	public void write(List<? extends String> items) throws Exception {
		writer.setLineAggregator(new PassThroughLineAggregator<>());
	    writer.setName("chunkFileItemWriter");
	    
		writer.setResource(new FileSystemResource("foos-" + this.getTimestamp() + ".txt"));
        writer.open(new ExecutionContext());
        writer.write(items);
        writer.close();
		
	}
	
	private String getTimestamp() {
        // TODO tested on unix/linux systems, update as needed to not contain illegal characters for a file name on MS windows
        return LocalDateTime.now().toString();
    }

}
