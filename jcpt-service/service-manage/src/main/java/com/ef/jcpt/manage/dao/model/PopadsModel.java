package com.ef.jcpt.manage.dao.model;

import java.util.Date;

public class PopadsModel {
    private Integer id;

    private String modelName;

    private String modelDesc;

    private String modelContent;

    private String modelStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc == null ? null : modelDesc.trim();
    }

    public String getModelContent() {
        return modelContent;
    }

    public void setModelContent(String modelContent) {
        this.modelContent = modelContent == null ? null : modelContent.trim();
    }

    public String getModelStatus() {
        return modelStatus;
    }

    public void setModelStatus(String modelStatus) {
        this.modelStatus = modelStatus == null ? null : modelStatus.trim();
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