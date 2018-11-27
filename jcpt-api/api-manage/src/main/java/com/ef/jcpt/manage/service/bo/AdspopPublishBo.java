package com.ef.jcpt.manage.service.bo;

import java.io.Serializable;

public class AdspopPublishBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -233462572345779911L;

	private int id;
	private String modelId;
	private String taskName;
	private String taskDesc;
	private String taskUrl;
	private String publishUser;
	private String publishPhone;
	private String popMode;
	private String validStartTime;
	private String validEndTime;
	private String intervalTime;
	private String province;
	private String city;
	private String remark;
	private String taskImageFilePath;
	private String taskImageFileName;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

	public String getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}

	public String getPublishPhone() {
		return publishPhone;
	}

	public void setPublishPhone(String publishPhone) {
		this.publishPhone = publishPhone;
	}

	public String getPopMode() {
		return popMode;
	}

	public void setPopMode(String popMode) {
		this.popMode = popMode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTaskImageFilePath() {
		return taskImageFilePath;
	}

	public void setTaskImageFilePath(String taskImageFilePath) {
		this.taskImageFilePath = taskImageFilePath;
	}

	public String getValidStartTime() {
		return validStartTime;
	}

	public void setValidStartTime(String validStartTime) {
		this.validStartTime = validStartTime;
	}

	public String getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(String validEndTime) {
		this.validEndTime = validEndTime;
	}

	public String getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskImageFileName() {
		return taskImageFileName;
	}

	public void setTaskImageFileName(String taskImageFileName) {
		this.taskImageFileName = taskImageFileName;
	}
}
