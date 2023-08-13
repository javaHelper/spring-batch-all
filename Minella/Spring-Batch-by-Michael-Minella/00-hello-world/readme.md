# Hello-world 

`docker-compose up --build`

App Logs

````
prateekashtikar@Prateeks-MacBook-Pro hello-world % docker-compose up --build
[+] Building 3.4s (16/16) FINISHED                                                                                                                                                
 => [internal] load build definition from Dockerfile                                                                                                                         0.0s
 => => transferring dockerfile: 314B                                                                                                                                         0.0s
 => [internal] load .dockerignore                                                                                                                                            0.0s
 => => transferring context: 2B                                                                                                                                              0.0s
 => [internal] load metadata for docker.io/library/openjdk:11.0.6-jre-slim                                                                                                   3.2s
 => [internal] load metadata for docker.io/library/maven:3.8.6-eclipse-temurin-17                                                                                            3.2s
 => [auth] library/openjdk:pull token for registry-1.docker.io                                                                                                               0.0s
 => [auth] library/maven:pull token for registry-1.docker.io                                                                                                                 0.0s
 => [builder 1/5] FROM docker.io/library/maven:3.8.6-eclipse-temurin-17@sha256:5092873778f0495464c1151df8f5c2e01a09ba37d931be719cbc1fc0f4559a07                              0.0s
 => [internal] load build context                                                                                                                                            0.0s
 => => transferring context: 1.24kB                                                                                                                                          0.0s
 => [stage-1 1/3] FROM docker.io/library/openjdk:11.0.6-jre-slim@sha256:d5ac902f3777234744268f634c3b32d8d8af57e11bb955ea631dd4421760ad32                                     0.0s
 => CACHED [stage-1 2/3] WORKDIR /usr/app                                                                                                                                    0.0s
 => CACHED [builder 2/5] WORKDIR build                                                                                                                                       0.0s
 => CACHED [builder 3/5] COPY src src                                                                                                                                        0.0s
 => CACHED [builder 4/5] COPY pom.xml pom.xml                                                                                                                                0.0s
 => CACHED [builder 5/5] RUN mvn clean package                                                                                                                               0.0s
 => CACHED [stage-1 3/3] COPY --from=BUILDER /build/target/*.jar app.jar                                                                                                     0.0s
 => exporting to image                                                                                                                                                       0.0s
 => => exporting layers                                                                                                                                                      0.0s
 => => writing image sha256:d669c5ae8365cbf2f627550e6580a76413b7d5697eb2d332743bec5043282209                                                                                 0.0s
 => => naming to docker.io/library/hello-world                                                                                                                               0.0s
[+] Running 1/1
 ⠿ Container hello-world-hello-world-1  Recreated                                                                                                                            0.1s
Attaching to hello-world-hello-world-1
hello-world-hello-world-1  | 
hello-world-hello-world-1  |   .   ____          _            __ _ _
hello-world-hello-world-1  |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
hello-world-hello-world-1  | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
hello-world-hello-world-1  |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
hello-world-hello-world-1  |   '  |____| .__|_| |_|_| |_\__, | / / / /
hello-world-hello-world-1  |  =========|_|==============|___/=/_/_/_/
hello-world-hello-world-1  |  :: Spring Boot ::                (v2.7.0)
hello-world-hello-world-1  | 
hello-world-hello-world-1  | 2023-07-12 18:43:32.104  INFO 1 --- [           main] com.example.HelloWorldApplication        : Starting HelloWorldApplication v0.0.1-SNAPSHOT using Java 11.0.6 on 690bfa33b0df with PID 1 (/usr/app/app.jar started by root in /usr/app)
hello-world-hello-world-1  | 2023-07-12 18:43:32.109  INFO 1 --- [           main] com.example.HelloWorldApplication        : No active profile set, falling back to 1 default profile: "default"
hello-world-hello-world-1  | 2023-07-12 18:43:32.871  INFO 1 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
hello-world-hello-world-1  | 2023-07-12 18:43:33.079  INFO 1 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
hello-world-hello-world-1  | 2023-07-12 18:43:33.213  INFO 1 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: H2
hello-world-hello-world-1  | 2023-07-12 18:43:33.363  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
hello-world-hello-world-1  | 2023-07-12 18:43:33.450  INFO 1 --- [           main] com.example.HelloWorldApplication        : Started HelloWorldApplication in 1.849 seconds (JVM running for 2.403)
hello-world-hello-world-1  | 2023-07-12 18:43:33.452  INFO 1 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
hello-world-hello-world-1  | 2023-07-12 18:43:33.502  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=helloWorldJob]] launched with the following parameters: [{}]
hello-world-hello-world-1  | 2023-07-12 18:43:33.530  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
hello-world-hello-world-1  | Hello World !
hello-world-hello-world-1  | 2023-07-12 18:43:33.542  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 12ms
hello-world-hello-world-1  | 2023-07-12 18:43:33.548  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=helloWorldJob]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 28ms
hello-world-hello-world-1  | 2023-07-12 18:43:33.558  INFO 1 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
hello-world-hello-world-1  | 2023-07-12 18:43:33.572  INFO 1 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
hello-world-hello-world-1 exited with code 0

````