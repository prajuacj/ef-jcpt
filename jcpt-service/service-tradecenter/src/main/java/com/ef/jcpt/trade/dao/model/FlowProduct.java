package com.ef.jcpt.trade.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class FlowProduct {
	private Integer id;

	private String productName;

	private Integer productNum;

	private BigDecimal price;

	private String productType;

	private String productInstruction;

	private Short productTerm;

	private BigDecimal preferentialPrice;

	private String useArea;

	private String productStatus;

	private String backFile;

	private String remark;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType == null ? null : productType.trim();
	}

	public String getProductInstruction() {
		return productInstruction;
	}

	public void setProductInstruction(String productInstruction) {
		this.productInstruction = productInstruction == null ? null : productInstruction.trim();
	}

	public Short getProductTerm() {
		return productTerm;
	}

	public void setProductTerm(Short productTerm) {
		this.productTerm = productTerm;
	}

	public BigDecimal getPreferentialPrice() {
		return preferentialPrice;
	}

	public void setPreferentialPrice(BigDecimal preferentialPrice) {
		this.preferentialPrice = preferentialPrice;
	}

	public String getUseArea() {
		return useArea;
	}

	public void setUseArea(String useArea) {
		this.useArea = useArea == null ? null : useArea.trim();
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus == null ? null : productStatus.trim();
	}

	public String getBackFile() {
		return backFile;
	}

	public void setBackFile(String backFile) {
		this.backFile = backFile == null ? null : backFile.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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