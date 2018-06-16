package com.ef.jcpt.wi.user.controller;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.gofar.fnh.common.ErrorMsg;
import com.gofar.fnh.common.FrontH5DataConsts;
import com.gofar.fnh.common.FrontH5DataResult;
import com.gofar.fnh.member.utils.ToolSendSMSUtil;
import com.leimingtech.core.entity.TokenVo;
import com.leimingtech.core.entity.vo.CartVo;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@RequestMapping("/regist")
	@ResponseBody
	public BasicServiceModel<String> regist(@RequestParam(required = false, value = "mobileCode") String mobileCode,
			@RequestParam(required = false, value = "mobile") String mobile,
			@RequestParam(required = false, value = "password") String password,
			@RequestParam(required = false, value = "refereeId") String refereeId,
			@RequestParam(required = false, value = "goodsId") String goodsId,
			@RequestParam(required = false, value = "memberType") String memberType) {
		FrontH5DataResult<Map<String, String>> result = new FrontH5DataResult<Map<String, String>>();
		// Map<String, String> map = new HashMap<String, String>();

		Member member = new Member();
		try {
			// 手机注册
			member.setMemberMobile(mobile);
			member.setMemberPasswd(password);
			member.setMemberName(mobile);
			String sessionValidCode = ToolSendSMSUtil
					.getAuthCodeByMem(FrontH5DataConsts.FRONTH5_REGIST_VALIIDCODE_PREFIX + mobile);
			if ((null == sessionValidCode) || (!sessionValidCode.equals(mobileCode))) {
				ErrorMsg errMsg = new ErrorMsg();
				errMsg.setErrCode(FrontH5DataConsts.FRONTH5_REGIST_FAIL);
				errMsg.setErrMsg("手机验证码错误！");

				result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
				result.setReturnMsg(errMsg);
				return result;
			}
			Integer membercount = memberService.findMemberExistCount(member);
			if (membercount > 0) {
				ErrorMsg errMsg = new ErrorMsg();
				errMsg.setErrCode(FrontH5DataConsts.FRONTH5_REGIST_FAIL);
				errMsg.setErrMsg("该用户已存在");

				result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
				result.setReturnMsg(errMsg);
				return result;
			} else {
				member.setIndustryType(memberService.findUserType(mobile, refereeId));// 获取注册用户身份类别

				// 获取用户注册链接
				// StringBuffer requestURL = request.getRequestURL();//request请求url
				// String queryString = request.getQueryString();//request请求url的参数
				// requestURL.append("?"+queryString);//合成 url+?+参数 的完整格式

				member.setRefereeId(refereeId);// 推荐用户ID
				member.setGoodsId(goodsId);// 分享商品ID

				member.setMemberType((memberType != null && !memberType.equals("")) ? memberType : "appzc");// 默认会员类型为网站注册

				savemember(member);// 保存会员信息

				// 注册成功
				result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_SUCCESS);
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("用户注册失败", e.getMessage());

			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrCode(FrontH5DataConsts.FRONTH5_REGIST_FAIL);
			errMsg.setErrMsg("用户注册失败");

			result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
			result.setReturnMsg(errMsg);
			return result;
		}
	}

	/**
	 * 獲取注册验证码
	 */
	@RequestMapping("/getRegistValidCode")
	@ResponseBody
	public FrontH5DataResult<String> getRegistValidCode(@RequestParam(required = true, value = "phone") String phone) {
		FrontH5DataResult<String> result = new FrontH5DataResult<String>();
		String sendCode = ToolSendSMSUtil.generateRandom6BitNumStr(6);
		boolean sendResult = ToolSendSMSUtil.sendSMS(phone, FrontH5DataConsts.FRONTH5_REGIST_VALIIDCODE_CACHETIME,
				FrontH5DataConsts.FRONTH5_REGIST_VALIIDCODE_PREFIX, sendCode);
		if (sendResult) {
			result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_SUCCESS);
			return result;
		} else {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrCode(FrontH5DataConsts.FRONTH5RETURNFLAG_SMSSEND_FAIL);
			errMsg.setErrMsg("短信发送失败");

			result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
			result.setReturnMsg(errMsg);
			return result;
		}

	}

	@RequestMapping("/login")
	public @ResponseBody FrontH5DataResult<Map<String, Object>> login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "captcha", required = false) String captcha,
			@RequestParam(value = "remember_me", required = false, defaultValue = "1") String remember_me,
			@RequestParam(value = "appID", required = true) String appID, HttpServletRequest request) {
		FrontH5DataResult<Map<String, Object>> result = new FrontH5DataResult<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();/*
																 * 
																 * Subject subject = SecurityUtils.getSubject(); Session
																 * session = subject.getSession();
																 */
		if (null == username) {
			username = appID;
		}
		Member member = new Member();
		member.setMemberName(username);
		/*
		 * member = memberService.findMemberExist(member); if (member != null &&
		 * member.getMemberState() == 0) {// 判断会员是否有登陆权限 ErrorMsg errMsg=new ErrorMsg();
		 * errMsg.setErrCode(FrontH5DataConsts.FRONTH5_LOGIN_FAIL);
		 * errMsg.setErrMsg("该会员禁止登陆");
		 * 
		 * result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
		 * result.setReturnMsg(errMsg); return result;
		 * 
		 * }
		 */

		/*
		 * String sessionCaptcha = ToolSendSMSUtil.getAuthCodeByMem(username);
		 * 
		 * if (StringUtils.isNotEmpty(captcha) && !sessionCaptcha.equals(captcha)) {
		 * ErrorMsg errMsg=new ErrorMsg();
		 * errMsg.setErrCode(FrontH5DataConsts.FRONTH5_LOGIN_FAIL);
		 * errMsg.setErrMsg("验证码错误");
		 * 
		 * result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
		 * result.setReturnMsg(errMsg); return result; }
		 */
		// boolean isrmemberName = "1".equals(remember_me) ? true : false;
		/*
		 * String host = IpUtil.getIpAddr(request); UsernamePasswordToken token = new
		 * UsernamePasswordToken(username, password.toCharArray(), isrmemberName, host,
		 * captcha, false); try { if ("1".equals(remember_me)) {
		 * token.setRememberMe(true); } subject.login(token);
		 * CacheUtils.initCacheUser(subject.getPrincipal().toString()); } catch
		 * (UnknownAccountException e) { ErrorMsg errMsg=new ErrorMsg();
		 * errMsg.setErrCode(FrontH5DataConsts.FRONTH5_LOGIN_FAIL);
		 * errMsg.setErrMsg("用户名/密码错误");
		 * 
		 * result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
		 * result.setReturnMsg(errMsg); return result;
		 * 
		 * } catch (IncorrectCredentialsException e) { ErrorMsg errMsg=new ErrorMsg();
		 * errMsg.setErrCode(FrontH5DataConsts.FRONTH5_LOGIN_FAIL);
		 * errMsg.setErrMsg("用户名/密码错误");
		 * 
		 * result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
		 * result.setReturnMsg(errMsg); return result; } catch (Exception e) { //
		 * 其他错误，比如锁定，如果想单独处理请单独catch 处理 ErrorMsg errMsg=new ErrorMsg();
		 * errMsg.setErrCode(FrontH5DataConsts.FRONTH5_LOGIN_FAIL);
		 * errMsg.setErrMsg("登录异常/身份验证失败");
		 * 
		 * result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
		 * result.setReturnMsg(errMsg); return result; }
		 */
		try {
			// memberService.login(username, password, member.getMemberPasswd());
			// 保存toten信息
			member.setMemberId(appID);
			TokenVo token = new TokenVo();
			member.setMemberPasswd("");// 清空member中的密码
			token.setMember(member);
			// 删除此username绑定的token 当你在其他地方登陆后，前一个token失效
			cacheUtil.delTokenByUsername(username);
			String tokenKey = cacheUtil.setToken(token, username);
			map.put("token", tokenKey);
			map.put("member", member);
		} catch (Exception e) {
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrCode(FrontH5DataConsts.FRONTH5_LOGIN_FAIL);
			errMsg.setErrMsg("账号/密码错误！");
			result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
			result.setReturnMsg(errMsg);
			return result;
		}
		// 修改登陆者的登陆信息
		// memberService.updateweiMember(member.getMemberId());
		try {
			// 登录成功后,session中购物车数据存表
			CartVo cartVo = cacheUtil.getCart(appID);
			if (cartVo != null) {
				cartService.addLoginCart(cartVo, member.getMemberId());
			}
		} catch (Exception e) {

		}
		cacheUtil.delCart(appID);

		/**
		 * 登录环信通讯
		 */
		// EasemobIMUsers.loginWebIm(username, password);

		result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_SUCCESS);
		result.setReturnData(map);
		return result;
	}

	@RequestMapping("/upPassword")
	public @ResponseBody FrontH5DataResult<Map<String, Object>> upPassword(
			@RequestParam(required = true, value = "username") String username,
			@RequestParam(required = true, value = "newPasswd") String newPasswd,
			@RequestParam(required = true, value = "mode") String mode,
			@RequestParam(required = false, value = "oldPassword") String oldPassword,
			@RequestParam(required = false, value = "mobileCode") String mobileCode) {
		FrontH5DataResult<Map<String, Object>> result = new FrontH5DataResult<Map<String, Object>>();

		Member member = new Member();
		member.setMemberName(username);
		member = memberService.findMemberExist(member);
		if (member != null) {
			try {
				if (null != mode && mode.equals("1")) {
					// mode=1:验证旧密码是否正确
					memberService.login(username, oldPassword, member.getMemberPasswd());
				} else if (null != mode && mode.equals("2")) {
					// mode=2:验证手机验证码是否正确
					String sessionValidCode = ToolSendSMSUtil
							.getAuthCodeByMem(FrontH5DataConsts.FRONTH5_REGIST_VALIIDCODE_PREFIX + username);
					if ((null == sessionValidCode) || (!sessionValidCode.equals(mobileCode))) {
						ErrorMsg errMsg = new ErrorMsg();
						errMsg.setErrCode(FrontH5DataConsts.FRONTH5_UPPASSWORD_FAIL);
						errMsg.setErrMsg("手机验证码错误！");

						result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
						result.setReturnMsg(errMsg);
						return result;
					}
				}
				// 修改密码
				memberService.updatePass(newPasswd, member.getMemberId());
			} catch (Exception e) {
				// 账号验证未通过
				ErrorMsg errMsg = new ErrorMsg();
				errMsg.setErrCode(FrontH5DataConsts.FRONTH5_UPPASSWORD_FAIL);
				errMsg.setErrMsg(FrontH5DataConsts.getMsgMap().get(FrontH5DataConsts.FRONTH5_UPPASSWORD_FAIL));

				result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
				result.setReturnMsg(errMsg);
				return result;
			}
			result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_SUCCESS);
			return result;
		} else {
			// 账号未注册
			ErrorMsg errMsg = new ErrorMsg();
			errMsg.setErrCode(FrontH5DataConsts.FRONTH5_UPPASSWORD_FAIL);
			errMsg.setErrMsg("账号未注册");

			result.setReturnTag(FrontH5DataConsts.FRONTH5RETURNFLAG_FAIL);
			result.setReturnMsg(errMsg);
			return result;
		}
	}
}
