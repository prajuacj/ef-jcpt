package com.ef.jcpt.wi.authen.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.core.cache.CacheUtil;
import com.ef.jcpt.core.entity.TokenVo;
import com.ef.jcpt.core.wechatpay.WXPayUtil;
import com.ef.jcpt.trade.service.IOrderPayService;
import com.ef.jcpt.trade.service.bo.OrderInfoBo;
import com.ef.jcpt.user.service.bo.UserInfoBo;

@Controller
@RequestMapping("/authen")
public class AuthenController extends BaseController {

	@Autowired
	IOrderPayService orderPayServiceImpl;

	@Autowired
	private CacheUtil cacheUtil;

	@RequestMapping(value = "/authentication.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> authentication(HttpServletRequest req, String sign, String params) {
		String cmd = "AuthenController:authentication";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			JSONObject jsonObj = JSONObject.parseObject(params);
			String tokenKey = jsonObj.getString("tokenKey");
			TokenVo token = cacheUtil.getToken(tokenKey);
			if (null != token) {
				UserInfoBo bo = token.getUser();
				String userName = bo.getMobile();

				String phoneModel = jsonObj.getString("mobileModel");
				String nationCode = jsonObj.getString("nationCode");
				return orderPayServiceImpl.listOperator(phoneModel, nationCode);
			} else {
				bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
				bsm.setMsg("会话已过期，请重新登录！");
				logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
				return bsm;
			}

		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取运营商信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}
}
