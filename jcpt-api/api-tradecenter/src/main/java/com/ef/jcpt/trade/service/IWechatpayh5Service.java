package com.ef.jcpt.trade.service;

import java.io.IOException;
import java.util.Map;

import com.ef.jcpt.trade.service.bo.SNSUserInfo;

public interface IWechatpayh5Service {

	// 微信进行首次链接验证确保正确的token
	public String toPay(Map<String, String> requestParams);

	// 点击支付时提交
	public Map<String, Object> submitwhchat(String paySn);

	// 获得code后进行第二次跳转
	public String toPay2(Map<String, String> requestParams) throws IOException;

	// 获取网页授权信息 appid 公众账号唯一标示 ，appsecret 公众账号的秘钥
	public SNSUserInfo getOauth2AccessToken(String Appid, String appSecret, String code);

	// 刷新网页授权凭证 refreshToken 刷新凭证
	public SNSUserInfo refreshOauth2AccessToken(String Appid, String refreshToken);

	// 获取用户信息 accessToken网页授权接口调用凭证，openid用户标示
	public SNSUserInfo getSNSUSerInfo(String accessToken, String openid);

	// 判断授权是否有效
	public String getisSq(String accessToken, String openid);

	// snsapi_base获得openid
	public String getopenIdbysnsapibase(String Appid, String appSecret, String code);

	/**
	 * 统一提交订单
	 * 
	 * @param paySn
	 * @param os
	 * @param openCode
	 * @param openId
	 * @param spbillIp
	 * @return
	 */
	public Map<String, String> unifiedOrder(String paySn, String os, String openCode, String openId, String spbillIp);

	/**
	 * 通过支付系统来源获取微信支付平台Key
	 * 
	 * @param os
	 * @return
	 */
	public String getKeyByOs(String os);
}
