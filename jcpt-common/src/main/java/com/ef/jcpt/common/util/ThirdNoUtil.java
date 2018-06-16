/**
 * Project Name:caifubao-datamigration <br>
 * File Name:ThirdNoUtil.java <br>
 * Package Name:com.caifubao.jcpt.datamigration.util <br>
 * @author chenjmf
 * Date:2018年4月1日下午4:41:07 <br>
 * Copyright (c) 2018, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;

import java.util.Date;
import java.util.Random;

import com.ef.jcpt.common.util.DateUtil;

/**
 * ClassName: ThirdNoUtil <br>
 * Description: TODO
 * 
 * @author chenjmf
 * @Date 2018年4月1日下午4:41:07 <br>
 * @version
 * @since JDK 1.6
 */
public class ThirdNoUtil {

	public static String getThirdNo() {
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		builder.append(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
		builder.append(random.nextInt(899999) + 100000);
		return builder.toString();
	}

	public static String getOrderId() {
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		builder.append(DateUtil.format(new Date(), "yyyyMMdd"));
		builder.append(random.nextInt(89999999) + 10000000);
		return builder.toString();
	}

	public static void main(String[] args) {
		System.out.println(getThirdNo());
		System.out.println(getThirdNo());
		System.out.println(getThirdNo());
		System.out.println(getThirdNo());
		System.out.println(getThirdNo());
		System.out.println(getThirdNo());
		System.out.println(getThirdNo());
		System.out.println(getThirdNo());
		System.out.println(getThirdNo());
		System.out.println(getThirdNo());
		System.out.println(getOrderId());
	}
}
