package com.ef.jcpt.user.service.bo;

import java.io.Serializable;

import com.ef.jcpt.common.base.BaseBo;

public class LoginBo extends BaseBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8924659955941881749L;
	private String userName;// 是 string 用户名
	private String password;// 是 string 密码
	private String captcha;// 是 string 短信验证码
	private String rememberMe;// 是 string 是否记住我（1：记住）
	private String loginType;
	
	private String phoneModel;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
}
