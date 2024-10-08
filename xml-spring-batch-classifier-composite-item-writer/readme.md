#

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:batch="http://www.springframework.org/schema/batch" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/batch http://www.springframework.org/schema/batch/spring-batch-3.0.xsd
  http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd">

    <bean id="transactionManager" class="org.springframework.batch.support.transaction.ResourcelessTransactionManager"/>

    <bean id="jobRepository" class="org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean"/>

    <bean id="jobLauncher" class="org.springframework.batch.core.launch.support.SimpleJobLauncher">
        <property name="jobRepository" ref="jobRepository"/>
    </bean>

    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/test"/>
        <property name="username" value="root"/>
        <property name="password" value="Password"/>
    </bean>


    <bean id="employeeItemReader" class="org.springframework.batch.item.database.JdbcCursorItemReader">
        <property name="dataSource" ref="dataSource"/>
        <property name="sql" value="SELECT * FROM test.customer"/>
        <property name="rowMapper">
            <bean class="com.example.mapper.CustomerRowMapper"/>
        </property>
    </bean>

    <!-- XML Writer -->
    <bean id="xmlItemWriter" class="org.springframework.batch.item.xml.StaxEventItemWriter">
        <property name="resource" value="file:xml/customer.xml"/>
        <property name="rootTagName" value="Customers"/>
        <property name="marshaller">
            <bean class="org.springframework.oxm.jaxb.Jaxb2Marshaller">
                <property name="classesToBeBound">
                    <list>
                        <value>com.example.model.Customer</value>
                    </list>
                </property>
            </bean>
        </property>
    </bean>

    <!-- CSV Writer  -->
    <bean id="csvItemWriter" class="org.springframework.batch.item.file.FlatFileItemWriter">
        <property name="resource" value="file:cvs/customer.csv"/>
        <property name="shouldDeleteIfExists" value="true"/>

        <property name="lineAggregator">
            <bean class="org.springframework.batch.item.file.transform.DelimitedLineAggregator">
                <property name="delimiter" value=","/>
                <property name="fieldExtractor">
                    <bean class="org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor">
                        <property name="names" value="id, firstName, lastName, birthdate"/>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>

    <bean id="customerClassifier" class="com.example.classifier.CustomerClassifier" >
        <constructor-arg index="0" ref="xmlItemWriter" />
        <constructor-arg index="1" ref="csvItemWriter" />
    </bean>

    <bean id="classifierCompositeItemWriter" class="org.springframework.batch.item.support.ClassifierCompositeItemWriter" >
        <property name="classifier" ref="customerClassifier" />
    </bean>

    <batch:job id="employeeJob">
        <batch:step id="step1">
            <batch:tasklet transaction-manager="transactionManager">
                <batch:chunk reader="employeeItemReader" writer="classifierCompositeItemWriter"
                             commit-interval="10">
                    <batch:streams>
                        <batch:stream ref="csvItemWriter" />
                        <batch:stream ref="xmlItemWriter" />
                    </batch:streams>
                </batch:chunk>
            </batch:tasklet>
        </batch:step>
    </batch:job>
</beans>
```
