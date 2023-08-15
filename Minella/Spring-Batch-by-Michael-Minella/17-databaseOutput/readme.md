# database Output

````
 .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 23:36:31.640  INFO 74145 --- [           main] com.example.DatabaseOutputApplication    : Starting DatabaseOutputApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 74145 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/17-databaseOutput/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/17-databaseOutput)
2023-08-13 23:36:31.643  INFO 74145 --- [           main] com.example.DatabaseOutputApplication    : No active profile set, falling back to 1 default profile: "default"
2023-08-13 23:36:32.915  INFO 74145 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2023-08-13 23:36:32.951  INFO 74145 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 22 ms. Found 0 JPA repository interfaces.
2023-08-13 23:36:33.632  INFO 74145 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-08-13 23:36:34.202  INFO 74145 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-08-13 23:36:34.722  INFO 74145 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2023-08-13 23:36:34.935  INFO 74145 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.6.9.Final
2023-08-13 23:36:35.230  INFO 74145 --- [           main] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
2023-08-13 23:36:35.390  INFO 74145 --- [           main] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.MySQL8Dialect
2023-08-13 23:36:35.587  INFO 74145 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2023-08-13 23:36:35.598  INFO 74145 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2023-08-13 23:36:35.899  WARN 74145 --- [           main] o.s.b.a.batch.JpaBatchConfigurer         : JPA does not support custom isolation levels, so locks may not be taken when launching Jobs. To silence this warning, set 'spring.batch.jdbc.isolation-level-for-create' to 'default'.
2023-08-13 23:36:35.904  INFO 74145 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: MYSQL
2023-08-13 23:36:36.092  INFO 74145 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 23:36:36.345  INFO 74145 --- [           main] com.example.DatabaseOutputApplication    : Started DatabaseOutputApplication in 5.79 seconds (JVM running for 6.65)
2023-08-13 23:36:36.524  INFO 74145 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{date=1691949996350, JobId=1691949996351, time=1691949996351}]
2023-08-13 23:36:36.577  INFO 74145 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
2023-08-13 23:36:36.682  INFO 74145 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 105ms
2023-08-13 23:36:36.697  INFO 74145 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{date=1691949996350, JobId=1691949996351, time=1691949996351}] and the following status: [COMPLETED] in 148ms
STATUS :: COMPLETED
2023-08-13 23:36:36.720  INFO 74145 --- [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2023-08-13 23:36:36.730  INFO 74145 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2023-08-13 23:36:36.760  INFO 74145 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

````