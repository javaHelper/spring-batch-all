# Split


```
 .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 21:05:48.212  INFO 56322 --- [           main] com.example.demo.SplitApplication        : Starting SplitApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 56322 (/Users/prats/Documents/Prateek/Spring-Security-Oauth2-Tutorial-with-Keycloak/split/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/Spring-Security-Oauth2-Tutorial-with-Keycloak/split)
2023-08-13 21:05:48.219  INFO 56322 --- [           main] com.example.demo.SplitApplication        : No active profile set, falling back to 1 default profile: "default"
2023-08-13 21:05:49.212  INFO 56322 --- [           main] com.example.demo.SplitApplication        : Started SplitApplication in 1.366 seconds (JVM running for 2.292)
2023-08-13 21:05:49.216  INFO 56322 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-13 21:05:49.216  WARN 56322 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No datasource was provided...using a Map based JobRepository
2023-08-13 21:05:49.216  WARN 56322 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No transaction manager was provided, using a ResourcelessTransactionManager
2023-08-13 21:05:49.226  INFO 56322 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 21:05:49.260  INFO 56322 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=job]] launched with the following parameters: [{}]
2023-08-13 21:05:49.308  INFO 56322 --- [cTaskExecutor-2] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
2023-08-13 21:05:49.308  INFO 56322 --- [cTaskExecutor-1] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step2]
step1 has been executed on thread SimpleAsyncTaskExecutor-2
step2 has been executed on thread SimpleAsyncTaskExecutor-1
2023-08-13 21:05:49.366  INFO 56322 --- [cTaskExecutor-2] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 57ms
2023-08-13 21:05:49.366  INFO 56322 --- [cTaskExecutor-1] o.s.batch.core.step.AbstractStep         : Step: [step2] executed in 56ms
2023-08-13 21:05:49.377  INFO 56322 --- [cTaskExecutor-1] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step3]
step3 has been executed on thread SimpleAsyncTaskExecutor-1
2023-08-13 21:05:49.383  INFO 56322 --- [cTaskExecutor-1] o.s.batch.core.step.AbstractStep         : Step: [step3] executed in 6ms
2023-08-13 21:05:49.389  INFO 56322 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=job]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 115ms

```
