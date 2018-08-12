package com.ef.jcpt.wi.trade.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.core.cache.CacheUtil;
import com.ef.jcpt.core.entity.TokenVo;
import com.ef.jcpt.user.service.bo.UserInfoBo;

@Controller
@RequestMapping("/orderTrade")
public class OrderTradeController extends BaseController {

	@Autowired
	private CacheUtil cacheUtil;

	@RequestMapping("/toPay.json")
	@ResponseBody
	public BasicServiceModel<String> toPay(HttpServletRequest req, String sign, String params) {
		String cmd = "OrderTradeController:toPay";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			BasicServiceModel<String> result = validateSign(sign, params);
			if (ReqStatusConst.FAIL.equals(result.getCode())) {
				logger.error(
						LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				JSONObject jsonObj = JSONObject.parseObject(params);
				String tokenKey = jsonObj.getString("token");
				TokenVo token = cacheUtil.getToken(tokenKey);
				if (null != token) {
					UserInfoBo bo = token.getUser();
					String bo.getUserName();
				} else {
					bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
					bsm.setMsg("会话已过期，请重新登录！");
					logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
					return bsm;
				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("注册失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping("/listOrder.json")
	@ResponseBody
	public BasicServiceModel<String> listOrder(@RequestParam(required = true, value = "userId") String userId) {
		return null;
	}
}
