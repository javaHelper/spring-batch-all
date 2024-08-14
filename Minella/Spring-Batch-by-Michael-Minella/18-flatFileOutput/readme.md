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



```
cat /var/folders/kn/4wr9__651l37hckxvnnwt4hh0000gn/T/customerOutput16372546261656706851.out
{"id":1,"firstName":"John","lastName":"Doe","birthdate":"10-10-1952 10:10:10"}
{"id":2,"firstName":"Amy","lastName":"Eugene","birthdate":"05-07-1985 17:10:00"}
{"id":3,"firstName":"Laverne","lastName":"Mann","birthdate":"11-12-1988 10:10:10"}
{"id":4,"firstName":"Janice","lastName":"Preston","birthdate":"19-02-1960 10:10:10"}
{"id":5,"firstName":"Pauline","lastName":"Rios","birthdate":"29-08-1977 10:10:10"}
{"id":6,"firstName":"Perry","lastName":"Burnside","birthdate":"10-03-1981 10:10:10"}
{"id":7,"firstName":"Todd","lastName":"Kinsey","birthdate":"14-12-1998 10:10:10"}
{"id":8,"firstName":"Jacqueline","lastName":"Hyde","birthdate":"20-03-1983 10:10:10"}
{"id":9,"firstName":"Rico","lastName":"Hale","birthdate":"10-10-2000 10:10:10"}
{"id":10,"firstName":"Samuel","lastName":"Lamm","birthdate":"11-11-1999 10:10:10"}
{"id":11,"firstName":"Robert","lastName":"Coster","birthdate":"10-10-1972 10:10:10"}
{"id":12,"firstName":"Tamara","lastName":"Soler","birthdate":"02-01-1978 10:10:10"}
{"id":13,"firstName":"Justin","lastName":"Kramer","birthdate":"19-11-1951 10:10:10"}
{"id":14,"firstName":"Andrea","lastName":"Law","birthdate":"14-10-1959 10:10:10"}
{"id":15,"firstName":"Laura","lastName":"Porter","birthdate":"12-12-2010 10:10:10"}
{"id":16,"firstName":"Michael","lastName":"Cantu","birthdate":"11-04-1999 10:10:10"}
{"id":17,"firstName":"Andrew","lastName":"Thomas","birthdate":"04-05-1967 10:10:10"}
{"id":18,"firstName":"Jose","lastName":"Hannah","birthdate":"16-09-1950 10:10:10"}
{"id":19,"firstName":"Valerie","lastName":"Hilbert","birthdate":"13-06-1966 10:10:10"}
{"id":20,"firstName":"Patrick","lastName":"Durham","birthdate":"12-10-1978 10:10:10"}
prateekashtikar@Prateeks-MacBook-Pro Spring-Batch-by-Michael-Minella % 
```
