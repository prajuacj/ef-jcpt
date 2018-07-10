package com.ef.jcpt.trade.service.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Alipayh5Bo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9008883584036342875L;

	/**
	   * 支付订单号
	   */
	  private String outTradeNo;
	  /**
	   * 服务器异步通知页面路径
	   */
	  private String notifyUrl;
	  /**
	   * 服务器同步通知页面路径
	   */
	  private String returnUrl;
	  /**
		* 支付金额
		*/
	  private BigDecimal payAmount;
	  /**
	   * 支付点击返回跳转页面路径
	   */
	  private String backUrl;
	  /**
	   * 扩展字段
	   */
	  private String ext1;
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
}
