# Reading XML

````
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-13 22:58:37.957  INFO 68989 --- [           main] com.example.ReadingXmlApplication        : Starting ReadingXmlApplication using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 68989 (/Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/11-readingXML/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/spring-batch-latest/Minella/Spring-Batch-by-Michael-Minella/11-readingXML)
2023-08-13 22:58:37.961  INFO 68989 --- [           main] com.example.ReadingXmlApplication        : No active profile set, falling back to 1 default profile: "default"
2023-08-13 22:58:39.229  INFO 68989 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2023-08-13 22:58:39.265  INFO 68989 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 22 ms. Found 0 JPA repository interfaces.
2023-08-13 22:58:39.975  INFO 68989 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-08-13 22:58:40.591  INFO 68989 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-08-13 22:58:40.991  INFO 68989 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2023-08-13 22:58:41.183  INFO 68989 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.6.9.Final
2023-08-13 22:58:41.438  INFO 68989 --- [           main] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
2023-08-13 22:58:41.594  INFO 68989 --- [           main] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.MySQL8Dialect
Hibernate: 
    
    drop table if exists customer
Hibernate: 
    
    create table customer (
       id bigint not null auto_increment,
        birthdate date,
        first_name varchar(255),
        last_name varchar(255),
        primary key (id)
    ) engine=InnoDB
2023-08-13 22:58:42.161  INFO 68989 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2023-08-13 22:58:42.169  INFO 68989 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2023-08-13 22:58:42.501  WARN 68989 --- [           main] o.s.b.a.batch.JpaBatchConfigurer         : JPA does not support custom isolation levels, so locks may not be taken when launching Jobs. To silence this warning, set 'spring.batch.jdbc.isolation-level-for-create' to 'default'.
2023-08-13 22:58:42.505  INFO 68989 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: MYSQL
2023-08-13 22:58:42.663  INFO 68989 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-13 22:58:42.885  INFO 68989 --- [           main] com.example.ReadingXmlApplication        : Started ReadingXmlApplication in 5.795 seconds (JVM running for 6.58)
2023-08-13 22:58:43.045  INFO 68989 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{date=1691947722892, JobId=1691947722893, time=1691947722893}]
2023-08-13 22:58:43.084  INFO 68989 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
2023-08-13 22:58:43.518  INFO 68989 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 432ms
2023-08-13 22:58:43.541  INFO 68989 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{date=1691947722892, JobId=1691947722893, time=1691947722893}] and the following status: [COMPLETED] in 470ms
STATUS :: COMPLETED
2023-08-13 22:58:43.569  INFO 68989 --- [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2023-08-13 22:58:43.576  INFO 68989 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2023-08-13 22:58:43.614  INFO 68989 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

Process finished with exit code 0

````