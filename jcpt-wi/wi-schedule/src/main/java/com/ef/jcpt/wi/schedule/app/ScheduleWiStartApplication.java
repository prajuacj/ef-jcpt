package com.ef.jcpt.wi.schedule.app;

import java.io.IOException;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.common.util.CloudPropertiesUtil;

@SpringBootApplication(scanBasePackages = { "com.ef.jcpt.wi.schedule" })
@ImportResource(locations = { "classpath:context/applicationContext.xml" })
public class ScheduleWiStartApplication {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleWiStartApplication.class);

	private static volatile boolean running = true;

	public static void main(String[] args) throws Exception {
		ApplicationContext context = null;
		Class<?> clazz = ScheduleWiStartApplication.class;
		try {
			context = SpringApplication.run(clazz, args);
			logger.info(LogTemplate.genCommonSysOkLogStr("app:start",
					clazz.getName() + " APP Start ON SpringBoot Success"));
		} catch (Exception e) {
			logger.error(
					LogTemplate.genCommonSysOkLogStr("app:start", clazz.getName() + " APP Start ON SpringBoot Failed"),
					e);
			System.exit(1);
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scheduler scheduler = (Scheduler) context.getBean("scheduler");
		String jobName = "com.ef.jcpt.wi.schedule.job.test.MyTestJob";
		String jobGroup = "job.test";
		String cronExpression = "0/10 * * * * ?";
		String jobDescription = "my test job";

		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

		CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
				.withMisfireHandlingInstructionDoNothing();
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(jobDescription)
				.withSchedule(schedBuilder).build();

		Class<? extends Job> jobclazz = (Class<? extends Job>) Class.forName(jobName);
		JobDetail jobDetail = JobBuilder.newJob(jobclazz).withIdentity(jobKey).withDescription(jobDescription).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

	@Bean(name = "scheduler")
	public Scheduler scheduler() throws IOException, SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory(quartzProperties());
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		return scheduler;
	}

	public Properties quartzProperties() throws IOException {
		String driver = CloudPropertiesUtil.getProperty("jdbc.driverClassName");
		String url = CloudPropertiesUtil.getProperty("schedule.jdbc.url");
		String user = CloudPropertiesUtil.getProperty("schedule.jdbc.username");
		String pwd = CloudPropertiesUtil.getProperty("schedule.jdbc.password");
		String threadCount = CloudPropertiesUtil.getProperty("quartz.threadPool.threadCount", "5");
		Properties prop = new Properties();
		prop.put("quartz.scheduler.instanceName", "ServerScheduler");
		prop.put("org.quartz.scheduler.instanceId", "AUTO");
		prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
		prop.put("org.quartz.scheduler.instanceId", "NON_CLUSTERED");
		prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
		prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
		prop.put("org.quartz.jobStore.tablePrefix", "NQRTZ_");
		prop.put("org.quartz.jobStore.isClustered", "true");
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		prop.put("org.quartz.threadPool.threadCount", threadCount);

		prop.put("org.quartz.dataSource.quartzDataSource.driver", driver);
		prop.put("org.quartz.dataSource.quartzDataSource.URL", url);
		prop.put("org.quartz.dataSource.quartzDataSource.user", user);
		prop.put("org.quartz.dataSource.quartzDataSource.password", pwd);
		prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", "10");
		prop.put("org.quartz.dataSource.quartzDataSource.validationQuery", "SELECT 1");
		prop.put("org.quartz.dataSource.quartzDataSource.discardIdleConnectionsSeconds", "180");
		return prop;
	}
}
