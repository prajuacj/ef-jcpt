package com.ef.jcpt.trade.dao.model;

import java.util.Date;

public class ReceivedTask {
    private Integer id;

    private Integer taskId;

    private String receivedUser;

    private Date receivedTime;

    private String taskStatus;

    private String validImgs;

    private Date finishedTime;

    private String taskReward;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
        this.receivedUser = receivedUser == null ? null : receivedUser.trim();
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.trim();
    }

    public String getValidImgs() {
        return validImgs;
    }

    public void setValidImgs(String validImgs) {
        this.validImgs = validImgs == null ? null : validImgs.trim();
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getTaskReward() {
        return taskReward;
    }

    public void setTaskReward(String taskReward) {
        this.taskReward = taskReward == null ? null : taskReward.trim();
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