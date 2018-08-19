package com.ef.jcpt.user.service.bo;

import java.util.Date;

import com.ef.jcpt.common.base.BaseBo;

public class UserInfoBo extends BaseBo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6411950055608239019L;

	private String loginPassword;

	private String realName;

	private String certType;

	private String certCode;

	private String mobile;

	private String sex;

	private String mifiSerial;

	private Date createTime;

	private Date updateTime;

	private String country;
	
	private String nationCode;

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMifiSerial() {
		return mifiSerial;
	}

	public void setMifiSerial(String mifiSerial) {
		this.mifiSerial = mifiSerial;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
}
