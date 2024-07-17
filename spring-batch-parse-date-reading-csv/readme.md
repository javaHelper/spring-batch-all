#

# Apparoach -1

```xml
<bean id="flatFileItemReader" class="org.springframework.batch.item.file.FlatFileItemReader" scope="step">
        <property name="resource" value="classpath:employee.csv" />
        <property name="linesToSkip" value="1" />
        <property name="lineMapper">
            <bean class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
                <property name="fieldSetMapper">
                    <!-- Mapper which maps each individual items in a record to properties in POJO -->
                    <bean class="com.example.EmployeeFieldSetMapper" />
                </property>
                <property name="lineTokenizer">
                    <!-- A tokenizer class to be used when items in input record are separated by specific characters -->
                    <bean class="org.springframework.batch.item.file.transform.DelimitedLineTokenizer">
                        <property name="delimiter" value=","/>
                        <property name="names" value="empId,firstName,lastName,age,email,joiningDate" />
                    </bean>
                </property>
            </bean>
        </property>
    </bean>
```

FieldSetMapper 

```java

package com.example;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EmployeeFieldSetMapper implements FieldSetMapper<Employee> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM  d yyyy hh:mm:ss:SSSa", Locale.US);
    DateTimeFormatter NEW_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.US);

    @Override
    public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
        String joiningDate = fieldSet.readRawString("joiningDate");
        return Employee.builder()
                .empId(fieldSet.readLong("empId"))
                .firstName(fieldSet.readString("firstName"))
                .lastName(fieldSet.readString("lastName"))
                .age(fieldSet.readInt("age"))
                .email(fieldSet.readString("email"))
                .joiningDate(joiningDate != null ?  formatFDate(joiningDate) : null)
                .build();
    }

    private String formatFDate(String value){
        LocalDateTime dateTime = LocalDateTime.parse(value, formatter);
        return dateTime.format(NEW_FORMAT);
    }
}
```
