# item Stream

Run this program twice


```java
package com.example.config;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * Convenience interface that combines ItemStream and ItemReader .
 */

public class StatefullItemReader implements ItemStreamReader<String> {

    private final List<String> items;
    private int curIndex = -1;
    private boolean restart = false;

    public StatefullItemReader(List<String> items) {
        this.items = items;
        this.curIndex = 0;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey("curIndex")) {
            this.curIndex = executionContext.getInt("curIndex");
            this.restart = true;
        } else {
            this.curIndex = 0;
            executionContext.put("curIndex", this.curIndex);
        }

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("curIndex", this.curIndex);
    }

    @Override
    public void close() throws ItemStreamException {

    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String item = null;

        if (this.curIndex < this.items.size()) {
            item = this.items.get(this.curIndex);
            this.curIndex++;
        }

        if (this.curIndex == 42 && !restart) {
            throw new RuntimeException("The Answer to the Ultimate Question of Life, the universe & everything...");
        }

        return item;
    }

}
```


````
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 23:08:34.213  INFO 70473 --- [           main] com.example.ItemStreamApplication        : Starting ItemStreamApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 70473 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/13-itemStream/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/13-itemStream)
2023-08-13 23:08:34.216  INFO 70473 --- [           main] com.example.ItemStreamApplication        : No active profile set, falling back to 1 default profile: "default"
2023-08-13 23:08:35.911  INFO 70473 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-08-13 23:08:36.520  INFO 70473 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-08-13 23:08:37.047  INFO 70473 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: MYSQL
2023-08-13 23:08:37.296  INFO 70473 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 23:08:37.556  INFO 70473 --- [           main] com.example.ItemStreamApplication        : Started ItemStreamApplication in 4.14 seconds (JVM running for 4.879)
2023-08-13 23:08:37.561  INFO 70473 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-13 23:08:37.730  INFO 70473 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=statefulJob]] launched with the following parameters: [{}]
2023-08-13 23:08:37.797  INFO 70473 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
>> 41
>> 42
>> 43
>> 44
>> 45
>> 46
>> 47
>> 48
>> 49
>> 50
>> 51
>> 52
>> 53
>> 54
>> 55
>> 56
>> 57
>> 58
>> 59
>> 60
>> 61
>> 62
>> 63
>> 64
>> 65
>> 66
>> 67
>> 68
>> 69
>> 70
>> 71
>> 72
>> 73
>> 74
>> 75
>> 76
>> 77
>> 78
>> 79
>> 80
>> 81
>> 82
>> 83
>> 84
>> 85
>> 86
>> 87
>> 88
>> 89
>> 90
>> 91
>> 92
>> 93
>> 94
>> 95
>> 96
>> 97
>> 98
>> 99
>> 100
2023-08-13 23:08:37.874  INFO 70473 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 77ms
2023-08-13 23:08:37.892  INFO 70473 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=statefulJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 135ms
2023-08-13 23:08:37.915  INFO 70473 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2023-08-13 23:08:37.950  INFO 70473 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

Process finished with exit code 0

````