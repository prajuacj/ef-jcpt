package com.ef.jcpt.trade.dao.model;

import java.util.Date;

public class PhoneSupportOperator {
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
        this.phoneModel = phoneModel == null ? null : phoneModel.trim();
    }

    public String getUserNationCode() {
        return userNationCode;
    }

    public void setUserNationCode(String userNationCode) {
        this.userNationCode = userNationCode == null ? null : userNationCode.trim();
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
        this.operatorStatus = operatorStatus == null ? null : operatorStatus.trim();
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