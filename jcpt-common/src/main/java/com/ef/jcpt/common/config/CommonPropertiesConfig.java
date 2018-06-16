/**
 * Project Name:caifubao-common <br>
 * File Name:CommonPropertiesConfig.java <br>
 * Package Name:com.caifubao.jcpt.common.constant.config <br>
 * @author xiezbmf
 * Date:2017年10月17日下午5:45:02 <br>
 * Copyright (c) 2017, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.config;

import com.ef.jcpt.common.util.CloudPropertiesUtil;

/**
 * ClassName: CommonPropertiesConfig <br>
 * Description: TODO
 * 
 * @author xiezbmf
 * @Date 2017年10月17日下午5:45:02 <br>
 * @version
 * @since JDK 1.6
 */
public interface CommonPropertiesConfig {
	String DEFAULT_ENCODE = "UTF-8";

	/**
	 * SYSTEM_SUPERROLE:超级管理员角色名称.
	 */
	String SYSTEM_SUPERROLE = "系统超级管理员";

	/**
	 * SYSTEM_USER_ID:系统用户id.
	 */
	String SYSTEM_USER_ID = "-1";

	/**
	 * SYSTEM_USER_NAME:系统用户名.
	 */
	String SYSTEM_USER_NAME = "管理员";

	/**
	 * SMS_URL:短信发送请求地址.
	 */
	String SMS_URL = CloudPropertiesUtil.getProperty("sms.url");
	/**
	 * SMS_MD5KEY:短信发送加密key.
	 */
	String SMS_MD5KEY = CloudPropertiesUtil.getProperty("sms.md5key");
}
