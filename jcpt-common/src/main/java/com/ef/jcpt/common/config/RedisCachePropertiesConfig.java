package com.ef.jcpt.common.config;

import com.ef.jcpt.common.util.CloudPropertiesUtil;

/**
 * ClassName: RedisCachePropertiesConfig <br>
 * Description: redis缓存相关配置信息
 * 
 * @author xiezbmf
 * @Date 2017年10月19日下午6:17:26 <br>
 * @version
 * @since JDK 1.6
 */
public interface RedisCachePropertiesConfig {
	// redis服务器配置

	String REDIS_HOST = CloudPropertiesUtil.getProperty("redis.host", "10.50.10.203");

	String REDIS_PWD = CloudPropertiesUtil.getProperty("redis.pwd");

	int REDIS_PORT = CloudPropertiesUtil.getPropertyInt("redis.port", 6379);
	/**
	 * 缓存有效时间，单位秒
	 */
	int REDIS_EXPIRE = CloudPropertiesUtil.getPropertyInt("redis.expire", 1800);

	/**
	 * REDIS_CONNECT_TIMEOUT:连接超时时间 单位秒.
	 */
	int REDIS_CONNECT_TIMEOUT = CloudPropertiesUtil.getPropertyInt("redis.connect.timeout", 2000);

	// redis服务缓存key常量

	/**
	 * POWER_REDIS_KEY_PREFIX: 权限缓存前缀
	 */
	String POWER_REDIS_KEY_PREFIX = "redis_power_";

	/**
	 * ROLE_REDIS_KEY_PREFIX: 角色缓存前缀
	 */
	String ROLE_REDIS_KEY_PREFIX = "redis_role_";

	/**
	 * sid cookie对应的key
	 */
	String SID_COOKIE = CloudPropertiesUtil.getProperty("sid.cookie.key", "_SID_hhnjcpt");

	String JCPT_SHIRO_SESSION_KEY = CloudPropertiesUtil.getProperty("shiro.session.key", "JCPT_USER_SID:");;// "jcpt_shiro_session:";

	/**
	 * 全局id生成缓存key
	 */
	String ID_PROVIDER_GLOABL_KEY_PREFIX = "ID_PROVIDER_KEY_";

	/**
	 * 全局id生成缓存key
	 */
	String FLOW_PROVIDER_GLOABL_KEY_PREFIX = "FLOW_PROVIDER_KEY_";

	/**
	 * 全局交易流水号生成缓存key.
	 */
	String TRADE_NO_PROVIDER_GLOABL_KEY_PREFIX = "TRADE_NO_PROVIDER_KEY_";
}
