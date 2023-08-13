# Decision

````
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 21:14:41.881  INFO 57610 --- [           main] c.example.DecisionCheckmarksApplication  : Starting DecisionCheckmarksApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 57610 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/04-decisionCheckmarks/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/04-decisionCheckmarks)
2023-08-13 21:14:41.884  INFO 57610 --- [           main] c.example.DecisionCheckmarksApplication  : No active profile set, falling back to 1 default profile: "default"
2023-08-13 21:14:43.323  INFO 57610 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-08-13 21:14:43.519  INFO 57610 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-08-13 21:14:43.863  INFO 57610 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: H2
2023-08-13 21:14:43.989  INFO 57610 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 21:14:44.184  INFO 57610 --- [           main] c.example.DecisionCheckmarksApplication  : Started DecisionCheckmarksApplication in 3.067 seconds (JVM running for 3.753)
2023-08-13 21:14:44.192  INFO 57610 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-13 21:14:44.242  INFO 57610 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=job]] launched with the following parameters: [{}]
2023-08-13 21:14:44.331  INFO 57610 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [startStep]
This is the start Tasklet
2023-08-13 21:14:44.344  INFO 57610 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [startStep] executed in 12ms
2023-08-13 21:14:44.349  INFO 57610 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [oddStep]
This is the odd Tasklet
2023-08-13 21:14:44.356  INFO 57610 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [oddStep] executed in 7ms
2023-08-13 21:14:44.365  INFO 57610 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [evenStep]
This is the even Tasklet
2023-08-13 21:14:44.378  INFO 57610 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [evenStep] executed in 12ms
2023-08-13 21:14:44.383  INFO 57610 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=job]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 74ms
2023-08-13 21:14:44.405  INFO 57610 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2023-08-13 21:14:44.417  INFO 57610 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

Process finished with exit code 0

````