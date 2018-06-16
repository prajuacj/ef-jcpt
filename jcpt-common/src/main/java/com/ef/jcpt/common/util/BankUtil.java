/**
 * Project Name:caifubao-common <br>
 * File Name:BankUtil.java <br>
 * Package Name:com.caifubao.jcpt.common.util <br>
 * @author chenjmf
 * Date:2018年3月30日上午9:20:35 <br>
 * Copyright (c) 2018, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;

/**
 * ClassName: BankUtil <br>
 * Description: TODO
 * 
 * @author chenjmf
 * @Date 2018年3月30日上午9:20:35 <br>
 * @version
 * @since JDK 1.6
 */
public class BankUtil {
	/**
	 * PA_BANK:平安银行卡号开始数字标识.
	 */
	public static final int[] PA_BANK = new int[] { 526855, 528020, 622155, 622156,602999 };

	public static boolean isPaBank(String cardNo) {
		if (StringUtil.isEmpty(cardNo)) {
			return false;
		}
		int prefix = Integer.parseInt(cardNo.substring(0, 6));
		int len = PA_BANK.length;
		for (int i = 0; i < len; i++) {
			if (prefix == PA_BANK[i]) {
				return true;
			}
		}
		return false;
	}
}
