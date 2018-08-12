package com.ef.jcpt.user.dao.model;

import java.util.Date;

public class UserMifiRelation {
    private Integer id;

	private String userName;

	private String mifiSerial;

	private String useStatus;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getMifiSerial() {
		return mifiSerial;
	}

	public void setMifiSerial(String mifiSerial) {
		this.mifiSerial = mifiSerial == null ? null : mifiSerial.trim();
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus == null ? null : useStatus.trim();
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