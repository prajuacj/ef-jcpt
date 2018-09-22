package com.ef.jcpt.manage.service.bo;

import java.io.Serializable;
import java.util.Date;

public class OperatorInfoBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5896281912795959179L;

	private String nationCode;

    private String operatorName;

    private String networkStandard;

    private String remark;

    private Date createTime;

    private Date updateTime;

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getNetworkStandard() {
		return networkStandard;
	}

	public void setNetworkStandard(String networkStandard) {
		this.networkStandard = networkStandard;
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
