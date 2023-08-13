# flatFiles

````
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 22:44:17.809  INFO 67132 --- [           main] com.example.FlatfilesApplication         : Starting FlatfilesApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 67132 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/10-flatfiles/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/10-flatfiles)
2023-08-13 22:44:17.812  INFO 67132 --- [           main] com.example.FlatfilesApplication         : No active profile set, falling back to 1 default profile: "default"
2023-08-13 22:44:19.603  INFO 67132 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-08-13 22:44:19.849  INFO 67132 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-08-13 22:44:20.169  INFO 67132 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: H2
2023-08-13 22:44:20.323  INFO 67132 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 22:44:20.588  INFO 67132 --- [           main] com.example.FlatfilesApplication         : Started FlatfilesApplication in 3.753 seconds (JVM running for 4.569)
2023-08-13 22:44:20.597  INFO 67132 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
2023-08-13 22:44:20.650  INFO 67132 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{}]
2023-08-13 22:44:20.766  INFO 67132 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
Customer(id=1, firstName= John, lastName= Doe, birthdate=1952-10-10T10:10:10)
Customer(id=2, firstName= Amy, lastName= Eugene, birthdate=1985-07-05T17:10)
Customer(id=3, firstName= Laverne, lastName= Mann, birthdate=1988-12-11T10:10:10)
Customer(id=4, firstName= Janice, lastName= Preston, birthdate=1960-02-19T10:10:10)
Customer(id=5, firstName= Pauline, lastName= Rios, birthdate=1977-08-29T10:10:10)
Customer(id=6, firstName= Perry, lastName= Burnside, birthdate=1981-03-10T10:10:10)
Customer(id=7, firstName= Todd, lastName= Kinsey, birthdate=1998-12-14T10:10:10)
Customer(id=8, firstName= Jacqueline, lastName= Hyde, birthdate=1983-03-20T10:10:10)
Customer(id=9, firstName= Rico, lastName= Hale, birthdate=2000-10-10T10:10:10)
Customer(id=10, firstName= Samuel, lastName= Lamm, birthdate=1999-11-11T10:10:10)
Customer(id=11, firstName= Robert, lastName= Coster, birthdate=1972-10-10T10:10:10)
Customer(id=12, firstName= Tamara, lastName= Soler, birthdate=1978-01-02T10:10:10)
Customer(id=13, firstName= Justin, lastName= Kramer, birthdate=1951-11-19T10:10:10)
Customer(id=14, firstName= Andrea, lastName= Law, birthdate=1959-10-14T10:10:10)
Customer(id=15, firstName= Laura, lastName= Porter, birthdate=2010-12-12T10:10:10)
Customer(id=16, firstName= Michael, lastName= Cantu, birthdate=1999-04-11T10:10:10)
Customer(id=17, firstName= Andrew, lastName= Thomas, birthdate=1967-05-04T10:10:10)
Customer(id=18, firstName= Jose, lastName= Hannah, birthdate=1950-09-16T10:10:10)
Customer(id=19, firstName= Valerie, lastName= Hilbert, birthdate=1966-06-13T10:10:10)
Customer(id=20, firstName= Patrick, lastName= Durham, birthdate=1978-10-12T10:10:10)
2023-08-13 22:44:20.859  INFO 67132 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 93ms
2023-08-13 22:44:20.867  INFO 67132 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 131ms
2023-08-13 22:44:20.887  INFO 67132 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{date=1691946860875, time=1691946860875}]
2023-08-13 22:44:20.894  INFO 67132 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
Customer(id=1, firstName= John, lastName= Doe, birthdate=1952-10-10T10:10:10)
Customer(id=2, firstName= Amy, lastName= Eugene, birthdate=1985-07-05T17:10)
Customer(id=3, firstName= Laverne, lastName= Mann, birthdate=1988-12-11T10:10:10)
Customer(id=4, firstName= Janice, lastName= Preston, birthdate=1960-02-19T10:10:10)
Customer(id=5, firstName= Pauline, lastName= Rios, birthdate=1977-08-29T10:10:10)
Customer(id=6, firstName= Perry, lastName= Burnside, birthdate=1981-03-10T10:10:10)
Customer(id=7, firstName= Todd, lastName= Kinsey, birthdate=1998-12-14T10:10:10)
Customer(id=8, firstName= Jacqueline, lastName= Hyde, birthdate=1983-03-20T10:10:10)
Customer(id=9, firstName= Rico, lastName= Hale, birthdate=2000-10-10T10:10:10)
Customer(id=10, firstName= Samuel, lastName= Lamm, birthdate=1999-11-11T10:10:10)
Customer(id=11, firstName= Robert, lastName= Coster, birthdate=1972-10-10T10:10:10)
Customer(id=12, firstName= Tamara, lastName= Soler, birthdate=1978-01-02T10:10:10)
Customer(id=13, firstName= Justin, lastName= Kramer, birthdate=1951-11-19T10:10:10)
Customer(id=14, firstName= Andrea, lastName= Law, birthdate=1959-10-14T10:10:10)
Customer(id=15, firstName= Laura, lastName= Porter, birthdate=2010-12-12T10:10:10)
Customer(id=16, firstName= Michael, lastName= Cantu, birthdate=1999-04-11T10:10:10)
Customer(id=17, firstName= Andrew, lastName= Thomas, birthdate=1967-05-04T10:10:10)
Customer(id=18, firstName= Jose, lastName= Hannah, birthdate=1950-09-16T10:10:10)
Customer(id=19, firstName= Valerie, lastName= Hilbert, birthdate=1966-06-13T10:10:10)
Customer(id=20, firstName= Patrick, lastName= Durham, birthdate=1978-10-12T10:10:10)
2023-08-13 22:44:20.919  INFO 67132 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 25ms
2023-08-13 22:44:20.927  INFO 67132 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{date=1691946860875, time=1691946860875}] and the following status: [COMPLETED] in 39ms
STATUS :: COMPLETED
2023-08-13 22:44:20.948  INFO 67132 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2023-08-13 22:44:20.960  INFO 67132 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

Process finished with exit code 0

````