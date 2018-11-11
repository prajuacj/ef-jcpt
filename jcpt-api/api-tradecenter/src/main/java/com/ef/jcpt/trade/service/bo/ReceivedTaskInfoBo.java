package com.ef.jcpt.trade.service.bo;

import java.io.Serializable;
import java.util.Date;

public class ReceivedTaskInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5186692088016939352L;

	private Integer taskId;

    private String receivedUser;

    private String taskStatus;

    private String validImgs;

    private Date finishedTime;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getReceivedUser() {
		return receivedUser;
	}

	public void setReceivedUser(String receivedUser) {
		this.receivedUser = receivedUser;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getValidImgs() {
		return validImgs;
	}

	public void setValidImgs(String validImgs) {
		this.validImgs = validImgs;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}
}
