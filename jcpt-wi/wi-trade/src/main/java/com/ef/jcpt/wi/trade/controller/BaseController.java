package com.ef.jcpt.wi.trade.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ef.jcpt.common.entity.BasicServiceModel;

public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public BasicServiceModel<String> validateSign(String sign, String params) {
		BasicServiceModel<String> result = new BasicServiceModel<String>();
		// if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(params)) {
		// result.setCode(ReqStatusConst.FAIL);
		// result.setErrorCode(ReqStatusConst.VALUE_NOT_NULL);
		// result.setMsg("参数为空");
		// }else{
		// String newStr = DigestUtils.md5Hex(SecurityPropertiesConfig.ACCOUNT_MD5_KEY
		// + params + SecurityPropertiesConfig.ACCOUNT_MD5_KEY);
		// if (!sign.equalsIgnoreCase(newStr)) {
		// result.setCode(ReqStatusConst.FAIL);
		// result.setErrorCode(ReqStatusConst.SIGN_CHECK_FAIL);
		// result.setMsg("MD5验证失败");
		// }
		// }
		return result;
	}

	public String getFullURL(HttpServletRequest request) throws UnsupportedEncodingException {
		StringBuffer url = request.getRequestURL();
		if (request.getQueryString() != null) {
			url.append("?");
			url.append(request.getQueryString());
		} else {
			url.append("?");
			StringBuffer params = new StringBuffer();
			setRequestToParamMap(request, params);
			url.append(params);
		}
		return url.toString();
	}

	protected void setRequestToParamMap(HttpServletRequest request, StringBuffer params)
			throws UnsupportedEncodingException {
		Enumeration<String> keyNames = request.getParameterNames();
		while (keyNames.hasMoreElements()) {
			String attrName = keyNames.nextElement();
			if (request.getParameter(attrName) != "") {
				params.append(attrName + "=").append(URLDecoder.decode(request.getParameter(attrName), "utf-8"))
						.append("&");
			}
		}
	}
}
