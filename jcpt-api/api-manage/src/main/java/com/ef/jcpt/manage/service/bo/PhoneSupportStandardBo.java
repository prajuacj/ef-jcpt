package com.ef.jcpt.manage.service.bo;

import java.io.Serializable;
import java.util.Date;

public class PhoneSupportStandardBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3948341389535376180L;

	private String phoneModel;

    private String mobileNetworkStandard;

    private String remark;

    private Date createTime;

    private Date updateTime;

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getMobileNetworkStandard() {
		return mobileNetworkStandard;
	}

	public void setMobileNetworkStandard(String mobileNetworkStandard) {
		this.mobileNetworkStandard = mobileNetworkStandard;
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
