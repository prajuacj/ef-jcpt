package com.ef.jcpt.manage.service.bo;

import java.io.Serializable;

public class AdspopPublishBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -233462572345779911L;
	
	private String modelId;
	private String taskName;
	private String taskDesc;
	private String taskUrl;
	private String publishUser;
	private String publishPhone;
	private String popMode;
	private String remark;
	private String taskImageFilePath;
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
}
