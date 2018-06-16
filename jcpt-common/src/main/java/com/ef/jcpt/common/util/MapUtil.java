/**
 * Project Name:caifubao-common <br>
 * File Name:MapUtil.java <br>
 * Package Name:com.caifubao.jcpt.common.util <br>
 * @author xiezbmf
 * Date:2017年11月9日下午3:26:22 <br>
 * Copyright (c) 2017, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.CollectionUtils;

/**
 * ClassName: MapUtil <br>
 * Description: TODO
 * @author xiezbmf
 * @Date 2017年11月9日下午3:26:22 <br>
 * @version
 * @since JDK 1.6
 */
public class MapUtil {
	/**
	 * 
	 * isAllNullValueMap:判断Map中的值是否全部为空，空字符串也认为是空值. <br>
	 * 
	 * @author xiezbmf
	 * @Date 2017年2月22日下午2:14:03 <br>
	 * @param map
	 * @return
	 */
	public static boolean isAllNullValueMap(Map<String, Object> map) {
		if (CollectionUtils.isEmpty(map)) {// 空map
			return true;
		}
		for (Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if (entry.getValue() != null) {
				if (value instanceof String) {
					if (!"".equals(value.toString())) {// 非空字符串
						return false;
					}
				} else {// 非空对象
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * getValue:从codeListmap中获取值. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年8月23日下午3:00:58 <br>
	 * @param key
	 * @param codeList
	 * @param dv dv!=empty 找不到时的默认值
	 * @return
	 */
	public static String getValue(String key, Map<String, String> codeList,
			String dv) {
		if(codeList==null) {
			if (StringUtil.isNotEmpty(dv)) {
				return dv;
			}
			return null;
		}
		String value = codeList.get(key);
		if (StringUtil.isNotEmpty(value)) {
			return value;
		}
		if (StringUtil.isNotEmpty(dv)) {
			return dv;
		}
		return key;
	}
	
	/**
	 * 
	 * getValue:从codeListmap中获取值. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年8月23日下午3:01:44 <br>
	 * @param key
	 * @param codeList
	 * @return 找不到时返回key
	 */
	public static String getValue(String key, Map<String, String> codeList) {
		return getValue(key, codeList, null);
	}
	
	
	
	/**
	 * getMapValue:(这里用一句话描述这个方法的作用). <br>
	 *
	 * @author sungymf
	 * @Date 2017年8月31日上午10:37:31 <br>
	 * @param mapPara
	 * @param strKey
	 * @return
	 */
	public static String getMapValue(Map<String, Object> mapPara, String strKey) {
		String strRes = "";
		String strDefault = "";
		if (mapPara == null || mapPara.size() == 0) {
			return strDefault;
		}
		if (mapPara.get(strKey) == null) {
			return strDefault;
		}
		strRes = String.valueOf(mapPara.get(strKey));
		if (strRes == null || "".equals(strRes.trim())) {
			return strDefault;
		}
		strRes = strRes.trim();
		return strRes;
	}
	
	
	/**
	 * getBigDecimal:(这里用一句话描述这个方法的作用). <br>
	 *
	 * @author sungymf
	 * @Date 2018年1月17日下午2:32:29 <br>
	 * @param map
	 * @param key
	 * @return
	 */
	public static BigDecimal getBigDecimal(Map<String,Object> map,String key)
	{
		BigDecimal result=BigDecimal.ZERO;
		 if(map!=null)
		 {
			 Object investamt=map.get(key);
			 if(investamt!=null)
			 {
				 if(investamt instanceof BigDecimal)
				 {
					 result=(BigDecimal) investamt;
				 }else if (investamt instanceof Double)
				 {
					 Double num=(Double)investamt;
					 result=new BigDecimal(num);
				 }else if(investamt instanceof Long)
				 {
					 Long num=(Long)investamt;
					 result=BigDecimal.valueOf(num);
				 }else if(investamt instanceof Integer)
				 {
					 Integer num=(Integer)investamt;
					 result=new BigDecimal(num);
				 }else if(investamt instanceof String){
					result=StringUtil.isEmpty(String.valueOf(investamt)) ? null : new BigDecimal(String.valueOf(investamt));
				 }
				 else{
					 result=new BigDecimal(investamt.toString());
				 }
			 }
		 }
		 return result;
	}
}

	