/**
 * Project Name:caifubao-common <br>
 * File Name:UnicodeUtil.java <br>
 * Package Name:com.caifubao.jcpt.common.util <br>
 * @author xiezbmf
 * Date:2018年3月25日上午11:35:44 <br>
 * Copyright (c) 2018, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: UnicodeUtil <br>
 * Description: TODO
 * @author xiezbmf
 * @Date 2018年3月25日上午11:35:44 <br>
 * @version
 * @since JDK 1.8
 */
public class UnicodeUtil {
	public static String unicodeToString(String str) {  
		  
	    Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");  
	    Matcher matcher = pattern.matcher(str);  
	    char ch;  
	    while (matcher.find()) {  
	        //group 6728  
	        String group = matcher.group(2);  
	        //ch:'木' 26408  
	        ch = (char) Integer.parseInt(group, 16);  
	        //group1 \u6728  
	        String group1 = matcher.group(1);  
	        str = str.replace(group1, ch + "");  
	    }  
	    return str;  
	}  
     
	public static String stringToUnicode(String s) {  
	    try {  
	        StringBuffer out = new StringBuffer("");  
	        //直接获取字符串的unicode二进制  
	        byte[] bytes = s.getBytes("unicode");  
	        //然后将其byte转换成对应的16进制表示即可  
	        for (int i = 0; i < bytes.length - 1; i += 2) {  
	            out.append("\\u");  
	            String str = Integer.toHexString(bytes[i + 1] & 0xff);  
	            for (int j = str.length(); j < 2; j++) {  
	                out.append("0");  
	            }  
	            String str1 = Integer.toHexString(bytes[i] & 0xff);  
	            out.append(str1);  
	            out.append(str);  
	        }  
	        return out.toString();  
	    } catch (UnsupportedEncodingException e) {  
	        e.printStackTrace();  
	        return null;  
	    }  
	}
}

	