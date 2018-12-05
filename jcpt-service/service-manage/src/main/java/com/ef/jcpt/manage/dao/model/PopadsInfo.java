package com.ef.jcpt.manage.dao.model;

import java.util.Date;

public class PopadsInfo {
    private Integer id;

	private Integer modelId;

	private String modelName;

	private String taskName;

	private String taskDesc;

	private String taskImgs;

	private String taskUrl;

	private String taskStatus;

	private String publishUser;

	private String auditingUser;

	private String auditingAdvise;

	private String taskContent;

	private String publishPhone;

	private Integer popMode;

	private Integer execType;

	private Date validStartTime;

	private Date validEndTime;

	private Integer intervalTime;

	private String province;

	private String city;

	private String remark;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName == null ? null : modelName.trim();
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName == null ? null : taskName.trim();
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc == null ? null : taskDesc.trim();
	}

	public String getTaskImgs() {
		return taskImgs;
	}

	public void setTaskImgs(String taskImgs) {
		this.taskImgs = taskImgs == null ? null : taskImgs.trim();
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl == null ? null : taskUrl.trim();
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus == null ? null : taskStatus.trim();
	}

	public String getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser == null ? null : publishUser.trim();
	}

	public String getAuditingUser() {
		return auditingUser;
	}

	public void setAuditingUser(String auditingUser) {
		this.auditingUser = auditingUser == null ? null : auditingUser.trim();
	}

	public String getAuditingAdvise() {
		return auditingAdvise;
	}

	public void setAuditingAdvise(String auditingAdvise) {
		this.auditingAdvise = auditingAdvise == null ? null : auditingAdvise.trim();
	}

	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent == null ? null : taskContent.trim();
	}

	public String getPublishPhone() {
		return publishPhone;
	}

	public void setPublishPhone(String publishPhone) {
		this.publishPhone = publishPhone == null ? null : publishPhone.trim();
	}

	public Integer getPopMode() {
		return popMode;
	}

	public void setPopMode(Integer popMode) {
		this.popMode = popMode;
	}

	public Integer getExecType() {
		return execType;
	}

	public void setExecType(Integer execType) {
		this.execType = execType;
	}

	public Date getValidStartTime() {
		return validStartTime;
	}

	public void setValidStartTime(Date validStartTime) {
		this.validStartTime = validStartTime;
	}

	public Date getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
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