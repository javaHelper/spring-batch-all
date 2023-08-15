#FlatFileOutput

````
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-15 10:09:54.306  INFO 80904 --- [           main] com.example.FlatFileOutputApplication    : Starting FlatFileOutputApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 80904 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/18-flatFileOutput/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/18-flatFileOutput)
2023-08-15 10:09:54.309  INFO 80904 --- [           main] com.example.FlatFileOutputApplication    : No active profile set, falling back to 1 default profile: "default"
>> Output Path = /var/folders/kn/4wr9__651l37hckxvnnwt4hh0000gn/T/customerOutput258150453531332140.out
2023-08-15 10:09:56.296  INFO 80904 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-08-15 10:09:56.966  INFO 80904 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-08-15 10:09:57.537  INFO 80904 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: MYSQL
2023-08-15 10:09:57.572  INFO 80904 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-15 10:09:57.871  INFO 80904 --- [           main] com.example.FlatFileOutputApplication    : Started FlatFileOutputApplication in 4.549 seconds (JVM running for 5.377)
2023-08-15 10:09:57.977  INFO 80904 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{date=1692074397877, time=1692074397877}]
2023-08-15 10:09:58.019  INFO 80904 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
2023-08-15 10:09:58.111  INFO 80904 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 92ms
2023-08-15 10:09:58.127  INFO 80904 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{date=1692074397877, time=1692074397877}] and the following status: [COMPLETED] in 129ms
STATUS :: COMPLETED
2023-08-15 10:09:58.149  INFO 80904 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2023-08-15 10:09:58.177  INFO 80904 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
````