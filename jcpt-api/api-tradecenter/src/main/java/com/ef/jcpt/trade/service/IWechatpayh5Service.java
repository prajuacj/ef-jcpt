package com.ef.jcpt.trade.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ef.jcpt.trade.service.bo.PayInfoBo;
import com.ef.jcpt.trade.service.bo.SNSUserInfo;

public interface IWechatpayh5Service {

	// 微信进行首次链接验证确保正确的token
	public Map<String, String> toPay(PayInfoBo payBo, String code, String ip);
}
