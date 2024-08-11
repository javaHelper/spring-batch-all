package com.example;

import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

@Setter
public class MyConversionService extends DefaultConversionService {
    private MyConverter myConverter;

    @Override
    public void addConverter(Converter<?, ?> converter) {
        super.addConverter(myConverter);
    }
}