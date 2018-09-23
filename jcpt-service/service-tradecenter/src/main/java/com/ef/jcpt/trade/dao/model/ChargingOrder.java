package com.ef.jcpt.trade.dao.model;

import java.util.Date;

public class ChargingOrder {
    private Integer id;

    private String payFlow;

    private String orderId;

    private Integer operatorId;

    private String operatorName;

    private String operatorNationCode;

    private String deviceId;

    private String userName;

    private String phoneModel;

    private Integer initFlow;

    private Integer usedFlow;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date updateTime;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayFlow() {
        return payFlow;
    }

    public void setPayFlow(String payFlow) {
        this.payFlow = payFlow == null ? null : payFlow.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public String getOperatorNationCode() {
        return operatorNationCode;
    }

    public void setOperatorNationCode(String operatorNationCode) {
        this.operatorNationCode = operatorNationCode == null ? null : operatorNationCode.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel == null ? null : phoneModel.trim();
    }

    public Integer getInitFlow() {
        return initFlow;
    }

    public void setInitFlow(Integer initFlow) {
        this.initFlow = initFlow;
    }

    public Integer getUsedFlow() {
        return usedFlow;
    }

    public void setUsedFlow(Integer usedFlow) {
        this.usedFlow = usedFlow;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}