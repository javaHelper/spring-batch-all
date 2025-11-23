package com.example.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * Interface to be implemented by any object that wishes to be notified of the ApplicationContext that it runs in.
 *  Implementing this interface makes sense for example when an object requires access to a set of collaborating beans. 
 *  Note that configuration via bean references is preferable to implementing this interface just for bean lookup purposes.
 */
@Component
public class JobConfiguration implements ApplicationContextAware{

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager manager;
	
	@Autowired
	private JobExplorer jobExplorer;

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobRegistry jobRegistry;
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Bean
	public JobRegistryBeanPostProcessor JobRegistrar() throws Exception {
		JobRegistryBeanPostProcessor beanPostProcessor = new JobRegistryBeanPostProcessor();
		beanPostProcessor.setJobRegistry(jobRegistry);
		beanPostProcessor.setBeanFactory(this.applicationContext.getAutowireCapableBeanFactory());
		beanPostProcessor.afterPropertiesSet();
		
		return beanPostProcessor;
	}
	
	@Bean
	public JobOperator jobOperator() throws Exception {
		SimpleJobOperator simpleJobOperator = new SimpleJobOperator();
		simpleJobOperator.setJobExplorer(jobExplorer);
		simpleJobOperator.setJobLauncher(jobLauncher);
		simpleJobOperator.setJobParametersConverter(new DefaultJobParametersConverter());
		simpleJobOperator.setJobRepository(jobRepository);
		simpleJobOperator.setJobRegistry(jobRegistry);
		simpleJobOperator.afterPropertiesSet();
		
		return simpleJobOperator;
	}
	
	@Bean
	@StepScope
	public Tasklet tasklet(@Value("#{jobParameters['name']}") String name) {
		System.out.println("NAME VALUE  = "+name);
		return (contribution, chunkContext) -> {
			System.out.println(String.format("The job run for %s", name));
			return RepeatStatus.FINISHED;
		};
	}

	@Bean
	public TaskletStep getStep(Tasklet tasklet) {
		return new StepBuilder("step1", jobRepository)
				.tasklet(tasklet, manager)
				.build();
	}
	
	@Bean
    public Job job(Tasklet tasklet) {
        return new JobBuilder("job", jobRepository)
                .start(getStep(tasklet))
                .build();
    }
}
