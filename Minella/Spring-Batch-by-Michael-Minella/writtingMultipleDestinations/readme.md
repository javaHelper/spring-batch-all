# Writting to multiple destinations

This app reads from MySQL DB and writes to multiple files


````
@Prateeks-MacBook-Pro writtingMultipleDestinations % docker-compose build         
[+] Building 5.3s (9/9) FINISHED                                                                                                                                                  
 => [internal] load build definition from Dockerfile                                                                                                                         0.0s
 => => transferring dockerfile: 31B                                                                                                                                          0.0s
 => [internal] load .dockerignore                                                                                                                                            0.0s
 => => transferring context: 2B                                                                                                                                              0.0s
 => [internal] load metadata for docker.io/library/openjdk:11.0.6-jre-slim                                                                                                   3.5s
 => [auth] library/openjdk:pull token for registry-1.docker.io                                                                                                               0.0s
 => [1/3] FROM docker.io/library/openjdk:11.0.6-jre-slim@sha256:d5ac902f3777234744268f634c3b32d8d8af57e11bb955ea631dd4421760ad32                                             0.0s
 => [internal] load build context                                                                                                                                            1.5s
 => => transferring context: 21.88MB                                                                                                                                         1.5s
 => CACHED [2/3] WORKDIR /usr/app                                                                                                                                            0.0s
 => [3/3] COPY target/*.jar app.jar                                                                                                                                          0.1s
 => exporting to image                                                                                                                                                       0.1s
 => => exporting layers                                                                                                                                                      0.1s
 => => writing image sha256:c4c6d8f81f0c9985ed4819094dc709f0df3ba7c8c7ecd3e3e00a65c7b0a04037                                                                                 0.0s
 => => naming to docker.io/library/writting-multiple-destinations                                                                                                            0.0s
prateekashtikar@Prateeks-MacBook-Pro writtingMultipleDestinations % docker-compose up            
[+] Running 3/3
 ⠿ Network writtingmultipledestinations_default    Created                                                                                                                   0.1s
 ⠿ Container writtingmultipledestinations-mysql-1  Created                                                                                                                   0.1s
 ⠿ Container writting-multiple-destinations        Created                                                                                                                   0.1s
Attaching to writting-multiple-destinations, writtingmultipledestinations-mysql-1
writtingmultipledestinations-mysql-1  | 2023-07-13 10:06:55+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 8.0.32-1.el8 started.
writtingmultipledestinations-mysql-1  | 2023-07-13 10:06:56+00:00 [Note] [Entrypoint]: Switching to dedicated user 'mysql'
writtingmultipledestinations-mysql-1  | 2023-07-13 10:06:56+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 8.0.32-1.el8 started.
writtingmultipledestinations-mysql-1  | '/var/lib/mysql/mysql.sock' -> '/var/run/mysqld/mysqld.sock'
writtingmultipledestinations-mysql-1  | 2023-07-13T10:06:56.892146Z 0 [Warning] [MY-011068] [Server] The syntax '--skip-host-cache' is deprecated and will be removed in a future release. Please use SET GLOBAL host_cache_size=0 instead.
writtingmultipledestinations-mysql-1  | 2023-07-13T10:06:56.893847Z 0 [System] [MY-010116] [Server] /usr/sbin/mysqld (mysqld 8.0.32) starting as process 1
writtingmultipledestinations-mysql-1  | 2023-07-13T10:06:56.902123Z 1 [System] [MY-013576] [InnoDB] InnoDB initialization has started.
writtingmultipledestinations-mysql-1  | 2023-07-13T10:06:57.122084Z 1 [System] [MY-013577] [InnoDB] InnoDB initialization has ended.
writting-multiple-destinations        | 
writting-multiple-destinations        |   .   ____          _            __ _ _
writting-multiple-destinations        |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
writting-multiple-destinations        | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
writting-multiple-destinations        |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
writting-multiple-destinations        |   '  |____| .__|_| |_|_| |_\__, | / / / /
writting-multiple-destinations        |  =========|_|==============|___/=/_/_/_/
writting-multiple-destinations        |  :: Spring Boot ::                (v2.7.0)
writting-multiple-destinations        | 
writting-multiple-destinations        | 2023-07-13 10:06:57.246  INFO 1 --- [           main] .WrittingMultipleDestinationsApplication : Starting WrittingMultipleDestinationsApplication v0.0.1-SNAPSHOT using Java 11.0.6 on c1a9729f37f7 with PID 1 (/usr/app/app.jar started by root in /usr/app)
writting-multiple-destinations        | 2023-07-13 10:06:57.248  INFO 1 --- [           main] .WrittingMultipleDestinationsApplication : No active profile set, falling back to 1 default profile: "default"
writtingmultipledestinations-mysql-1  | 2023-07-13T10:06:57.317001Z 0 [Warning] [MY-010068] [Server] CA certificate ca.pem is self signed.
writtingmultipledestinations-mysql-1  | 2023-07-13T10:06:57.317042Z 0 [System] [MY-013602] [Server] Channel mysql_main configured to support TLS. Encrypted connections are now supported for this channel.
writtingmultipledestinations-mysql-1  | 2023-07-13T10:06:57.318760Z 0 [Warning] [MY-011810] [Server] Insecure configuration for --pid-file: Location '/var/run/mysqld' in the path is accessible to all OS users. Consider choosing a different directory.
writtingmultipledestinations-mysql-1  | 2023-07-13T10:06:57.332131Z 0 [System] [MY-011323] [Server] X Plugin ready for connections. Bind-address: '::' port: 33060, socket: /var/run/mysqld/mysqlx.sock
writtingmultipledestinations-mysql-1  | 2023-07-13T10:06:57.332255Z 0 [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.32'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.
writting-multiple-destinations        | >> Output Path = /tmp/customerOutput5270904722452836507.out
writting-multiple-destinations        | >> Output Path = /tmp/customerOutput7015311435047311672.out
writting-multiple-destinations        | 2023-07-13 10:06:58.003  INFO 1 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
writting-multiple-destinations        | 2023-07-13 10:06:58.326  INFO 1 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
writting-multiple-destinations        | 2023-07-13 10:06:58.699  INFO 1 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: MYSQL
writting-multiple-destinations        | 2023-07-13 10:06:58.748  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
writting-multiple-destinations        | 2023-07-13 10:06:58.818  INFO 1 --- [           main] .WrittingMultipleDestinationsApplication : Started WrittingMultipleDestinationsApplication in 2.128 seconds (JVM running for 2.471)
writting-multiple-destinations        | 2023-07-13 10:06:58.898  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{JobId=1689242818819, date=1689242818819, time=1689242818819}]
writting-multiple-destinations        | 2023-07-13 10:06:58.938  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
writting-multiple-destinations        | WARNING: An illegal reflective access operation has occurred
writting-multiple-destinations        | WARNING: Illegal reflective access by com.thoughtworks.xstream.core.util.Fields (jar:file:/usr/app/app.jar!/BOOT-INF/lib/xstream-1.4.7.jar!/) to field java.util.TreeMap.comparator
writting-multiple-destinations        | WARNING: Please consider reporting this to the maintainers of com.thoughtworks.xstream.core.util.Fields
writting-multiple-destinations        | WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
writting-multiple-destinations        | WARNING: All illegal access operations will be denied in a future release
writting-multiple-destinations        | 2023-07-13 10:06:59.125  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 187ms
writting-multiple-destinations        | 2023-07-13 10:06:59.139  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{JobId=1689242818819, date=1689242818819, time=1689242818819}] and the following status: [COMPLETED] in 218ms
writting-multiple-destinations        | STATUS :: COMPLETED
writting-multiple-destinations        | 2023-07-13 10:06:59.147  WARN 1 --- [ionShutdownHook] o.s.b.f.support.DisposableBeanAdapter    : Custom destroy method 'close' on bean with name 'itemWriter' threw an exception: java.lang.NullPointerException
writting-multiple-destinations        | 2023-07-13 10:06:59.148  WARN 1 --- [ionShutdownHook] o.s.b.f.support.DisposableBeanAdapter    : Custom destroy method 'close' on bean with name 'xmlItemWriter' threw an exception: java.lang.NullPointerException
writting-multiple-destinations        | 2023-07-13 10:06:59.150  INFO 1 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
writting-multiple-destinations        | 2023-07-13 10:06:59.187  INFO 1 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
writting-multiple-destinations exited with code 0

````

````
prateekashtikar@Prateeks-MacBook-Pro xmlFileOutput % docker ps 
CONTAINER ID   IMAGE          COMMAND                  CREATED         STATUS         PORTS                               NAMES
ce514b3fe6a5   mysql:8.0.32   "docker-entrypoint.s…"   2 minutes ago   Up 2 minutes   0.0.0.0:3306->3306/tcp, 33060/tcp   writtingmultipledestinations-mysql-1
prateekashtikar@Prateeks-MacBook-Pro xmlFileOutput % docker exec -it ce514b3fe6a5 bash
bash-4.4# mysql -u root -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 25
Server version: 8.0.32 MySQL Community Server - GPL

Copyright (c) 2000, 2023, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> use test;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> show tables;
+------------------------------+
| Tables_in_test               |
+------------------------------+
| BATCH_JOB_EXECUTION          |
| BATCH_JOB_EXECUTION_CONTEXT  |
| BATCH_JOB_EXECUTION_PARAMS   |
| BATCH_JOB_EXECUTION_SEQ      |
| BATCH_JOB_INSTANCE           |
| BATCH_JOB_SEQ                |
| BATCH_STEP_EXECUTION         |
| BATCH_STEP_EXECUTION_CONTEXT |
| BATCH_STEP_EXECUTION_SEQ     |
| customer                     |
+------------------------------+
10 rows in set (0.01 sec)

mysql> select * from customer;
+----+------------+----------+---------------------+
| id | firstName  | lastName | birthdate           |
+----+------------+----------+---------------------+
|  1 | John       | Doe      | 10-10-1952 10:10:10 |
|  2 | Amy        | Eugene   | 05-07-1985 17:10:00 |
|  3 | Laverne    | Mann     | 11-12-1988 10:10:10 |
|  4 | Janice     | Preston  | 19-02-1960 10:10:10 |
|  5 | Pauline    | Rios     | 29-08-1977 10:10:10 |
|  6 | Perry      | Burnside | 10-03-1981 10:10:10 |
|  7 | Todd       | Kinsey   | 14-12-1998 10:10:10 |
|  8 | Jacqueline | Hyde     | 20-03-1983 10:10:10 |
|  9 | Rico       | Hale     | 10-10-2000 10:10:10 |
| 10 | Samuel     | Lamm     | 11-11-1999 10:10:10 |
| 11 | Robert     | Coster   | 10-10-1972 10:10:10 |
| 12 | Tamara     | Soler    | 02-01-1978 10:10:10 |
| 13 | Justin     | Kramer   | 19-11-1951 10:10:10 |
| 14 | Andrea     | Law      | 14-10-1959 10:10:10 |
| 15 | Laura      | Porter   | 12-12-2010 10:10:10 |
| 16 | Michael    | Cantu    | 11-04-1999 10:10:10 |
| 17 | Andrew     | Thomas   | 04-05-1967 10:10:10 |
| 18 | Jose       | Hannah   | 16-09-1950 10:10:10 |
| 19 | Valerie    | Hilbert  | 13-06-1966 10:10:10 |
| 20 | Patrick    | Durham   | 12-10-1978 10:10:10 |
+----+------------+----------+---------------------+
20 rows in set (0.00 sec)

mysql> 
````