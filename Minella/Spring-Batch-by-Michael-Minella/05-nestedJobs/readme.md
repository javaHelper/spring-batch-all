# Nested Jobs 

```
 .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 21:21:15.453  INFO 59176 --- [           main] com.example.demo.Application             : Starting Application using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 59176 (/Users/prats/Documents/Prateek/Spring-Security-Oauth2-Tutorial-with-Keycloak/05-nestedJobs/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/Spring-Security-Oauth2-Tutorial-with-Keycloak/05-nestedJobs)
2023-08-13 21:21:15.459  INFO 59176 --- [           main] com.example.demo.Application             : No active profile set, falling back to 1 default profile: "default"
2023-08-13 21:21:16.405  INFO 59176 --- [           main] com.example.demo.Application             : Started Application in 1.322 seconds (JVM running for 2.025)
2023-08-13 21:21:16.411  INFO 59176 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-13 21:21:16.412  WARN 59176 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No datasource was provided...using a Map based JobRepository
2023-08-13 21:21:16.412  WARN 59176 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No transaction manager was provided, using a ResourcelessTransactionManager
2023-08-13 21:21:16.423  INFO 59176 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 21:21:16.457  INFO 59176 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=childJob]] launched with the following parameters: [{}]
2023-08-13 21:21:16.488  INFO 59176 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1a]
	>>This is step 1a
2023-08-13 21:21:16.513  INFO 59176 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1a] executed in 24ms
2023-08-13 21:21:16.529  INFO 59176 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=childJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 51ms
2023-08-13 21:21:16.533  INFO 59176 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=parentJob]] launched with the following parameters: [{}]
2023-08-13 21:21:16.537  INFO 59176 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
>> This is step 1
2023-08-13 21:21:16.543  INFO 59176 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 6ms
2023-08-13 21:21:16.547  INFO 59176 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [childJobStep]
2023-08-13 21:21:16.554  INFO 59176 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=childJob]] launched with the following parameters: [{}]
2023-08-13 21:21:16.555  INFO 59176 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Step already complete or not restartable, so no action to execute: StepExecution: id=1, version=3, name=step1a, status=COMPLETED, exitStatus=COMPLETED, readCount=0, filterCount=0, writeCount=0 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=1, rollbackCount=0, exitDescription=
2023-08-13 21:21:16.557  INFO 59176 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=childJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 2ms
2023-08-13 21:21:16.557  INFO 59176 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [childJobStep] executed in 10ms
2023-08-13 21:21:16.560  INFO 59176 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=parentJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 26ms

```