package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;

import java.time.LocalDate;

@Configuration
public class JobConfig {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public FlatFileItemReader<PersonDto> flatFileItemReader(){
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source);
            }
        });

        BeanWrapperFieldSetMapper<PersonDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setConversionService(service);
        fieldSetMapper.setTargetType(PersonDto.class);

        FlatFileItemReader<PersonDto> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("person.csv"));

        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("id","firstName","lastName","dob");
            }});
            setFieldSetMapper(fieldSetMapper);
        }});
        reader.setLinesToSkip(1);
        return reader;
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
