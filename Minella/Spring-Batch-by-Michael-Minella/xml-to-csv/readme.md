# xml to csv

In this example, we're reading from XML file and creating csv file out of it

````sh
@Prateeks-MacBook-Pro xml-to-csv % docker ps 
CONTAINER ID   IMAGE          COMMAND                  CREATED          STATUS          PORTS                               NAMES
3dae6aeccec9   mysql:8.0.32   "docker-entrypoint.s…"   16 minutes ago   Up 16 minutes   0.0.0.0:3306->3306/tcp, 33060/tcp   xmlfileoutput-mysql-1
@Prateeks-MacBook-Pro xml-to-csv % 
````

````
@Prateeks-MacBook-Pro xml-to-csv % docker-compose up
[+] Running 0/1
 ⠿ xml-to-csv Warning                                                                                                                                                        5.6s
[+] Building 121.4s (16/16) FINISHED                                                                                                                                              
 => [internal] load build definition from Dockerfile                                                                                                                         0.0s
 => => transferring dockerfile: 311B                                                                                                                                         0.0s
 => [internal] load .dockerignore                                                                                                                                            0.0s
 => => transferring context: 2B                                                                                                                                              0.0s
 => [internal] load metadata for docker.io/library/openjdk:11.0.6-jre-slim                                                                                                   3.1s
 => [internal] load metadata for docker.io/library/maven:3.8.6-eclipse-temurin-17                                                                                            3.2s
 => [auth] library/maven:pull token for registry-1.docker.io                                                                                                                 0.0s
 => [auth] library/openjdk:pull token for registry-1.docker.io                                                                                                               0.0s
 => [stage-1 1/3] FROM docker.io/library/openjdk:11.0.6-jre-slim@sha256:d5ac902f3777234744268f634c3b32d8d8af57e11bb955ea631dd4421760ad32                                     0.0s
 => [maven_builder 1/5] FROM docker.io/library/maven:3.8.6-eclipse-temurin-17@sha256:5092873778f0495464c1151df8f5c2e01a09ba37d931be719cbc1fc0f4559a07                        0.0s
 => [internal] load build context                                                                                                                                            0.0s
 => => transferring context: 8.48kB                                                                                                                                          0.0s
 => CACHED [maven_builder 2/5] WORKDIR build                                                                                                                                 0.0s
 => [maven_builder 3/5] COPY src /build/src                                                                                                                                  0.0s
 => [maven_builder 4/5] COPY pom.xml pom.xml                                                                                                                                 0.0s
 => [maven_builder 5/5] RUN mvn clean package                                                                                                                              117.7s
 => CACHED [stage-1 2/3] WORKDIR /usr/app                                                                                                                                    0.0s
 => [stage-1 3/3] COPY --from=MAVEN_BUILDER /build/target/*.jar app.jar                                                                                                      0.0s
 => exporting to image                                                                                                                                                       0.1s
 => => exporting layers                                                                                                                                                      0.0s
 => => writing image sha256:f48c0015051c970736e95b70e6b4d63c122f71ce131383471264e68b550f4e65                                                                                 0.0s
 => => naming to docker.io/library/xml-to-csv                                                                                                                                0.0s
[+] Running 2/2
 ⠿ Network xml-to-csv_default  Created                                                                                                                                       0.1s
 ⠿ Container xml-to-csv        Created                                                                                                                                       0.1s
Attaching to xml-to-csv
xml-to-csv  | 
xml-to-csv  |   .   ____          _            __ _ _
xml-to-csv  |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
xml-to-csv  | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
xml-to-csv  |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
xml-to-csv  |   '  |____| .__|_| |_|_| |_\__, | / / / /
xml-to-csv  |  =========|_|==============|___/=/_/_/_/
xml-to-csv  |  :: Spring Boot ::                (v2.7.0)
xml-to-csv  | 
xml-to-csv  | 2023-07-13 08:48:14.074  INFO 1 --- [           main] com.example.XmlToCsvApplication          : Starting XmlToCsvApplication v0.0.1-SNAPSHOT using Java 11.0.6 on 35b7e888201a with PID 1 (/usr/app/app.jar started by root in /usr/app)
xml-to-csv  | 2023-07-13 08:48:14.075  INFO 1 --- [           main] com.example.XmlToCsvApplication          : No active profile set, falling back to 1 default profile: "default"
xml-to-csv  | 2023-07-13 08:48:14.653  INFO 1 --- [           main] com.example.XmlToCsvApplication          : Started XmlToCsvApplication in 0.867 seconds (JVM running for 1.226)
xml-to-csv  | 2023-07-13 08:48:14.654  INFO 1 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
xml-to-csv  | 2023-07-13 08:48:14.655  WARN 1 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No datasource was provided...using a Map based JobRepository
xml-to-csv  | 2023-07-13 08:48:14.655  WARN 1 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No transaction manager was provided, using a ResourcelessTransactionManager
xml-to-csv  | 2023-07-13 08:48:14.667  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
xml-to-csv  | 2023-07-13 08:48:14.701  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=xmlToCsvJob]] launched with the following parameters: [{}]
xml-to-csv  | 2023-07-13 08:48:14.722  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
xml-to-csv  | 2023-07-13 08:48:14.843  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 120ms
xml-to-csv  | 2023-07-13 08:48:14.850  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=xmlToCsvJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 131ms
xml-to-csv exited with code 0
@Prateeks-MacBook-Pro xml-to-csv % 
````