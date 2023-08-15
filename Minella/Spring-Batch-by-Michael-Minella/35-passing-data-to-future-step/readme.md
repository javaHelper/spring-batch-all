# passing data to future step

https://stackoverflow.com/questions/62184281/null-pointer-exception-on-passing-the-value-between-steps-in-spring-batch/62193058#62193058

````
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-15 16:17:04.919  INFO 96392 --- [           main] c.e.d.PassingDataToFutureStepApplication : Starting PassingDataToFutureStepApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 96392 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/35-passing-data-to-future-step/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/35-passing-data-to-future-step)
2023-08-15 16:17:04.923  INFO 96392 --- [           main] c.e.d.PassingDataToFutureStepApplication : No active profile set, falling back to 1 default profile: "default"
2023-08-15 16:17:06.914  INFO 96392 --- [           main] c.e.d.PassingDataToFutureStepApplication : Started PassingDataToFutureStepApplication in 2.998 seconds (JVM running for 3.852)
2023-08-15 16:17:06.920  INFO 96392 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-15 16:17:06.921  WARN 96392 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No datasource was provided...using a Map based JobRepository
2023-08-15 16:17:06.921  WARN 96392 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No transaction manager was provided, using a ResourcelessTransactionManager
2023-08-15 16:17:06.938  INFO 96392 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-15 16:17:07.002  INFO 96392 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{}]
2023-08-15 16:17:07.050  INFO 96392 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
Items Writer called, Size is : 2
item = 1
item = 2
Items Writer called, Size is : 2
item = 3
item = 4
2023-08-15 16:17:07.133  INFO 96392 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 83ms
2023-08-15 16:17:07.152  INFO 96392 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step2]
In step 2: step 1 wrote 4 items
2023-08-15 16:17:07.187  INFO 96392 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step2] executed in 35ms
2023-08-15 16:17:07.197  INFO 96392 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 168ms

Process finished with exit code 0

````