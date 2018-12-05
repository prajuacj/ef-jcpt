package com.ef.jcpt.manage.service.bo;

import java.io.Serializable;

public class MixJSBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2251127016394239909L;

	private String jsUrl;
	
	private int jsCount;

	public String getJsUrl() {
		return jsUrl;
	}

	public void setJsUrl(String jsUrl) {
		this.jsUrl = jsUrl;
	}

	public int getJsCount() {
		return jsCount;
	}

	public void setJsCount(int jsCount) {
		this.jsCount = jsCount;
	}
}
