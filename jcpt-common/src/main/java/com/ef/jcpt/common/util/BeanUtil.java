/**
 * Project Name:jcpt-common <br>
 * File Name:BeanUtil.java <br>
 * Package Name:com.hehenian.jcpt.core.util <br>
 * @author xiezbmf
 * Date:2017年6月20日下午5:26:29 <br>
 * Copyright (c) 2017, 深圳市彩付宝网络技术有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

/**
 * ClassName: BeanUtil <br>
 * Description: java bean 工具类
 * 
 * @author xiezbmf
 * @Date 2017年6月20日下午5:26:29 <br>
 * @version
 * @since JDK 1.6
 */
public class BeanUtil extends BeanUtils {

	private static String firstUpperCase(String attr) {
		char c = attr.charAt(0);
		String C = new String(new char[] { c }).toUpperCase();
		return C + attr.substring(1);
	}

	/**
	 * 
	 * getAttrToMap:将java bean转化成对应的键值的map. <br>
	 * 
	 * @author xiezbmf
	 * @Date 2017年6月21日上午9:35:59 <br>
	 * @param o 实例对象
	 * @return
	 */
	public static Map<String, Object> getAttrToMap(Object o) {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		Method[] methods = cls.getDeclaredMethods();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			String fName = firstUpperCase(f.getName());
			try {
				Method method = cls.getDeclaredMethod("get" + fName, null);
				Object retobj = method.invoke(o, null);
				map.put(f.getName(), retobj);
			} catch (NoSuchMethodException e) {
				for (Method method : methods) {
					String name = method.getName();
					if (name.startsWith("get")) {
						name = name.substring(3);
						if (name.equalsIgnoreCase(f.getName())) {
							try {
								Object retobj = method.invoke(o, null);
								map.put(f.getName(), retobj);
							} catch (Exception e1) {
								//log.error("getAttrToMap attr " + f.getName() + " not set", e1);
							}
							break;
						}
					}
				}
				//log.error("未找到对应属性的" + f.getName() + "的get方法", e);
			} catch (Exception e) {
				//log.error("getAttrToMap attr " + f.getName() + " not set", e);
			}

		}
		return map;
	}

	/**
	 * 
	 * copySameProperties:复制javabean 相同属性. <br>
	 * 
	 * @author xiezbmf
	 * @Date 2017年6月21日上午9:48:28 <br>
	 * @param orig 原始实例
	 * @param dest 目标实例
	 */
	public static void copySameProperties(Object orig, Object dest) {
		try {
			BeanUtils.copyProperties(orig, dest);
		} catch (Exception e) {
			//log.error("copySameProperties error", e);
		}
	}

	public static void copyProperties(Object source, Object target)
			throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (value != null) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod
									.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target",ex);
					}
				}
			}
		}
	}

	
	/**
	 * copyPropertiesList:复制javabean列表. <br>
	 *
	 * @author sungymf
	 * @param <T>
	 * @Date 2017年10月23日上午9:37:05 <br>
	 * @param list	源list
	 * @param target 目标类CLASS
	 * @param objects
	 * @return
	 */
	public static List<?> copyPropertiesList(List<?> list, Class target) {
        List<Object> result = new ArrayList();
        if (list != null) {
            for (Object o : list) {
                try {
                    Object d = target.newInstance();
                    copyProperties(o,d);
                    result.add(d);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
