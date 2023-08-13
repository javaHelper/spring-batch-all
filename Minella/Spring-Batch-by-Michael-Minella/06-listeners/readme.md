# Listeners

```
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 21:39:46.144  INFO 61347 --- [           main] com.example.demo.Application             : Starting Application using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 61347 (/Users/prats/Documents/Prateek/Spring-Security-Oauth2-Tutorial-with-Keycloak/06-listeners/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/Spring-Security-Oauth2-Tutorial-with-Keycloak/06-listeners)
2023-08-13 21:39:46.153  INFO 61347 --- [           main] com.example.demo.Application             : No active profile set, falling back to 1 default profile: "default"
2023-08-13 21:39:47.140  INFO 61347 --- [           main] com.example.demo.Application             : Started Application in 1.322 seconds (JVM running for 2.046)
2023-08-13 21:39:47.143  INFO 61347 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-13 21:39:47.144  WARN 61347 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No datasource was provided...using a Map based JobRepository
2023-08-13 21:39:47.144  WARN 61347 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No transaction manager was provided, using a ResourcelessTransactionManager
2023-08-13 21:39:47.154  INFO 61347 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 21:39:47.188  INFO 61347 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=listenerJob]] launched with the following parameters: [{}]
JobListener | beforeJob | Email Sent starting...listenerJob
2023-08-13 21:39:47.229  INFO 61347 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
>> Before the chunk
Writing item one
Writing item two
<< After the chunk
>> Before the chunk
Writing item three
<< After the chunk
2023-08-13 21:39:47.260  INFO 61347 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 31ms
JobListener | beforeJob | Email Sent completed...listenerJob
2023-08-13 21:39:47.273  INFO 61347 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=listenerJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 70ms

```
