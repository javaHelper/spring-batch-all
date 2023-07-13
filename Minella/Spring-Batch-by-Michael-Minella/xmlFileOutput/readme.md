# XML File Output

This project reads from MySQL DB and write to XML file 

NOTE: Please build the project manually using `mvn clean install`

- Build docker compose file 

`docker-compose build`

- Run the docker compose 

`docker-compose up`

```
@Prateeks-MacBook-Pro xmlFileOutput % docker-compose build
[+] Building 1.3s (8/8) FINISHED                                                                                                                                     
 => [internal] load build definition from dockerfile                                                                                                            0.0s
 => => transferring dockerfile: 289B                                                                                                                            0.0s
 => [internal] load .dockerignore                                                                                                                               0.0s
 => => transferring context: 2B                                                                                                                                 0.0s
 => [internal] load metadata for docker.io/library/openjdk:11.0.6-jre-slim                                                                                      1.1s
 => [1/3] FROM docker.io/library/openjdk:11.0.6-jre-slim@sha256:d5ac902f3777234744268f634c3b32d8d8af57e11bb955ea631dd4421760ad32                                0.0s
 => [internal] load build context                                                                                                                               0.0s
 => => transferring context: 89B                                                                                                                                0.0s
 => CACHED [2/3] WORKDIR /usr/app                                                                                                                               0.0s
 => [3/3] COPY target/*.jar app.jar                                                                                                                             0.0s
 => exporting to image                                                                                                                                          0.1s
 => => exporting layers                                                                                                                                         0.0s
 => => writing image sha256:6b22c529008fbf25085ad8a3e3909c255cdc77ed3abf63435b73c0917f9aa814                                                                    0.0s
 => => naming to docker.io/library/xmlfileoutput                                                                                                                0.0s
@Prateeks-MacBook-Pro xmlFileOutput % 
@Prateeks-MacBook-Pro xmlFileOutput % docker-compose up            
[+] Running 3/3
 ⠿ Network xmlfileoutput_default    Created                                                                                                                     0.1s
 ⠿ Container xmlfileoutput-mysql-1  Created                                                                                                                     0.1s
 ⠿ Container xmlfileoutput          Created                                                                                                                     0.1s
Attaching to xmlfileoutput, xmlfileoutput-mysql-1
xmlfileoutput-mysql-1  | 2023-07-13 08:33:59+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 8.0.32-1.el8 started.
xmlfileoutput-mysql-1  | 2023-07-13 08:34:00+00:00 [Note] [Entrypoint]: Switching to dedicated user 'mysql'
xmlfileoutput-mysql-1  | 2023-07-13 08:34:00+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 8.0.32-1.el8 started.
xmlfileoutput-mysql-1  | '/var/lib/mysql/mysql.sock' -> '/var/run/mysqld/mysqld.sock'
xmlfileoutput-mysql-1  | 2023-07-13T08:34:00.823524Z 0 [Warning] [MY-011068] [Server] The syntax '--skip-host-cache' is deprecated and will be removed in a future release. Please use SET GLOBAL host_cache_size=0 instead.
xmlfileoutput-mysql-1  | 2023-07-13T08:34:00.825402Z 0 [System] [MY-010116] [Server] /usr/sbin/mysqld (mysqld 8.0.32) starting as process 1
xmlfileoutput-mysql-1  | 2023-07-13T08:34:00.832381Z 1 [System] [MY-013576] [InnoDB] InnoDB initialization has started.
xmlfileoutput          | 
xmlfileoutput          |   .   ____          _            __ _ _
xmlfileoutput          |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
xmlfileoutput          | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
xmlfileoutput          |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
xmlfileoutput          |   '  |____| .__|_| |_|_| |_\__, | / / / /
xmlfileoutput          |  =========|_|==============|___/=/_/_/_/
xmlfileoutput          |  :: Spring Boot ::                (v2.7.0)
xmlfileoutput          | 
xmlfileoutput          | 2023-07-13 08:34:01.111  INFO 1 --- [           main] com.example.XmlFileOutputApplication     : Starting XmlFileOutputApplication v0.0.1-SNAPSHOT using Java 11.0.6 on 521b62d7d92e with PID 1 (/usr/app/app.jar started by root in /usr/app)
xmlfileoutput          | 2023-07-13 08:34:01.113  INFO 1 --- [           main] com.example.XmlFileOutputApplication     : No active profile set, falling back to 1 default profile: "default"
xmlfileoutput-mysql-1  | 2023-07-13T08:34:01.114627Z 1 [System] [MY-013577] [InnoDB] InnoDB initialization has ended.
xmlfileoutput-mysql-1  | 2023-07-13T08:34:01.304542Z 0 [Warning] [MY-010068] [Server] CA certificate ca.pem is self signed.
xmlfileoutput-mysql-1  | 2023-07-13T08:34:01.304575Z 0 [System] [MY-013602] [Server] Channel mysql_main configured to support TLS. Encrypted connections are now supported for this channel.
xmlfileoutput-mysql-1  | 2023-07-13T08:34:01.306622Z 0 [Warning] [MY-011810] [Server] Insecure configuration for --pid-file: Location '/var/run/mysqld' in the path is accessible to all OS users. Consider choosing a different directory.
xmlfileoutput-mysql-1  | 2023-07-13T08:34:01.320418Z 0 [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.32'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.
xmlfileoutput-mysql-1  | 2023-07-13T08:34:01.320416Z 0 [System] [MY-011323] [Server] X Plugin ready for connections. Bind-address: '::' port: 33060, socket: /var/run/mysqld/mysqlx.sock
xmlfileoutput          | >> Output Path = /tmp/customerOutput1558201783545145811.out
xmlfileoutput          | 2023-07-13 08:34:01.777  INFO 1 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
xmlfileoutput          | 2023-07-13 08:34:02.051  INFO 1 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
xmlfileoutput          | 2023-07-13 08:34:02.430  INFO 1 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: MYSQL
xmlfileoutput          | 2023-07-13 08:34:02.601  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
xmlfileoutput          | 2023-07-13 08:34:02.674  INFO 1 --- [           main] com.example.XmlFileOutputApplication     : Started XmlFileOutputApplication in 2.08 seconds (JVM running for 2.425)
xmlfileoutput          | 2023-07-13 08:34:02.757  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{JobId=1689237242676, date=1689237242676, time=1689237242676}]
xmlfileoutput          | 2023-07-13 08:34:02.796  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
xmlfileoutput          | WARNING: An illegal reflective access operation has occurred
xmlfileoutput          | WARNING: Illegal reflective access by com.thoughtworks.xstream.core.util.Fields (jar:file:/usr/app/app.jar!/BOOT-INF/lib/xstream-1.4.7.jar!/) to field java.util.TreeMap.comparator
xmlfileoutput          | WARNING: Please consider reporting this to the maintainers of com.thoughtworks.xstream.core.util.Fields
xmlfileoutput          | WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
xmlfileoutput          | WARNING: All illegal access operations will be denied in a future release
xmlfileoutput          | 2023-07-13 08:34:02.944  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 148ms
xmlfileoutput          | 2023-07-13 08:34:02.958  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{JobId=1689237242676, date=1689237242676, time=1689237242676}] and the following status: [COMPLETED] in 179ms
xmlfileoutput          | STATUS :: COMPLETED
xmlfileoutput          | 2023-07-13 08:34:02.965  WARN 1 --- [ionShutdownHook] o.s.b.f.support.DisposableBeanAdapter    : Custom destroy method 'close' on bean with name 'customerItemWriter' threw an exception: java.lang.NullPointerException
xmlfileoutput          | 2023-07-13 08:34:02.967  INFO 1 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
xmlfileoutput          | 2023-07-13 08:34:03.004  INFO 1 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
xmlfileoutput exited with code 0
```