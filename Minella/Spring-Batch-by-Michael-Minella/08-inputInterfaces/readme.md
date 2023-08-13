# inputInterfaces

```
 .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 22:34:03.356  INFO 65688 --- [           main] com.example.demo.Application             : Starting Application using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 65688 (/Users/prats/Documents/Prateek/Spring-Security-Oauth2-Tutorial-with-Keycloak/08-inputInterfaces/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/Spring-Security-Oauth2-Tutorial-with-Keycloak/08-inputInterfaces)
2023-08-13 22:34:03.360  INFO 65688 --- [           main] com.example.demo.Application             : No active profile set, falling back to 1 default profile: "default"
2023-08-13 22:34:04.573  INFO 65688 --- [           main] com.example.demo.Application             : Started Application in 1.656 seconds (JVM running for 2.799)
2023-08-13 22:34:04.576  INFO 65688 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-13 22:34:04.577  WARN 65688 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No datasource was provided...using a Map based JobRepository
2023-08-13 22:34:04.577  WARN 65688 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No transaction manager was provided, using a ResourcelessTransactionManager
2023-08-13 22:34:04.591  INFO 65688 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 22:34:04.627  INFO 65688 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=interfacesJob]] launched with the following parameters: [{}]
2023-08-13 22:34:04.664  INFO 65688 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
curItem = Foo
curItem = Bar
curItem = Baz
2023-08-13 22:34:04.698  INFO 65688 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 34ms
2023-08-13 22:34:04.713  INFO 65688 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=interfacesJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 70ms

```