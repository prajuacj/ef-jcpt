package com.ef.jcpt.wi.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.FrontH5DataConst;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.common.util.StringUtil;
import com.ef.jcpt.core.cache.CacheUtil;
import com.ef.jcpt.core.entity.TokenVo;
import com.ef.jcpt.core.sms.ToolSendSMSUtil;
import com.ef.jcpt.user.service.IUserService;
import com.ef.jcpt.user.service.bo.UserInfoBo;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	IUserService userServiceImpl;

	@Autowired
	private CacheUtil cacheUtil;

	@RequestMapping(value = "/regist.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> regist(HttpServletRequest req, String sign, String params) {
		String cmd = "UserController:regist";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		// Map<String, String> map = new HashMap<String, String>();

		try {
			BasicServiceModel<String> result = validateSign(sign, params);
			if (ReqStatusConst.FAIL.equals(result.getCode())) {
				logger.info(LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				UserInfoBo bo = JSON.parseObject(params, UserInfoBo.class);
				String reuslt = bo.validateFiled();
				if (StringUtil.isNotEmpty(reuslt)) {
					bsm = new BasicServiceModel<>(ReqStatusConst.INVALID_VALUE, reuslt);
					return bsm;
				} else {
					JSONObject jsonObj = JSONObject.parseObject(params);
					String mobile = bo.getMobile();
					String registValidCode = jsonObj.getString("registValidCode");
					String sessionValidCode = ToolSendSMSUtil
							.getAuthCodeByMem(FrontH5DataConst.FRONTH5_REGIST_VALIIDCODE_PREFIX + mobile);
					if ((null == sessionValidCode) || (!sessionValidCode.equals(registValidCode))) {
						bsm.setCode(ReqStatusConst.FAIL);
						bsm.setMsg("手机验证码错误！");
						return bsm;
					}
					Integer membercount = userServiceImpl.findMemberExistCount(bo.getUserName());
					if (membercount > 0) {
						bsm.setCode(ReqStatusConst.FAIL);
						bsm.setMsg("该用户已存在！");
						return bsm;
					} else {
						boolean isRegist = userServiceImpl.regist(bo);
						if (isRegist) {
							bsm.setCode(ReqStatusConst.OK);
							bsm.setMsg("注册成功！");
							return bsm;
						} else {
							bsm.setCode(ReqStatusConst.FAIL);
							bsm.setMsg("注册失败！");
							return bsm;
						}
					}
				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("注册失败！" + e.getMessage());
			return bsm;
		}
	}

	/**
	 * 獲取注册验证码
	 */
	@RequestMapping("/getRegistValidCode")
	@ResponseBody
	public BasicServiceModel<String> getRegistValidCode(@RequestParam(required = true, value = "phone") String phone) {
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		String sendCode = ToolSendSMSUtil.generateRandom6BitNumStr(6);
		boolean sendResult = ToolSendSMSUtil.sendSMS(phone, FrontH5DataConst.FRONTH5_REGIST_VALIIDCODE_CACHETIME,
				FrontH5DataConst.FRONTH5_REGIST_VALIIDCODE_PREFIX, sendCode);
		if (sendResult) {
			bsm.setCode(ReqStatusConst.OK);
			bsm.setMsg("注册验证短信发生成功！");
			return bsm;
		} else {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("注册验证短信发生失败！");
			return bsm;
		}

	}

	@RequestMapping("/login")
	public @ResponseBody BasicServiceModel<Map<String, Object>> login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "captcha", required = false) String captcha,
			@RequestParam(value = "remember_me", required = false, defaultValue = "1") String remember_me,
			@RequestParam(value = "appID", required = true) String appID, HttpServletRequest request) {
		BasicServiceModel<Map<String, Object>> result = new BasicServiceModel<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();/*
																 * 
																 * Subject subject = SecurityUtils.getSubject(); Session
																 * session = subject.getSession();
																 */
		if (null == username) {
			username = appID;
		}
		UserInfoBo member = new UserInfoBo();
		member.setUserName(username);

		try {
			boolean isLogin = userServiceImpl.login(username, password);
			if (isLogin) {
				TokenVo token = new TokenVo();
				token.setUser(member);
				String tokenKey = cacheUtil.setToken(token, username);
				map.put("token", tokenKey);
				map.put("member", member);
				result.setCode(ReqStatusConst.OK);
				result.setData(map);
				return result;
			} else {
				result.setCode(ReqStatusConst.FAIL);
				result.setMsg("登录失败，账户或者密码不正确！");
				return result;
			}
		} catch (Exception e) {
			result.setCode(ReqStatusConst.FAIL);
			result.setMsg("登录失败！" + e.getMessage());
			return result;
		}
	}

	@RequestMapping("/updatePwd")
	public @ResponseBody BasicServiceModel<String> updatePwd(
			@RequestParam(required = true, value = "username") String username,
			@RequestParam(required = true, value = "newPasswd") String newPasswd,
			@RequestParam(required = true, value = "mode") String mode,
			@RequestParam(required = false, value = "oldPassword") String oldPassword,
			@RequestParam(required = false, value = "validCode") String mobileCode) {
		BasicServiceModel<String> result = new BasicServiceModel<String>();

		UserInfoBo bo = userServiceImpl.findMemberExist(username);
		if (null != bo) {
			try {
				if (null != mode && mode.equals("1")) {
					// mode=1:验证旧密码是否正确
					boolean isLogin = userServiceImpl.login(username, oldPassword);
					if (!isLogin) {
						result.setCode(ReqStatusConst.FAIL);
						result.setMsg("修改失败，原密码不正确！");
						return result;
					}
				} else if (null != mode && mode.equals("2")) {
					// mode=2:验证手机验证码是否正确
					String sessionValidCode = ToolSendSMSUtil
							.getAuthCodeByMem(FrontH5DataConst.FRONTH5_REGIST_VALIIDCODE_PREFIX + username);
					if ((null == sessionValidCode) || (!sessionValidCode.equals(mobileCode))) {
						result.setCode(ReqStatusConst.FAIL);
						result.setMsg("修改失败，手机验证码错误！");
						return result;
					}
				}
				// 修改密码
				userServiceImpl.updatePwd(username, newPasswd);
				result.setCode(ReqStatusConst.OK);
				result.setData("修改密码成功");
				return result;
			} catch (Exception e) {
				// 账号验证未通过
				result.setCode(ReqStatusConst.FAIL);
				result.setMsg("修改失败！" + e.getMessage());
				return result;
			}
		} else {
			// 账号未注册
			result.setCode(ReqStatusConst.FAIL);
			result.setMsg("账号未注册！");
			return result;
		}
	}
}
