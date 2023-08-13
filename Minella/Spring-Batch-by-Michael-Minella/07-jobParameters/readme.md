# Job Parameters

````
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 22:24:09.367  INFO 64410 --- [           main] com.example.JobParametersApplication     : Starting JobParametersApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 64410 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/07-jobParameters/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/07-jobParameters)
2023-08-13 22:24:09.371  INFO 64410 --- [           main] com.example.JobParametersApplication     : No active profile set, falling back to 1 default profile: "default"
2023-08-13 22:24:11.252  INFO 64410 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-08-13 22:24:11.501  INFO 64410 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-08-13 22:24:11.733  INFO 64410 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: H2
2023-08-13 22:24:11.982  INFO 64410 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 22:24:12.277  INFO 64410 --- [           main] com.example.JobParametersApplication     : Started JobParametersApplication in 3.846 seconds (JVM running for 4.642)
2023-08-13 22:24:12.287  INFO 64410 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: [message=Aravind Dekate]
2023-08-13 22:24:12.358  INFO 64410 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=jobParametersJob]] launched with the following parameters: [{message=Aravind Dekate}]
2023-08-13 22:24:12.478  INFO 64410 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
Aravind Dekate
2023-08-13 22:24:12.580  INFO 64410 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 100ms
2023-08-13 22:24:12.587  INFO 64410 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=jobParametersJob]] completed with the following parameters: [{message=Aravind Dekate}] and the following status: [COMPLETED] in 145ms
2023-08-13 22:24:12.602  INFO 64410 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=jobParametersJob]] launched with the following parameters: [{message=MyHello, date=1691945652589, time=1691945652589}]
2023-08-13 22:24:12.612  INFO 64410 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
MyHello
2023-08-13 22:24:12.623  INFO 64410 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 11ms
2023-08-13 22:24:12.629  INFO 64410 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=jobParametersJob]] completed with the following parameters: [{message=MyHello, date=1691945652589, time=1691945652589}] and the following status: [COMPLETED] in 23ms
STATUS :: COMPLETED
2023-08-13 22:24:12.653  INFO 64410 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2023-08-13 22:24:12.664  INFO 64410 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

````