package com.example;

import org.springframework.batch.item.file.ResourceSuffixCreator;

public class EmployeeResourceSuffixCreator implements ResourceSuffixCreator {
    @Override
    public String getSuffix(int index) {
        return "-"+index;
    }
}