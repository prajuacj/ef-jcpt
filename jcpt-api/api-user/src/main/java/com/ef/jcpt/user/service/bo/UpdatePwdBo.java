package com.ef.jcpt.user.service.bo;

import java.io.Serializable;

import com.ef.jcpt.common.base.BaseBo;

public class UpdatePwdBo extends BaseBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9034314732291296856L;

	private String userName;// 是 string 用户名
	private String oldPassword;// 是 string 原先密码
	private String newPasswd;// 是 string 新密码
	private String mode;// 是 string 是否验证手机号（1:验证旧密码是否正确；2:验证手机验证码是否正确）
	private String validCode;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPasswd() {
		return newPasswd;
	}

	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

}
