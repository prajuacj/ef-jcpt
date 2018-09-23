package com.ef.jcpt.trade.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class PayInfo {
	
    private String flowId;

	private String orderId;

	private String userId;

	private String orderContent;

	private BigDecimal payAmount;

	private String payChannel;

	private String payStatus;

	private String usedNationCode;

	private BigDecimal handFee;

	private String requestParam;

	private String responseContent;

	private String payMemo;

	private String failReason;

	private String returnFlow;

	private Date createTime;

	private Date updateTime;

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId == null ? null : flowId.trim();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent == null ? null : orderContent.trim();
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
		this.payChannel = payChannel == null ? null : payChannel.trim();
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus == null ? null : payStatus.trim();
	}

	public String getUsedNationCode() {
		return usedNationCode;
	}

	public void setUsedNationCode(String usedNationCode) {
		this.usedNationCode = usedNationCode == null ? null : usedNationCode.trim();
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
		this.requestParam = requestParam == null ? null : requestParam.trim();
	}

	public String getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent == null ? null : responseContent.trim();
	}

	public String getPayMemo() {
		return payMemo;
	}

	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo == null ? null : payMemo.trim();
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason == null ? null : failReason.trim();
	}

	public String getReturnFlow() {
		return returnFlow;
	}

	public void setReturnFlow(String returnFlow) {
		this.returnFlow = returnFlow == null ? null : returnFlow.trim();
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