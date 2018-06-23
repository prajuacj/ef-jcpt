package com.ef.jcpt.core.entity;

import java.io.Serializable;

import com.ef.jcpt.user.service.bo.UserInfoBo;

public class TokenVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4654686748583291603L;

	private String tokenKey;

	private UserInfoBo user;

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public UserInfoBo getUser() {
		return user;
	}

	public void setUser(UserInfoBo user) {
		this.user = user;
	}
}
