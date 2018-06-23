package com.ef.jcpt.user.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import com.ef.jcpt.common.log.LogTemplate;

@SpringBootApplication
@ImportResource(locations = { "classpath:context/applicationContext.xml" })
public class UserServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceApplication.class);
	private static volatile boolean running = true;

	public static void main(String[] args) {

		Class<?> clazz = UserServiceApplication.class;
		try {
			SpringApplication.run(clazz, args);
			logger.info(LogTemplate.genCommonSysOkLogStr("app:start",
					clazz.getName() + " APP Start ON SpringBoot Success"));
		} catch (Exception e) {
			logger.error(
					LogTemplate.genCommonSysOkLogStr("app:start", clazz.getName() + " APP Start ON SpringBoot Failed"),
					e);
		}
		synchronized (clazz) {
			while (running) {
				try {
					clazz.wait();
				} catch (Throwable e) {
					running = false;
					logger.error(LogTemplate.genCommonSysOkLogStr("app:start",
							clazz.getName() + " APP Running ON SpringBoot Failed"), e);
				}
			}
		}
	}
}
