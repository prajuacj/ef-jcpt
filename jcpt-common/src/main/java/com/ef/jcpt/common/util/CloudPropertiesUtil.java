package com.ef.jcpt.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.bind.PropertySourcesPropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableConfigurationProperties(CloudConfigSupportProperties.class)
public class CloudPropertiesUtil implements
        ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    private static Logger logger = LoggerFactory.getLogger(CloudPropertiesUtil.class);
    
    private static Map<String, Object> cloudPropertyMap=new HashMap<String,Object>();

    private int order = Ordered.HIGHEST_PRECEDENCE + 11;

    @Autowired(required = false)
    private List<PropertySourceLocator> propertySourceLocators = Collections.EMPTY_LIST;


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!isHasCloudConfigLocator(this.propertySourceLocators)) {
            logger.info("未启用Config Server管理配置");
            return;
        }


        logger.info("检查Config Service配置资源");

        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        MutablePropertySources propertySources = environment.getPropertySources();
        logger.info("加载PropertySources源：" + propertySources.size() + "个");

        CloudConfigSupportProperties configSupportProperties = new CloudConfigSupportProperties();
        new RelaxedDataBinder(configSupportProperties, CloudConfigSupportProperties.CONFIG_PREFIX)
                .bind(new PropertySourcesPropertyValues(propertySources));
        if (!configSupportProperties.isEnable()) {
            logger.warn("未启用配置备份功能，可使用{}.enable打开", CloudConfigSupportProperties.CONFIG_PREFIX);
            return;
        }


        if (isCloudConfigLoaded(propertySources)) {
            PropertySource cloudConfigSource = getLoadedCloudPropertySource(propertySources);
            logger.info("成功获取ConfigService配置资源");
            //备份
            cloudPropertyMap = makeBackupPropertyMap(cloudConfigSource);
            //doBackup(cloudPropertyMap, configSupportProperties.getFile());

        } else {
            logger.error("获取ConfigService配置资源失败");

            Properties backupProperty = loadBackupProperty(configSupportProperties.getFile());
            if (backupProperty != null) {
                HashMap backupSourceMap = new HashMap<>(backupProperty);

                PropertySource backupSource = new MapPropertySource("backupSource", backupSourceMap);
                propertySources.addFirst(backupSource);
                logger.warn("使用备份的配置启动：{}", configSupportProperties.getFile());
            }
        }
    }

    /**
     * 是否启用了Spring Cloud Config获取配置资源
     *
     * @param propertySourceLocators
     * @return
     */
    private boolean isHasCloudConfigLocator(List<PropertySourceLocator> propertySourceLocators) {
        for (PropertySourceLocator sourceLocator : propertySourceLocators) {
            if (sourceLocator instanceof ConfigServicePropertySourceLocator) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否启用Cloud Config
     *
     * @param propertySources
     * @return
     */
    private boolean isCloudConfigLoaded(MutablePropertySources propertySources) {
        if (getLoadedCloudPropertySource(propertySources) == null) {
            return false;
        }
        return true;
    }

    /**
     * 获取加载的Cloud Config 配置项
     *
     * @param propertySources
     * @return
     */
    private PropertySource getLoadedCloudPropertySource(MutablePropertySources propertySources) {
        if (!propertySources.contains(PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME)) {
            return null;
        }
        PropertySource propertySource = propertySources.get(PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME);
        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> source : ((CompositePropertySource) propertySource).getPropertySources()) {
                if (source.getName().equals("configService")) {
                    return source;
                }
            }
        }
        return null;
    }


    /**
     * 生成备份的配置数据
     *
     * @param propertySource
     * @return
     */
    private Map<String, Object> makeBackupPropertyMap(PropertySource propertySource) {
//        PropertySource backupSource = new MapPropertySource("backupSource", backupSourceMap);
        Map<String, Object> backupSourceMap = new HashMap<>();

        if (propertySource instanceof CompositePropertySource) {
            CompositePropertySource composite = (CompositePropertySource) propertySource;
            for (PropertySource<?> source : composite.getPropertySources()) {
                if (source instanceof MapPropertySource) {
                    MapPropertySource mapSource = (MapPropertySource) source;
                    for (String propertyName : mapSource.getPropertyNames()) {
                        // 前面的配置覆盖后面的配置
                        if (!backupSourceMap.containsKey(propertyName)) {
                            backupSourceMap.put(propertyName, mapSource.getProperty(propertyName));
                        }
                    }
                }
            }
        }
        return backupSourceMap;
    }

    private void doBackup(Map<String, Object> backupPropertyMap, String filePath) {
        FileSystemResource fileSystemResource = new FileSystemResource(filePath);
        File backupFile = fileSystemResource.getFile();
        try {
            if (!backupFile.exists()) {
                backupFile.createNewFile();
            }
            if (!backupFile.canWrite()) {
                logger.error("无法读写文件：{}", fileSystemResource.getPath());
            }

            Properties properties = new Properties();
            Iterator<String> keyIterator = backupPropertyMap.keySet().iterator();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                properties.setProperty(key, String.valueOf(backupPropertyMap.get(key)));
            }

            FileOutputStream fos = new FileOutputStream(fileSystemResource.getFile());
            properties.store(fos, "Backup Cloud Config");
        } catch (IOException e) {
            logger.error("文件操作失败：{}", fileSystemResource.getPath());
            e.printStackTrace();
        }
    }

    private Properties loadBackupProperty(String filePath) {
        PropertiesFactoryBean propertiesFactory = new PropertiesFactoryBean();
        Properties props = new Properties();
        try {
            FileSystemResource fileSystemResource = new FileSystemResource(filePath);
            propertiesFactory.setLocation(fileSystemResource);

            propertiesFactory.afterPropertiesSet();
            props = propertiesFactory.getObject();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return props;
    }


    @Override
    public int getOrder() {
        return this.order;
    }
    
    /**
	 * 以String型获取配置文件中指定key的值，获取失败默认将返回 ""
	 * 
	 * @param name
	 *            配置文件中的key值
	 * @return
	 */
	public static String getProperty(String name) {
		if (StringUtil.isNotEmpty(name)) {
			String val = (String)(cloudPropertyMap.get(name));
			return (StringUtil.isNotEmpty(val)) ? val.trim() : "";
		} else {
			return "";
		}
	}

	/**
	 * 以String型获取配置文件中指定key的值
	 * 
	 * @param name
	 *            配置文件中的key值
	 * @param dv
	 *            获取失败的默认值
	 * @return
	 */
	public static String getProperty(String name, String dv) {
		String val = getProperty(name);
		if (StringUtil.isNotEmpty(val)) {
			return val;
		} else {
			return dv;
		}
	}

	/**
	 * 以int型获取配置文件中指定key的值，获取失败默认将返回0
	 * 
	 * @param name
	 *            配置文件中的key值
	 * @return
	 */
	public static int getPropertyInt(String name) {
		return getPropertyInt(name, 0);
	}

	/**
	 * 以int型获取配置文件中指定key的值
	 * 
	 * @param name
	 *            配置文件中的key值
	 * @param dv
	 *            获取失败的默认值
	 * @return
	 */
	public static int getPropertyInt(String name, int dv) {
		String val = getProperty(name);
		return StringUtil.String2Int(val, dv);
	}

	/**
	 * 字符串转成数组
	 * 
	 * @param name
	 * @param split
	 * @return
	 */
	public static String[] getPropertyArray(String name, String split) {
		String val = getProperty(name);
		split = StringUtil.isEmpty(split) ? "," : split;
		if (StringUtil.isNotEmpty(val)) {
			return val.split(split);
		} else {
			return new String[] {};
		}
	}

	public static long getPropertyLong(String keystr) {
		return getPropertyLong(keystr, 0);
	}

	public static long getPropertyLong(String name, long dv) {
		String val = getProperty(name);
		return StringUtil.String2Long(val, dv);
	}

	public static boolean getPropertyBoolean(String name, boolean dv) {
		String val = getProperty(name);
		try {
			return Boolean.parseBoolean(val);
		} catch (Exception e) {

		}
		return dv;
	}
}
