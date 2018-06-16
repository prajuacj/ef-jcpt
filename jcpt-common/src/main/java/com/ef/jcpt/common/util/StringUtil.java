/**
 * Project Name:caifubao-core <br>
 * File Name:StringUtil.java <br>
 * Package Name:com.caifubao.jcpt.core.utils <br>
 * @author xiezbmf
 * Date:2017年10月18日上午9:00:21 <br>
 * Copyright (c) 2017, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.ef.jcpt.common.config.CommonPropertiesConfig;
import com.ef.jcpt.common.log.LogTemplate;

/**
 * ClassName: StringUtil <br>
 * Description: 字符串工具类，继承spring.StringUtils
 * 
 * @author xiezbmf
 * @Date 2017年10月18日上午9:00:21 <br>
 * @version
 * @since JDK 1.6
 */
public class StringUtil extends StringUtils {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	public static int String2Int(String val, int dv) {
		try {
			if (StringUtil.isNotEmpty(val)) {
				int v = Integer.valueOf(val);
				return v;
			}
		} catch (NumberFormatException e) {
		}
		return dv;
	}

	public static long String2Long(String val, long dv) {

		try {
			if (StringUtil.isNotEmpty(val)) {
				long v = Long.valueOf(val);
				return v;
			}
		} catch (NumberFormatException e) {
		}
		return dv;
	}

	/**
	 * 进行html编码操作
	 * 
	 * @param s  转码后的字符
	 * @return
	 */
	public static String encodeHTML(String s) {
		if (isNotEmpty(s)) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				char ch = s.charAt(i);
				switch (ch) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\'':
					sb.append("&#39;");
					break;
				default:
					sb.append(ch);
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	/**
	 * html解码
	 * 
	 * @param s 解码后的字符
	 * @return
	 */
	public static String decodeHTML(String s) {
		s = s.replaceAll("&lt;", "<");
		s = s.replaceAll("&gt;", ">");
		s = s.replaceAll("&quot;", "\"");
		s = s.replaceAll("&nbsp;", " ");
		s = s.replaceAll("&amp;", "&");
		s = s.replace("&#39;", "'");
		return s;
	}

	/**
	 * 
	 * getUUID32:(获取32位uuid). <br/>
	 *
	 * @author xiezbmf 
	 * @Date:2016年9月12日下午3:12:37 <br/>
	 * @return
	 */
	public static String getUUID32() {
		UUID uuid = UUID.randomUUID();
		String s = uuid.toString();
		s = s.replace("-", "");
		return s;
	}

	/**
	 * 
	 * urlEncode:(采用utf-8，进行url编码). <br/>
	 *
	 * @author xiezbmf 
	 * @Date:2016年9月12日下午3:12:29 <br/>
	 * @param str
	 * @return
	 */
	public static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, CommonPropertiesConfig.DEFAULT_ENCODE);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * urlDecode:(采用utf-8，进行url解码). <br/>
	 *
	 * @author xiezbmf Date:2016年9月12日下午3:13:40 <br/>
	 * @param str
	 * @return
	 */
	public static String urlDecode(String str) {
		try {
			return URLDecoder.decode(str, CommonPropertiesConfig.DEFAULT_ENCODE);
		} catch (Exception e) {
			return null;
		}

	}

	public static String getPrefixPadding(int num, int length, char prefix){
		return getPrefixPadding(String.valueOf(num), length, prefix);
	}
	
	public static String getPrefixPadding(Long num, int length, char prefix){
		return getPrefixPadding(String.valueOf(num), length, prefix);
	}
	
	/**
	 * 
	 * getPrefixPadding:前缀补齐字符串，当长度超长时会截取. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年10月25日上午9:29:33 <br>
	 * @param old 原始字符串
	 * @param length 拼接后的字符串长度
	 * @param prefix 前缀补齐字符
	 * @return
	 */
	public static String getPrefixPadding(String old, int length, char prefix){
		StringBuilder sb = new StringBuilder();
		int serLen = old.length();
		if (serLen <= length) {
			int len = length - serLen;
			for (int i = 0; i < len; i++) {
				sb.append(prefix);
			}
			sb.append(old);
			return sb.toString();
		} else {
			return old.substring(0, length);
		}
	}

	/**
	 * 
	 * underscoreToCamelCase:下划线转驼峰. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年7月26日上午10:54:35 <br>
	 * @param colName
	 * @return
	 */
	public static String underscoreToCamelCase(String colName) {
		if (isEmpty(colName)) {
			return colName;
		}
		colName = colName.toLowerCase();
		String[] cols = colName.split("_");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cols.length; i++) {
			if (i == 0) {
				sb.append(cols[0]);
				continue;
			}
			char c = cols[i].charAt(0);
			char C = (char) (c - 32);
			sb.append(C).append(cols[i].substring(1));
		}
		return sb.toString();
	}

	public static boolean hasIn(String key, String[] keys) {

		for (String str : keys) {
			if (str.equals(key)) {
				return true;
			}
		}
		return false;
	}

	public static String strArrToString(String[] arr) {
		StringBuilder sb = new StringBuilder();
		for (String s : arr) {
			sb.append(s).append(",");
		}
		if (sb.length() > 0) {
			return sb.deleteCharAt(sb.length() - 1).toString();
		}
		return null;
	}

	/**
	 * 
	 * firstUpperCase:将首字母大写. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年8月16日上午10:51:01 <br>
	 * @param name
	 * @return
	 */
	public static String firstUpperCase(String name) {
		char c = name.charAt(0);
		String C = new String(new char[] { c }).toUpperCase();
		return C + name.substring(1);
	}

	public static String listToString(String[] list, String separator) {
		if (list == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str).append(separator);
		}
		return sb.toString();
	}
	
	
	/**
	 * getTemlateKey:获取${}模板中包含的所有key. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年10月26日下午3:01:16 <br>
	 * @param template
	 * @return
	 */
	public static List<String> getTemlateKey(String template){
		if(isEmpty(template)) {
			return null;
		}
		int index = 0;
		List<Integer> headList = new ArrayList<Integer>();
		while(true) {
			index = template.indexOf("${",index);
			if(index>=0) {
				headList.add(index);
				index+=2;
			}else {
				break;
			}
		}
		List<Integer> tailList = new ArrayList<Integer>();
		index = 0;
		while(true) {
			index = template.indexOf("}",index);
			if(index>=0) {
				tailList.add(index);
				index+=1;
			}else {
				break;
			}
		}
		int headSize = headList.size();
		int tailSize = tailList.size();
		if(headSize!=tailSize) {
			logger.error(LogTemplate.genCommonSysFailLogStr("common:getTemlateKey", "${}不匹配"));
			return null;
		}
		List<String> keyList = new ArrayList<String>();
		for(int i = 0;i<headSize;i++) {
			for(int j=0;j<tailSize;j++) {
				if(headList.get(i)>tailList.get(j)) {
					keyList.add(template.substring(headList.get(i-1)+2, tailList.get(j)));
					tailList.remove(j);
					tailSize-=1;
					i=0;
					break;
				}
				if(i==headSize-1) {
					if(headList.get(i)<tailList.get(j)) {
						keyList.add(template.substring(headList.get(i)+2, tailList.get(j)));
						tailList.remove(j);
						tailSize-=1;
						break;
					}
				}
			}
		}
		return keyList;
	}
}
