package com.example.demo;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;

import java.util.Collections;

public class MyTasklet implements Tasklet {
    private boolean readerInitialized;
    private JdbcPagingItemReader<Person> itemReader;

    public MyTasklet(JdbcPagingItemReader<Person> itemReader) {
        this.itemReader = itemReader;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
        if (!readerInitialized) {
            itemReader.open(executionContext);
            readerInitialized = true;
        }

        Person person = itemReader.read();
        if (person == null) {
            itemReader.close();
            return RepeatStatus.FINISHED;
        }
        // process the item
        process(person);

        // write the item in its own file (dynamically generated at runtime)
        write(person, executionContext);

        // save current state in execution context: in case of restart after failure, the job would resume where it left off.
        itemReader.update(executionContext);

        return RepeatStatus.CONTINUABLE;
    }

    private void process(Person person) {
        System.out.println("Process : "+ person);
    }

    private void write(Person person, ExecutionContext executionContext) throws Exception {
        FlatFileItemWriter<Person> itemWriter = new FlatFileItemWriterBuilder<Person>()
                .resource(new FileSystemResource("person" + person.getId() + ".csv"))
                .name("personItemWriter")
                .delimited()
                .names("id", "name")
                .build();
        itemWriter.open(executionContext);
        itemWriter.write(Collections.singletonList(person));
        itemWriter.close();
    }
}
