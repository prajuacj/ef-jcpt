package com.ef.jcpt.trade.service.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PayInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6657815823991155742L;

	private String flowId;

	private String orderId;

	private BigDecimal payAmount;

	private String payChannel;

	private String payStatus;

	private BigDecimal handFee;

	private String requestParam;

	private String responseContent;

	private String payMemo;

	private String failReason;

	private Date createTime;

	private Date updateTime;

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getHandFee() {
		return handFee;
	}

	public void setHandFee(BigDecimal handFee) {
		this.handFee = handFee;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public String getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	public String getPayMemo() {
		return payMemo;
	}

	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
