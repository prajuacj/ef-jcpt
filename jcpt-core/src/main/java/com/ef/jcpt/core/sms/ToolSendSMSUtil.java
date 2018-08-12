package com.ef.jcpt.core.sms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.common.util.CloudPropertiesUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

public class ToolSendSMSUtil {

	private static Logger logger = Logger.getLogger(ToolSendSMSUtil.class);

	private static ExecutorService authPool = Executors.newCachedThreadPool();

	private static Map<String, Object> authMap = new HashMap<String, Object>();

	private static int appId = CloudPropertiesUtil.getPropertyInt("wechat.sms.appid");

	private static String appkey = CloudPropertiesUtil.getProperty("wechat.sms.appkey");

	/**
	 * 发送验证码（用户注册）
	 * 
	 * @param phone
	 *            发送号码
	 * @param time
	 *            失效时间（分钟）
	 * @param prefix
	 *            业务前缀，防止同一手机号码在不同业务入口调用多次此方法而导致验证码被覆盖
	 * @return true：发送成功 false:发送失败
	 */
	public static boolean sendSMS(String phone, String time, String prefix, String content) {
		boolean sendRet = wechatSMS(phone, content);
		if (sendRet) {
			// 设置到内存中，用线程管理销毁该验证码
			putAuthCodeToMem(prefix + phone, content, Integer.valueOf(time));
			return true;
		} else {
			return false;
		}
	}

	public static boolean wechatSMS(String phone, String validCode) {
		String cmd = "ToolSendSMSUtil:sendSMS";
		try {
			SmsSingleSender ssender = new SmsSingleSender(appId, appkey);
			SmsSingleSenderResult result = ssender.send(0, "86", phone, validCode + "为您的登录验证码，请于3分钟内填写。如非本人操作，请忽略本短信。",
					"", "");
			logger.info(LogTemplate.genCommonSysLogStr(cmd, String.valueOf(result.result),
					result.errMsg + ",validCode=" + validCode));
			if (0 == result.result) {
				return true;
			} else {
				return false;
			}
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
			logger.error(LogTemplate.genCommonSysLogStr(cmd, e.getMessage(), e.getMessage()));
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
			logger.error(LogTemplate.genCommonSysLogStr(cmd, e.getMessage(), e.getMessage()));
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
			logger.error(LogTemplate.genCommonSysLogStr(cmd, e.getMessage(), e.getMessage()));
		}
		return false;
	}

	/**
	 * 生成随机6位数字字符串
	 * 
	 * @return
	 */
	public static String generateRandom6BitNumStr(int len) {
		String cnt = "";
		for (int i = 0; i < len; i++) {
			long val = Math.round(Math.random() * 1000);
			cnt += ((val) % 10);
		}
		return cnt;
	}

	/**
	 * 将验证码放入到内存，并通过线程管理销毁
	 * 
	 * @param phoneNum
	 *            发送的号码
	 * @param authCode
	 *            验证码
	 * @param ExperiterTime
	 *            失效时间
	 */
	public static void putAuthCodeToMem(final String phoneNum, String authCode, final int ExperiterTime) {
		authMap.put(phoneNum, authCode);
		logger.info("添加了" + phoneNum + "的验证码");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 设置几分钟后移除当前保存在Map中的手机验证码
					Thread.sleep(ExperiterTime * 60000);
					authMap.remove(phoneNum);
					logger.info("移除了" + phoneNum + "的验证码");
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
			}
		});
		authPool.execute(thread);
	}

	public static String getAuthCodeByMem(String phoneNum) {
		// 根据号码获取验证码， 是否存在两个同一个号码在一分钟内生成了两个验证码？？？这样就会覆盖了，导致业务失败的情况
		Object code = authMap.get(phoneNum);

		// 获取完成后该手机号的验证码就应该失效了，直接移除 ,此处放到验证成功后移除
		// authMap.remove(phoneNum);

		return (String) code;
	}

	/**
	 * 移除验证码
	 * 
	 * @param phoneNum
	 */
	public static void removeAuthCode(String phoneNum) {

		// 获取完成后该手机号的验证码就应该失效了，直接移除
		authMap.remove(phoneNum);

	}
}
