package com.ef.jcpt.user.service.bo;

import java.io.Serializable;

import com.ef.jcpt.common.base.BaseBo;

public class UpdatePwdBo extends BaseBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9034314732291296856L;

	private String mobile;// 是 string 用户名
	private String newPasswd;// 是 string 新密码
	private String validCode;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNewPasswd() {
		return newPasswd;
	}
	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

}
