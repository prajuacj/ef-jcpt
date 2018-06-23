package com.ef.jcpt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import com.ef.jcpt.common.log.LogTemplate;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(ConfigServerApplication.class);

	public static void main(String[] args) {
		Class<?> clazz = ConfigServerApplication.class;
		try {
			SpringApplication.run(clazz, args);
			logger.info(LogTemplate.genCommonSysOkLogStr("app:start",
					clazz.getName() + " APP Start ON SpringBoot Success"));
		} catch (Exception e) {
			logger.error(
					LogTemplate.genCommonSysOkLogStr("app:start", clazz.getName() + " APP Start ON SpringBoot Failed"),
					e);
		}

	}
}
