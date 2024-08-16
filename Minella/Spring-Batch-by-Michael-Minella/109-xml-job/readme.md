# XML Job

This app reads xml file and write to console

`docker-compose build`

`docker-compose up`


--------

````
prateekashtikar@Prateeks-MacBook-Pro xml-job % docker-compose build
[+] Building 1.2s (8/8) FINISHED                                                                                                                                     
 => [internal] load build definition from Dockerfile                                                                                                            0.0s
 => => transferring dockerfile: 31B                                                                                                                             0.0s
 => [internal] load .dockerignore                                                                                                                               0.0s
 => => transferring context: 2B                                                                                                                                 0.0s
 => [internal] load metadata for docker.io/library/openjdk:11.0.6-jre-slim                                                                                      1.0s
 => [internal] load build context                                                                                                                               0.0s
 => => transferring context: 86B                                                                                                                                0.0s
 => [1/3] FROM docker.io/library/openjdk:11.0.6-jre-slim@sha256:d5ac902f3777234744268f634c3b32d8d8af57e11bb955ea631dd4421760ad32                                0.0s
 => CACHED [2/3] WORKDIR /usr/app                                                                                                                               0.0s
 => CACHED [3/3] COPY target/*.jar app.jar                                                                                                                      0.0s
 => exporting to image                                                                                                                                          0.0s
 => => exporting layers                                                                                                                                         0.0s
 => => writing image sha256:2eecb6a9f855a3bdbc61d59c40c181c5e04fb19484306e7ec47acf10c8406284                                                                    0.0s
 => => naming to docker.io/library/xml-job                                                                                                                      0.0s
prateekashtikar@Prateeks-MacBook-Pro xml-job % docker-compose up   
[+] Running 1/0
 ⠿ Container xml-job  Recreated                                                                                                                                 0.1s
Attaching to xml-job
xml-job  | 
xml-job  |   .   ____          _            __ _ _
xml-job  |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
xml-job  | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
xml-job  |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
xml-job  |   '  |____| .__|_| |_|_| |_\__, | / / / /
xml-job  |  =========|_|==============|___/=/_/_/_/
xml-job  |  :: Spring Boot ::                (v2.7.0)
xml-job  | 
xml-job  | 2023-07-13 09:02:09.178  INFO 1 --- [           main] com.example.XmlJobApplication            : Starting XmlJobApplication v0.0.1-SNAPSHOT using Java 11.0.6 on 3f8eee49eda7 with PID 1 (/usr/app/app.jar started by root in /usr/app)
xml-job  | 2023-07-13 09:02:09.180  INFO 1 --- [           main] com.example.XmlJobApplication            : No active profile set, falling back to 1 default profile: "default"
xml-job  | 2023-07-13 09:02:09.875  INFO 1 --- [           main] com.example.XmlJobApplication            : Started XmlJobApplication in 1.052 seconds (JVM running for 1.375)
xml-job  | 2023-07-13 09:02:09.876  INFO 1 --- [           main] o.s.b.a.b.JobLauncherApplicationRunner   : Running default command line with: [customerFile=/input/customer.xml]
xml-job  | 2023-07-13 09:02:09.878  WARN 1 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No datasource was provided...using a Map based JobRepository
xml-job  | 2023-07-13 09:02:09.878  WARN 1 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No transaction manager was provided, using a ResourcelessTransactionManager
xml-job  | 2023-07-13 09:02:09.887  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
xml-job  | 2023-07-13 09:02:09.916  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{customerFile=/input/customer.xml}]
xml-job  | 2023-07-13 09:02:09.939  INFO 1 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [copyFileStep]
xml-job  | Customer(id=null, firstName=Laura, middleInitial=O, lastName=Minella, address=2039 Wall Street, city=Omaha, state=IL, zipCode=35446, transactions=[Transaction(accountNumber=829433, transactionDate=2010-10-14T05:49:58, amount=26.08)])
xml-job  | Customer(id=null, firstName=Michael, middleInitial=T, lastName=Buffett, address=8192 Wall Street, city=Omaha, state=NE, zipCode=25372, transactions=[Transaction(accountNumber=8179238, transactionDate=2010-10-27T05:56:59, amount=-91.76), Transaction(accountNumber=8179238, transactionDate=2010-10-06T21:51:05, amount=-25.99)])
xml-job  | 2023-07-13 09:02:10.048  INFO 1 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [copyFileStep] executed in 109ms
xml-job  | 2023-07-13 09:02:10.055  INFO 1 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{customerFile=/input/customer.xml}] and the following status: [COMPLETED] in 121ms
xml-job exited with code 0
prateekashtikar@Prateeks-MacBook-Pro xml-job % 
````