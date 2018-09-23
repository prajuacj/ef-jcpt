package com.ef.jcpt.trade.service.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6793403933712125626L;

	private String orderId;

	private String mobile;

	private String userId;

	private String productId;

	private String productName;

	private BigDecimal price;

	private Integer productNum;

	private BigDecimal totalAmount;

	private BigDecimal discountAmount;

	private BigDecimal payAmount;

	private String orderStatus;

	private Integer operatorId;

	private String operatorName;

	private String operatorNationCode;

	private String operatorNationName;

	private Date payTime;

	private Date validTime;
	
	private Integer validDay;

	private BigDecimal remainFlow;

	private String remark;

	private Date createTime;

	private Date updateTime;
	
	private String userNationCode;
	
	private String productNationCode;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorNationCode() {
		return operatorNationCode;
	}

	public void setOperatorNationCode(String operatorNationCode) {
		this.operatorNationCode = operatorNationCode;
	}

	public String getOperatorNationName() {
		return operatorNationName;
	}

	public void setOperatorNationName(String operatorNationName) {
		this.operatorNationName = operatorNationName;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public BigDecimal getRemainFlow() {
		return remainFlow;
	}

	public void setRemainFlow(BigDecimal remainFlow) {
		this.remainFlow = remainFlow;
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

	public String getUserNationCode() {
		return userNationCode;
	}

	public void setUserNationCode(String userNationCode) {
		this.userNationCode = userNationCode;
	}

	public String getProductNationCode() {
		return productNationCode;
	}

	public void setProductNationCode(String productNationCode) {
		this.productNationCode = productNationCode;
	}

	public Integer getValidDay() {
		return validDay;
	}

	public void setValidDay(Integer validDay) {
		this.validDay = validDay;
	}
}
