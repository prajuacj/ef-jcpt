package com.ef.jcpt.manage.dao.model;

import java.util.Date;

public class PhoneSupportStandard {
    private String phoneModel;

    private String mobileNetworkStandard;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel == null ? null : phoneModel.trim();
    }

    public String getMobileNetworkStandard() {
        return mobileNetworkStandard;
    }

    public void setMobileNetworkStandard(String mobileNetworkStandard) {
        this.mobileNetworkStandard = mobileNetworkStandard == null ? null : mobileNetworkStandard.trim();
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