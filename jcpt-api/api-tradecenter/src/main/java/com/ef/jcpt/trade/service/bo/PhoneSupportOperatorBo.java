package com.ef.jcpt.trade.service.bo;

import java.io.Serializable;
import java.util.Date;

public class PhoneSupportOperatorBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2471886373661362644L;

	private Integer id;

	private String phoneModel;

	private String userNationCode;

	private Integer supportOperator;

	private String operatorStatus;

	private String remark;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getUserNationCode() {
		return userNationCode;
	}

	public void setUserNationCode(String userNationCode) {
		this.userNationCode = userNationCode;
	}

	public Integer getSupportOperator() {
		return supportOperator;
	}

	public void setSupportOperator(Integer supportOperator) {
		this.supportOperator = supportOperator;
	}

	public String getOperatorStatus() {
		return operatorStatus;
	}

	public void setOperatorStatus(String operatorStatus) {
		this.operatorStatus = operatorStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
