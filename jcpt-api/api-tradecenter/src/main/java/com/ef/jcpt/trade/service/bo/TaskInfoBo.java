package com.ef.jcpt.trade.service.bo;

import java.io.Serializable;
import java.util.Date;

public class TaskInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1835889575396482704L;
	
	private String taskTitle;

    private String taskDesc;

    private String taskImgs;

    private String taskUrl;

    private String taskStatus;

    private Integer taskNum;

    private String rewardType;

    private Integer taskReward;

    private String publishUser;

    private String publishPhone;

    private Date publishTime;

    private String publishEmail;

    private String remark;

    private Date createTime;

    private Date updateTime;

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getTaskImgs() {
		return taskImgs;
	}

	public void setTaskImgs(String taskImgs) {
		this.taskImgs = taskImgs;
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public Integer getTaskReward() {
		return taskReward;
	}

	public void setTaskReward(Integer taskReward) {
		this.taskReward = taskReward;
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

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublishEmail() {
		return publishEmail;
	}

	public void setPublishEmail(String publishEmail) {
		this.publishEmail = publishEmail;
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
