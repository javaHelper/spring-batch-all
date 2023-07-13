# transition

`docker-compose build`

`docker-compose up`

````
@Prateeks-MacBook-Pro transitions % docker-compose build         
[+] Building 3.0s (8/8) FINISHED                                                                                                                                                  
 => [internal] load build definition from Dockerfile                                                                                                                         0.0s
 => => transferring dockerfile: 32B                                                                                                                                          0.0s
 => [internal] load .dockerignore                                                                                                                                            0.0s
 => => transferring context: 2B                                                                                                                                              0.0s
 => [internal] load metadata for docker.io/library/openjdk:11.0.6-jre-slim                                                                                                   1.2s
 => [1/3] FROM docker.io/library/openjdk:11.0.6-jre-slim@sha256:d5ac902f3777234744268f634c3b32d8d8af57e11bb955ea631dd4421760ad32                                             0.0s
 => [internal] load build context                                                                                                                                            1.6s
 => => transferring context: 21.24MB                                                                                                                                         1.5s
 => CACHED [2/3] WORKDIR /usr/app                                                                                                                                            0.0s
 => [3/3] COPY target/*.jar app.jar                                                                                                                                          0.1s
 => exporting to image                                                                                                                                                       0.1s
 => => exporting layers                                                                                                                                                      0.1s
 => => writing image sha256:023cacd79a8b4eaa3ef94e02075676d2bdcafa7c478d7daffd0db7a57031c4ca                                                                                 0.0s
 => => naming to docker.io/library/transition                                                                                                                                0.0s
prateekashtikar@Prateeks-MacBook-Pro transitions % docker-compose up            
[+] Running 1/1
 ⠿ Container transition  Recreated                                                                                                                                           0.1s
Attaching to transition
transition  | 
transition  |   .   ____          _            __ _ _
transition  |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
transition  | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
transition  |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
transition  |   '  |____| .__|_| |_|_| |_\__, | / / / /
transition  |  =========|_|==============|___/=/_/_/_/
transition  |  :: Spring Boot ::                (v2.7.0)
transition  | 
transition  | 2023-07-13 10:21:18.519  INFO 1 --- [           main] com.example.TransitionsApplication       : Starting TransitionsApplication v0.0.1-SNAPSHOT using Java 11.0.6 on 49d0db808aa3 with PID 1 (/usr/app/app.jar started by root in /usr/app)
transition  | 2023-07-13 10:21:18.522  INFO 1 --- [           main] com.example.TransitionsApplication       : No active profile set, falling back to 1 default profile: "default"
transition  | 2023-07-13 10:21:19.069  INFO 1 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
transition  | 2023-07-13 10:21:19.234  INFO 1 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
transition  | 2023-07-13 10:21:19.316  INFO 1 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: H2
transition  | 2023-07-13 10:21:19.437  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
transition  | 2023-07-13 10:21:19.500  INFO 1 --- [           main] com.example.TransitionsApplication       : Started TransitionsApplication in 1.26 seconds (JVM running for 1.707)
transition  | 2023-07-13 10:21:19.501  INFO 1 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: []
transition  | 2023-07-13 10:21:19.540  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=transitionJobSimpleNext]] launched with the following parameters: [{}]
transition  | 2023-07-13 10:21:19.564  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
transition  | >> This is step-1
transition  | 2023-07-13 10:21:19.574  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 10ms
transition  | 2023-07-13 10:21:19.577  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step2]
transition  | >> This is step-2
transition  | 2023-07-13 10:21:19.581  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step2] executed in 3ms
transition  | 2023-07-13 10:21:19.584  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step3]
transition  | >> This is step-3
transition  | 2023-07-13 10:21:19.588  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step3] executed in 3ms
transition  | 2023-07-13 10:21:19.592  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=transitionJobSimpleNext]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 40ms
transition  | 2023-07-13 10:21:19.597  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=transitionJobSimpleNext]] launched with the following parameters: [{date=1689243679593, time=1689243679593}]
transition  | 2023-07-13 10:21:19.601  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
transition  | >> This is step-1
transition  | 2023-07-13 10:21:19.605  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 4ms
transition  | 2023-07-13 10:21:19.608  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step2]
transition  | >> This is step-2
transition  | 2023-07-13 10:21:19.611  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step2] executed in 2ms
transition  | 2023-07-13 10:21:19.614  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step3]
transition  | >> This is step-3
transition  | 2023-07-13 10:21:19.617  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step3] executed in 3ms
transition  | 2023-07-13 10:21:19.620  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=transitionJobSimpleNext]] completed with the following parameters: [{date=1689243679593, time=1689243679593}] and the following status: [COMPLETED] in 22ms
transition  | STATUS :: COMPLETED
transition  | 2023-07-13 10:21:19.628  INFO 1 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
transition  | 2023-07-13 10:21:19.636  INFO 1 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
transition exited with code 0
@Prateeks-MacBook-Pro transitions % 
````