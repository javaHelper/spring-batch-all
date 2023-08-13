#

````
.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::                (v2.7.0)

2023-08-13 20:48:20.866  INFO 49321 --- [           main] com.example.FlowApplication              : Starting FlowApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 49321 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/flow/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/flow)
2023-08-13 20:48:20.872  INFO 49321 --- [           main] com.example.FlowApplication              : No active profile set, falling back to 1 default profile: "default"
2023-08-13 20:48:22.955  INFO 49321 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-08-13 20:48:23.196  INFO 49321 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-08-13 20:48:23.518  INFO 49321 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: H2
2023-08-13 20:48:23.685  INFO 49321 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 20:48:23.960  INFO 49321 --- [           main] com.example.FlowApplication              : Started FlowApplication in 4.096 seconds (JVM running for 5.011)
2023-08-13 20:48:23.966  INFO 49321 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-13 20:48:24.022  INFO 49321 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=flowFirstJob]] launched with the following parameters: [{}]
2023-08-13 20:48:24.152  INFO 49321 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
Step 1 from inside flow too
2023-08-13 20:48:24.174  INFO 49321 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 21ms
2023-08-13 20:48:24.183  INFO 49321 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step2]
Step 2 from inside flow too
2023-08-13 20:48:24.194  INFO 49321 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step2] executed in 11ms
2023-08-13 20:48:24.197  INFO 49321 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [myStep]
myStep was executed
2023-08-13 20:48:24.209  INFO 49321 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [myStep] executed in 11ms
2023-08-13 20:48:24.219  INFO 49321 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=flowFirstJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 153ms
2023-08-13 20:48:24.237  INFO 49321 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=flowLastJob]] launched with the following parameters: [{}]
2023-08-13 20:48:24.249  INFO 49321 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
Step 1 from inside flow too
2023-08-13 20:48:24.255  INFO 49321 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 5ms
2023-08-13 20:48:24.265  INFO 49321 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step2]
Step 2 from inside flow too
2023-08-13 20:48:24.273  INFO 49321 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step2] executed in 8ms
2023-08-13 20:48:24.287  INFO 49321 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=flowLastJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 40ms
2023-08-13 20:48:24.313  INFO 49321 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2023-08-13 20:48:24.325  INFO 49321 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

Process finished with exit code 0
````