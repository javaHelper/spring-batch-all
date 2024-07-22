package com.example;

import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import lombok.Setter;


@Setter
public class EmployeeClassifier implements Classifier<Employee, ItemWriter<? super Employee>> {
    private static final long serialVersionUID = 1L;
    private ItemWriter<Employee> javaDeveloperFileItemWriter;
    private ItemWriter<Employee> pythonDeveloperFileItemWriter;
    private ItemWriter<Employee> cloudDeveloperFileItemWriter;
    
    public EmployeeClassifier(ItemWriter<Employee> javaDeveloperFileItemWriter,
                              ItemWriter<Employee> pythonDeveloperFileItemWriter,
                              ItemWriter<Employee> cloudDeveloperFileItemWriter) {
        this.javaDeveloperFileItemWriter = javaDeveloperFileItemWriter;
        this.pythonDeveloperFileItemWriter = pythonDeveloperFileItemWriter;
        this.cloudDeveloperFileItemWriter = cloudDeveloperFileItemWriter;
    }

    @Override
    public ItemWriter<? super Employee> classify(Employee employee) {
        if(employee.getRole().equals("Java Developer")){
            return javaDeveloperFileItemWriter;
        }
        else if(employee.getRole().equals("Python Developer")){
            return pythonDeveloperFileItemWriter;
        }
        return cloudDeveloperFileItemWriter;
    }
}