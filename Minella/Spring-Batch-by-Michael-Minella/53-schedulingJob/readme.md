# Scheduling Job

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.0)

2023-08-15 18:21:49.153  INFO 6892 --- [           main] com.example.demo.Application             : Starting Application using Java 11.0.13 on Prateeks-MacBook-Pro.local with PID 6892 (/Users/prats/Documents/Prateek/Spring-Security-Oauth2-Tutorial-with-Keycloak/53-schedulingJob/target/classes started by prateekashtikar in /Users/prats/Documents/Prateek/Spring-Security-Oauth2-Tutorial-with-Keycloak/53-schedulingJob)
2023-08-15 18:21:49.158  INFO 6892 --- [           main] com.example.demo.Application             : No active profile set, falling back to 1 default profile: "default"
2023-08-15 18:21:50.178  INFO 6892 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration' of type [org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2023-08-15 18:21:50.212  INFO 6892 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'jobBuilders' of type [org.springframework.batch.core.configuration.annotation.JobBuilderFactory] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2023-08-15 18:21:50.214  INFO 6892 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'stepBuilders' of type [org.springframework.batch.core.configuration.annotation.StepBuilderFactory] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2023-08-15 18:21:50.221  INFO 6892 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'jobExplorer' of type [com.sun.proxy.$Proxy45] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2023-08-15 18:21:50.226  INFO 6892 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'jobRepository' of type [com.sun.proxy.$Proxy43] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2023-08-15 18:21:50.234  INFO 6892 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'jobRegistry' of type [com.sun.proxy.$Proxy47] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2023-08-15 18:21:50.251  INFO 6892 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'jobLauncher' of type [com.sun.proxy.$Proxy48] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2023-08-15 18:21:50.254  WARN 6892 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No datasource was provided...using a Map based JobRepository
2023-08-15 18:21:50.255  WARN 6892 --- [           main] o.s.b.c.c.a.DefaultBatchConfigurer       : No transaction manager was provided, using a ResourcelessTransactionManager
2023-08-15 18:21:50.270  INFO 6892 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
2023-08-15 18:21:50.271  INFO 6892 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'jobConfiguration' of type [com.example.demo.JobConfiguration$$EnhancerBySpringCGLIB$$12c4c3dd] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
2023-08-15 18:21:50.582  INFO 6892 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2023-08-15 18:21:50.591  INFO 6892 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2023-08-15 18:21:50.591  INFO 6892 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.63]
2023-08-15 18:21:50.700  INFO 6892 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2023-08-15 18:21:50.700  INFO 6892 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1474 ms
2023-08-15 18:21:51.399  INFO 6892 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-08-15 18:21:51.419  INFO 6892 --- [           main] com.example.demo.Application             : Started Application in 2.808 seconds (JVM running for 3.719)
2023-08-15 18:21:51.441  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Locating parameters for next instance of job with name=job
2023-08-15 18:21:51.459  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Attempting to launch job with name=job and parameters={run.id=1}
2023-08-15 18:21:51.537  INFO 6892 --- [cTaskExecutor-1] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{run.id=1}]
2023-08-15 18:21:51.575  INFO 6892 --- [cTaskExecutor-1] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
>> I was run at 06:21:51
2023-08-15 18:21:51.623  INFO 6892 --- [cTaskExecutor-1] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 47ms
2023-08-15 18:21:51.632  INFO 6892 --- [cTaskExecutor-1] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{run.id=1}] and the following status: [COMPLETED] in 74ms
2023-08-15 18:21:56.548  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Locating parameters for next instance of job with name=job
2023-08-15 18:21:56.570  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Attempting to launch job with name=job and parameters={run.id=2}
2023-08-15 18:21:56.573  INFO 6892 --- [cTaskExecutor-2] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{run.id=2}]
2023-08-15 18:21:56.577  INFO 6892 --- [cTaskExecutor-2] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
>> I was run at 06:21:56
2023-08-15 18:21:56.586  INFO 6892 --- [cTaskExecutor-2] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 8ms
2023-08-15 18:21:56.588  INFO 6892 --- [cTaskExecutor-2] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{run.id=2}] and the following status: [COMPLETED] in 13ms
2023-08-15 18:22:01.579  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Locating parameters for next instance of job with name=job
2023-08-15 18:22:01.588  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Attempting to launch job with name=job and parameters={run.id=3}
2023-08-15 18:22:01.593  INFO 6892 --- [cTaskExecutor-3] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{run.id=3}]
2023-08-15 18:22:01.597  INFO 6892 --- [cTaskExecutor-3] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
>> I was run at 06:22:01
2023-08-15 18:22:01.601  INFO 6892 --- [cTaskExecutor-3] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 4ms
2023-08-15 18:22:01.607  INFO 6892 --- [cTaskExecutor-3] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{run.id=3}] and the following status: [COMPLETED] in 12ms
2023-08-15 18:22:06.598  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Locating parameters for next instance of job with name=job
2023-08-15 18:22:06.607  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Attempting to launch job with name=job and parameters={run.id=4}
2023-08-15 18:22:06.613  INFO 6892 --- [cTaskExecutor-4] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{run.id=4}]
2023-08-15 18:22:06.619  INFO 6892 --- [cTaskExecutor-4] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
>> I was run at 06:22:06
2023-08-15 18:22:06.625  INFO 6892 --- [cTaskExecutor-4] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 5ms
2023-08-15 18:22:06.628  INFO 6892 --- [cTaskExecutor-4] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{run.id=4}] and the following status: [COMPLETED] in 13ms
2023-08-15 18:22:11.618  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Locating parameters for next instance of job with name=job
2023-08-15 18:22:11.627  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Attempting to launch job with name=job and parameters={run.id=5}
2023-08-15 18:22:11.631  INFO 6892 --- [cTaskExecutor-5] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{run.id=5}]
2023-08-15 18:22:11.635  INFO 6892 --- [cTaskExecutor-5] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
>> I was run at 06:22:11
2023-08-15 18:22:11.642  INFO 6892 --- [cTaskExecutor-5] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 7ms
2023-08-15 18:22:11.646  INFO 6892 --- [cTaskExecutor-5] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{run.id=5}] and the following status: [COMPLETED] in 12ms
2023-08-15 18:22:16.637  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Locating parameters for next instance of job with name=job
2023-08-15 18:22:16.645  INFO 6892 --- [   scheduling-1] o.s.b.c.l.support.SimpleJobOperator      : Attempting to launch job with name=job and parameters={run.id=6}
2023-08-15 18:22:16.650  INFO 6892 --- [cTaskExecutor-6] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] launched with the following parameters: [{run.id=6}]
2023-08-15 18:22:16.656  INFO 6892 --- [cTaskExecutor-6] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
>> I was run at 06:22:16
2023-08-15 18:22:16.667  INFO 6892 --- [cTaskExecutor-6] o.s.batch.core.step.AbstractStep         : Step: [step1] executed in 10ms
2023-08-15 18:22:16.675  INFO 6892 --- [cTaskExecutor-6] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job]] completed with the following parameters: [{run.id=6}] and the following status: [COMPLETED] in 21ms
2023-08-15 18:22:18.771  INFO 6892 --- [n(10)-127.0.0.1] inMXBeanRegistrar$SpringApplicationAdmin : Application shutdown requested.

```