#

```java
package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class ExistingServiceJobApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ExistingServiceJobListener existingServiceJobListener;

	@Bean
	public ItemReaderAdapter<Customer> customerItemReader(CustomerService customerService) {
		ItemReaderAdapter<Customer> adapter = new ItemReaderAdapter<>();
		adapter.setTargetObject(customerService);
		adapter.setTargetMethod("getCustomer");
		return adapter;
	}

	@Bean
	public ItemWriter<Customer> itemWriter() {
		return (items) -> items.forEach(System.out::println);
	}

	@Bean
	public Step copyFileStep() {
		return this.stepBuilderFactory.get("copyFileStep")
				.<Customer, Customer>chunk(10)
				.reader(customerItemReader(null))
				.writer(itemWriter())
				.build();
	}

	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("job")
				.start(copyFileStep())
				.listener(existingServiceJobListener)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ExistingServiceJobApplication.class, args);
	}
}
```


```java
package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;


@Component
public class CustomerService {
    private List<Customer> customers;
    private int curIndex;
    private String[] firstNames = {"Michael", "Warren", "Ann", "Terrence", "Erica", "Laura", "Steve", "Larry"};
    private String middleInitial = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String[] lastNames = {"Gates", "Darrow", "Donnelly", "Jobs", "Buffett", "Ellison", "Obama"};
    private String[] streets = {"4th Street", "Wall Street", "Fifth Avenue", "Mt. Lee Drive", "Jeopardy Lane",
            "Infinite Loop Drive", "Farnam Street", "Isabella Ave", "S. Greenwood Ave"};
    private String[] cities = {"Chicago", "New York", "Hollywood", "Aurora", "Omaha", "Atherton"};
    private String[] states = {"IL", "NY", "CA", "NE"};

    private Random generator = new Random();

    public CustomerService() {
        curIndex = 0;
        customers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            customers.add(buildCustomer());
        }
    }

    private Customer buildCustomer() {
        Customer customer = new Customer();
        customer.setId((long) generator.nextInt(Integer.MAX_VALUE));
        customer.setFirstName(firstNames[generator.nextInt(firstNames.length - 1)]);
        customer.setMiddleInitial(String.valueOf(middleInitial.charAt(generator.nextInt(middleInitial.length() - 1))));
        customer.setLastName(lastNames[generator.nextInt(lastNames.length - 1)]);
        customer.setAddress(generator.nextInt(9999) + " " + streets[generator.nextInt(streets.length - 1)]);
        customer.setCity(cities[generator.nextInt(cities.length - 1)]);
        customer.setState(states[generator.nextInt(states.length - 1)]);
        customer.setZipCode(String.valueOf(generator.nextInt(99999)));
        return customer;
    }

    public Customer getCustomer() {
        Customer cust = null;
        if (curIndex < customers.size()) {
            cust = customers.get(curIndex);
            curIndex++;
        }
        return cust;
    }
}
```