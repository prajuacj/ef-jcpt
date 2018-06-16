package com.ef.jcpt.core.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.jcpt.common.config.RedisCachePropertiesConfig;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.common.util.DateUtil;
import com.ef.jcpt.common.util.StringUtil;

/**
 * ClassName: IDProvider <br>
 * Description: IDProvider
 * @author xiezbmf
 * @Date 2017年10月24日下午4:11:19 <br>
 * @version
 * @since JDK 1.6
 */
public class IDProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(IDProvider.class);
	public final static String ID_PROVIDER_GLOABL_KEY_PREFIX = RedisCachePropertiesConfig.ID_PROVIDER_GLOABL_KEY_PREFIX;
	public final static String FLOW_PROVIDER_GLOABL_KEY_PREFIX = RedisCachePropertiesConfig.FLOW_PROVIDER_GLOABL_KEY_PREFIX;
	public final static int SEQ_LEN = 8;
	public final static int MAX_SEQ_LEN = 32;
	public final static long MAX_VALUE = getMaxValue(SEQ_LEN);
	public final static char PADDING_CHAR = '0';
	
	public static long getMaxValue(int len) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<len;i++) {
			sb.append("9");
		}
		return Long.valueOf(sb.toString());
	}
	/**
	 * 
	 * getId:获取单个id. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年10月24日下午5:44:03 <br>
	 * @param tableKey 各个表的缩写会作为前缀
	 * @return
	 */
	public static String getId(String tableKey) {
		if(StringUtil.isEmpty(tableKey)) {
			return null;
		}
		int leftLen = MAX_SEQ_LEN-SEQ_LEN-DateUtil.YMD_H_NUM.length();
		if(tableKey.length()>leftLen) {
			logger.error(LogTemplate.genCommonSysFailLogStr("core:getId", "tableKey too long"));
			return null;
		}
		String key = ID_PROVIDER_GLOABL_KEY_PREFIX+tableKey;
		Long value = RedisManager.getIncre(key);
		if(value!=null&&value>0) {
			if(value>=MAX_VALUE) {
				RedisManager.del(key);
				value = RedisManager.getIncre(key);
			}
			String prefix = DateUtil.format(new Date(), DateUtil.YMD_H_NUM);
			String suffix;
			try {
				suffix = StringUtil.getPrefixPadding(value, SEQ_LEN, PADDING_CHAR);
				return tableKey+prefix+suffix;
			} catch (Exception e) {
				logger.error(LogTemplate.genCommonSysFailLogStr("core:getId", e.getMessage()));
			}
			
		}
		return null;
	}
	
	public static String getFlowId(String flowKey) {
		if(StringUtil.isEmpty(flowKey)) {
			return null;
		}
		//int leftLen = MAX_SEQ_LEN-SEQ_LEN-DateUtil.YMD_H_NUM.length();
		/*if(flowKey.length()>leftLen) {
			logger.error(LogTemplate.genCommonSysFailLogStr("core:getId", "tableKey too long"));
			return null;
		}*/
		String key = FLOW_PROVIDER_GLOABL_KEY_PREFIX+flowKey;
		Long value = RedisManager.getIncre(key);
		if(value!=null&&value>0) {
			if(value>=MAX_VALUE) {
				RedisManager.del(key);
				value = RedisManager.getIncre(key);
			}
			String prefix = DateUtil.format(new Date(), DateUtil.YMD_HM_NUM);
			String suffix;
			try {
				suffix = StringUtil.getPrefixPadding(value, SEQ_LEN, PADDING_CHAR);
				return prefix+suffix;
			} catch (Exception e) {
				logger.error(LogTemplate.genCommonSysFailLogStr("core:getId", e.getMessage()));
			}
			
		}
		return null;
	}
	
	/**
	 * 
	 * getIds:获取批量的id. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年10月24日下午5:44:42 <br>
	 * @param tableKey 各个表的缩写会作为前缀
	 * @param n
	 * @return
	 */
	public static List<String> getIds(String tableKey,int n) {
		if(StringUtil.isEmpty(tableKey)) {
			return null;
		}
		int leftLen = MAX_SEQ_LEN-SEQ_LEN-DateUtil.YMD_H_NUM.length();
		if(tableKey.length()>leftLen) {
			logger.error(LogTemplate.genCommonSysFailLogStr("core:getIds", "tableKey too long"));
			return null;
		}
		String key = ID_PROVIDER_GLOABL_KEY_PREFIX+tableKey;
		Long first = RedisManager.getIncre(key);
		if(first+n>=MAX_VALUE) {
			RedisManager.del(key);
			first = RedisManager.getIncre(key);
		}
		List<String> idList = new ArrayList<String>();
		if(first!=null&&first>0) {
			Long end = RedisManager.getIncre(key,n-1);
			String prefix = DateUtil.format(new Date(), DateUtil.YMD_H_NUM);
			if(end-first!=n-1) {
				logger.error(LogTemplate.genCommonSysFailLogStr("core:getIds", "生成的id数与需求数不匹配"));
				return null;
			}
			for(int i=0;i<n;i++) {
				String suffix;
				try {
					suffix = StringUtil.getPrefixPadding(first+i, SEQ_LEN, PADDING_CHAR);
					idList.add(tableKey+prefix+suffix);
				} catch (Exception e) {
					logger.error(LogTemplate.genCommonSysFailLogStr("core:getIds", e.getMessage()));
					return null;
				}
			}
			return idList;
		}
		return null;
	}
}

	