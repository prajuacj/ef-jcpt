package com.ef.jcpt.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(CloudConfigSupportProperties.CONFIG_PREFIX)
public class CloudConfigSupportProperties {

    public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public static final String CONFIG_PREFIX = "spring.cloud.config.obtain";

    private boolean enable = true;

    private String file = "backup.properties";

}