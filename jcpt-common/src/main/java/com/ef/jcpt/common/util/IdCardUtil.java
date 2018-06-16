/**
 * Project Name:caifubao-common <br>
 * File Name:IdCardUtil.java <br>
 * Package Name:com.caifubao.jcpt.common.util <br>
 * @author xiezbmf
 * Date:2018年1月31日下午7:34:34 <br>
 * Copyright (c) 2018, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;
/**
 * ClassName: IdCardUtil <br>
 * Description: 身份证工具类
 * @author xiezbmf
 * @Date 2018年1月31日下午7:34:34 <br>
 * @version
 * @since JDK 1.8
 */
public class IdCardUtil {

	public static String toUpper(String idNo) {
		return idNo.toUpperCase();
	}
	
	public static String getBirthday(String idNo) {
		if(idNo.length()==15) {
			String birthday = "19" + idNo.substring(6, 8) + "-" 
					+ idNo.substring(8, 10) + "-" 
					+ idNo.substring(10, 12);
			return birthday;	
			
		}
		if(idNo.length()==18) {
			String birthday = idNo.substring(6, 10) + "-" 
					+ idNo.substring(10, 12) + "-" 
					+ idNo.substring(12, 14);
			return birthday;	
		}
		return "1900-01-01";
	}
	
	public static String getSex(String idNo) {
		if(idNo.length()==15) {
			String sex = Integer.parseInt(idNo.substring(14,15))%2==0?"FEMALE":"MALE";	
			return sex;
		}
		if(idNo.length()==18) {
			String sex = Integer.parseInt(idNo.substring(16,17))%2==0?"FEMALE":"MALE";	
			return sex;
		}
		return "UNKNOWN";
	}
}

	