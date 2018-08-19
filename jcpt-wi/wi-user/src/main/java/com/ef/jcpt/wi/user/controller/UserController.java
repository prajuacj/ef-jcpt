package com.ef.jcpt.wi.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.ef.jcpt.user.service.bo.LoginBo;
import com.ef.jcpt.user.service.bo.UpdatePwdBo;
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
				logger.error(
						LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				UserInfoBo bo = JSON.parseObject(params, UserInfoBo.class);
				String reuslt = bo.validateFiled();
				if (StringUtil.isNotEmpty(reuslt)) {
					bsm = new BasicServiceModel<>(ReqStatusConst.INVALID_VALUE, reuslt);
					logger.error(
							LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
					return bsm;
				} else {
					JSONObject jsonObj = JSONObject.parseObject(params);
					String mobile = bo.getMobile();
					String registValidCode = jsonObj.getString("validCode");
					String sessionValidCode = ToolSendSMSUtil
							.getAuthCodeByMem(FrontH5DataConst.FRONTH5_REGIST_VALIIDCODE_PREFIX + mobile);
					if ((null == sessionValidCode) || (!sessionValidCode.equals(registValidCode))) {
						bsm.setCode(ReqStatusConst.FAIL);
						bsm.setMsg("手机验证码错误！");
						logger.error(
								LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
						return bsm;
					} else {
						ToolSendSMSUtil.removeAuthCode(mobile);
					}
					Integer membercount = userServiceImpl.findMemberExistCount(bo.getMobile());
					if (membercount > 0) {
						bsm.setCode(ReqStatusConst.FAIL);
						bsm.setMsg("该用户已存在！");
						logger.error(
								LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
						return bsm;
					} else {
						boolean isRegist = userServiceImpl.regist(bo);
						if (isRegist) {
							bsm.setCode(ReqStatusConst.OK);
							bsm.setMsg("注册成功！");
							logger.info(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(),
									bsm.getMsg() + ",data=" + params));
							return bsm;
						} else {
							bsm.setCode(ReqStatusConst.FAIL);
							bsm.setMsg("注册失败！");
							logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(),
									bsm.getMsg() + ",data=" + params));
							return bsm;
						}
					}
				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("注册失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	/**
	 * 獲取注册验证码
	 */
	@RequestMapping("/getRegistValidCode.json")
	@ResponseBody
	public BasicServiceModel<String> getRegistValidCode(HttpServletRequest req, String sign, String params) {
		String cmd = "UserController:getRegistValidCode";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			BasicServiceModel<String> result = validateSign(sign, params);
			if (ReqStatusConst.FAIL.equals(result.getCode())) {
				logger.error(
						LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				JSONObject jsonObj = JSONObject.parseObject(params);
				String phone = jsonObj.getString("phone");

				String sendCode = ToolSendSMSUtil.generateRandom6BitNumStr(6);
				logger.info("产生的验证码为：" + sendCode);
				boolean sendResult = ToolSendSMSUtil.sendSMS(phone,
						FrontH5DataConst.FRONTH5_REGIST_VALIIDCODE_CACHETIME,
						FrontH5DataConst.FRONTH5_REGIST_VALIIDCODE_PREFIX, sendCode);
				if (sendResult) {
					bsm.setCode(ReqStatusConst.OK);
					bsm.setMsg("注册验证短信发生成功！");
					logger.info(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
					return bsm;
				} else {
					bsm.setCode(ReqStatusConst.FAIL);
					bsm.setMsg("注册验证短信发生失败！");
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

	@RequestMapping("/login.json")
	public @ResponseBody BasicServiceModel<String> login(HttpServletRequest req, String sign, String params) {
		String cmd = "UserController:login";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		Map<String, Object> map = new HashMap<String, Object>();/*
																 * 
																 * Subject subject = SecurityUtils.getSubject(); Session
																 * session = subject.getSession();
																 */

		try {
			BasicServiceModel<String> result = validateSign(sign, params);
			if (ReqStatusConst.FAIL.equals(result.getCode())) {
				logger.error(
						LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				LoginBo bo = JSON.parseObject(params, LoginBo.class);
				String reuslt = bo.validateFiled();
				if (StringUtil.isNotEmpty(reuslt)) {
					bsm = new BasicServiceModel<>(ReqStatusConst.INVALID_VALUE, reuslt);
					logger.error(
							LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
					return bsm;
				} else {
					String userName = bo.getUserName();
					String loginType = bo.getLoginType();
					String password = bo.getPassword();

					BasicServiceModel<UserInfoBo> logBsm = userServiceImpl.login(userName, password, loginType);
					if ((null != logBsm) && (ReqStatusConst.OK.equals(logBsm.getCode()))) {
						UserInfoBo retBo = logBsm.getData();
						TokenVo token = new TokenVo();
						token.setUser(retBo);
						String tokenKey = cacheUtil.setToken(token, userName);
						map.put("tokenKey", tokenKey);
						map.put("member", retBo);
						String retStr = JSON.toJSONString(map);
						result.setCode(ReqStatusConst.OK);
						result.setData(retStr);
						return result;
					} else {
						result.setCode(ReqStatusConst.FAIL);
						if (null != logBsm) {
							result.setMsg(logBsm.getMsg());
						} else {
							result.setMsg("登录失败，账户或者密码不正确！");
						}
						return result;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("登录失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping("/updatePwd.json")
	public @ResponseBody BasicServiceModel<String> updatePwd(HttpServletRequest req, String sign, String params) {
		String cmd = "UserController:updatePwd";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			BasicServiceModel<String> result = validateSign(sign, params);
			if (ReqStatusConst.FAIL.equals(result.getCode())) {
				logger.error(
						LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				UpdatePwdBo bo = JSON.parseObject(params, UpdatePwdBo.class);
				String reuslt = bo.validateFiled();
				if (StringUtil.isNotEmpty(reuslt)) {
					bsm = new BasicServiceModel<>(ReqStatusConst.INVALID_VALUE, reuslt);
					logger.error(
							LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
					return bsm;
				} else {
					String mobile = bo.getMobile();
					String mobileCode = bo.getValidCode();
					String newPasswd = bo.getNewPasswd();

					UserInfoBo uio = userServiceImpl.findMemberExist(mobile);
					if (null != uio) {
						String sessionValidCode = ToolSendSMSUtil
								.getAuthCodeByMem(FrontH5DataConst.FRONTH5_REGIST_VALIIDCODE_PREFIX + mobile);
						if ((null == sessionValidCode) || (!sessionValidCode.equals(mobileCode))) {
							result.setCode(ReqStatusConst.FAIL);
							result.setMsg("修改失败，手机验证码错误！");
							logger.error(LogTemplate.genCommonSysLogStr(cmd, result.getCode(),
									result.getMsg() + ",data=" + params));
							return result;
						} else {
							ToolSendSMSUtil.removeAuthCode(mobile);
						}

						// 修改密码
						userServiceImpl.updatePwd(mobile, newPasswd);
						result.setCode(ReqStatusConst.OK);
						result.setData("修改密码成功");
						return result;

					} else {
						// 账号未注册
						result.setCode(ReqStatusConst.FAIL);
						result.setMsg("账号未注册！");
						logger.error(LogTemplate.genCommonSysLogStr(cmd, result.getCode(),
								result.getMsg() + ",data=" + params));
						return result;
					}
				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("修改密码失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	public static void main(String[] args) {
		ToolSendSMSUtil.wechatSMS("13076922539", "123987");
	}
}
