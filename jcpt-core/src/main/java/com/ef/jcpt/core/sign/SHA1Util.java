/**
 * Project Name:caifubao-core <br>
 * File Name:SHA1Util.java <br>
 * Package Name:com.caifubao.jcpt.core.security.digest <br>
 * @author chenjmf
 * Date:2018年4月6日下午3:13:22 <br>
 * Copyright (c) 2018, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.core.sign;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * ClassName: SHA1Util <br>
 * Description: TODO
 * 
 * @author chenjmf
 * @Date 2018年4月6日下午3:13:22 <br>
 * @version
 * @since JDK 1.6
 */
public class SHA1Util {

	// 微信公众号开发的获取时间戳
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	// 创建签名SHA1
	public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set es = signParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
			// 要采用URLENCODER的原始值！
		}
		String params = sb.substring(0, sb.lastIndexOf("&"));

		return getSha1(params);
	}

	// Sha1签名
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
}
