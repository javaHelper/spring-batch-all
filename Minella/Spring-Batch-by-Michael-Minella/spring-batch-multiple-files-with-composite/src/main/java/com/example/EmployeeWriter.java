package com.example;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class EmployeeWriter implements ItemWriter<Employee> {
    @Override
    public void write(List<? extends Employee> items) throws Exception {
        final FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<>();
        writer.setLineAggregator(new PassThroughLineAggregator<>());
        writer.setName("chunkFileItemWriter");

        writer.setResource(new FileSystemResource("emp-" + getTimestamp() + ".csv"));
        writer.open(new ExecutionContext());
        writer.write(items);
        writer.close();
    }

    private String getTimestamp() {
        // TODO tested on unix/linux systems, update as needed to not contain illegal characters for a file name on MS windows
        return LocalDateTime.now().toString();
    }
}