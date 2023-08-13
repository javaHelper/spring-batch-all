# custom date read from csv

````
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 23:16:30.412  INFO 71505 --- [           main] c.e.demo.CustomDateReadCsvApplication    : Starting CustomDateReadCsvApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 71505 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/14-custom-date-read-csv/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/14-custom-date-read-csv)
2023-08-13 23:16:30.416  INFO 71505 --- [           main] c.e.demo.CustomDateReadCsvApplication    : No active profile set, falling back to 1 default profile: "default"
2023-08-13 23:16:32.266  INFO 71505 --- [           main] c.e.demo.CustomDateReadCsvApplication    : Started CustomDateReadCsvApplication in 2.806 seconds (JVM running for 3.576)
2023-08-13 23:16:32.271  INFO 71505 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-13 23:16:32.272  WARN 71505 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No datasource was provided...using a Map based JobRepository
2023-08-13 23:16:32.272  WARN 71505 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No transaction manager was provided, using a ResourcelessTransactionManager
2023-08-13 23:16:32.289  INFO 71505 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 23:16:32.353  INFO 71505 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{}]
2023-08-13 23:16:32.401  INFO 71505 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
PersonDto(id=1, firstName=Neha, lastName=Parate, dob=1988-05-05)
PersonDto(id=2, firstName=Harshita, lastName=Dekate, dob=1981-05-05)
PersonDto(id=3, firstName=Prakash, lastName=Nimje, dob=1982-05-05)
PersonDto(id=4, firstName=Laxmi, lastName=Dekate, dob=1983-05-05)
PersonDto(id=5, firstName=Shweta, lastName=Dhapodkar, dob=1984-05-05)
2023-08-13 23:16:32.556  INFO 71505 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 155ms
2023-08-13 23:16:32.572  INFO 71505 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 192ms

Process finished with exit code 0

````