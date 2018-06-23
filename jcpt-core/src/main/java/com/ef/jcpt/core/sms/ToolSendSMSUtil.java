package com.ef.jcpt.core.sms;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

public class ToolSendSMSUtil {

	private static Logger logger = Logger.getLogger(ToolSendSMSUtil.class);

	// private static String username;
	// private static String password;
	// private static String APP_ID;
	// public static String MSG_VAL_TEMPLATEID;
	// public static String MSG_NOTICE_TEMPLATEID;
	// /**
	// * 任务预约
	// */
	// public static String JOB_APPOINTMENT;
	// /**
	// * 任务取消预约
	// */
	// public static String JOB_CANCEL_APPOINTMENT;
	// private static String appServer_url;
	// private static String appServer_port;
	//
	private static ExecutorService authPool = Executors.newCachedThreadPool();
	private static Map<String, Object> authMap = new HashMap<String, Object>();
	//
	// static
	// {
	// // 取出对应的参数值
	// username = (String) PropertiesPlugin.getParamMapValue(DictKeys.account_sid);
	// password = (String) PropertiesPlugin.getParamMapValue(DictKeys.AUTH_TOKEN);
	// APP_ID = (String) PropertiesPlugin.getParamMapValue(DictKeys.APP_ID);
	// appServer_url = (String)
	// PropertiesPlugin.getParamMapValue(DictKeys.appServer_url);
	// appServer_port = (String)
	// PropertiesPlugin.getParamMapValue(DictKeys.appServer_port);
	//// username = AESUtil.Decrypt((String)
	// PropertiesPlugin.getParamMapValue(DictKeys.account_sid));
	//// password = AESUtil.Decrypt((String)
	// PropertiesPlugin.getParamMapValue(DictKeys.AUTH_TOKEN));
	//// APP_ID = AESUtil.Decrypt((String)
	// PropertiesPlugin.getParamMapValue(DictKeys.APP_ID));
	// // 短信验证码
	// MSG_VAL_TEMPLATEID = (String)
	// PropertiesPlugin.getParamMapValue(DictKeys.MSG_VAL_TEMPLATEID);
	// MSG_NOTICE_TEMPLATEID = (String)
	// PropertiesPlugin.getParamMapValue(DictKeys.MSG_NOTICE_TEMPLATEID);
	// JOB_APPOINTMENT = (String)
	// PropertiesPlugin.getParamMapValue(DictKeys.JOB_APPOINTMENT);
	// JOB_CANCEL_APPOINTMENT = (String)
	// PropertiesPlugin.getParamMapValue(DictKeys.JOB_CANCEL_APPOINTMENT);
	// }

	/**
	 * 发送短信通知文本内容
	 * 
	 * @param phone
	 *            发送号码
	 * @param templateId
	 *            发送的模板id
	 * @param datas
	 *            发送的文本占位符内容
	 * @return true：发送成功 false:发送失败
	 */
	// public static boolean sendTextSMS(String phone, String templateId, String[]
	// datas)
	// {
	// HashMap<String, Object> result = null;
	// CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
	// restAPI.init(appServer_url, appServer_port);
	// restAPI.setAccount(username, password);
	// restAPI.setAppId(APP_ID);
	// result = restAPI.sendTemplateSMS(phone, templateId, datas);
	// if ("000000".equals(result.get("statusCode")))
	// {
	// // putAuthCodeToMem(phone, content, Integer.valueOf(time));
	// return true;
	// }
	// else
	// {
	// logger.info("错误码=" + result.get("statusCode") + " 错误信息= " +
	// result.get("statusMsg"));
	// return false;
	// }
	// }

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
		HashMap<String, Object> result = null;

		// 初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

		// ******************************注释*********************************************
		// *初始化服务器地址和端口 *
		// *沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
		// *生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883"); *
		// *******************************************************************************
		restAPI.init("app.cloopen.com", "8883");
		// restAPI.init(appServer_url, appServer_port);

		// ******************************注释*********************************************
		// *初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN *
		// *ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
		// *参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。 *
		// ******************8a48b5515335f736015336b0da3002c8*************************************************************
		restAPI.setAccount("8a48b5515335f736015336b0da3002c8", "39646588d91146b4a29099b4cbfbb0c1");
		// restAPI.setAccount(username, password);

		// ******************************注释*********************************************
		// *初始化应用ID *
		// *测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID *
		// *应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
		// **************aaf98f895388dae5015397db4de31616*****************************************************************
		restAPI.setAppId("aaf98f895388dae5015397db4de31616");
		// restAPI.setAppId(APP_ID);

		// ******************************注释****************************************************************
		// *调用发送模板短信的接口发送短信 *
		// *参数顺序说明： *
		// *第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号 *
		// *第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。 *
		// *系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
		// *第三个参数是要替换的内容数组。 *
		// **************************************************************************************************

		// **************************************举例说明***********************************************************************
		// *假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为 *
		// *result = restAPI.sendTemplateSMS("13800000000","1" ,new
		// String[]{"6532","5"}); *
		// *则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入 *
		// *********************************************************************************************************************
		// result = restAPI.sendTemplateSMS(phone, "1", new String[] { content, time });
		result = restAPI.sendTemplateSMS(phone, "75861", new String[] { content, time });
		// result = restAPI.sendTemplateSMS(phone, MSG_VAL_TEMPLATEID, new String[] {
		// content, time });

		System.out.println("SDKTestGetSubAccounts result=" + result);
		if ("000000".equals(result.get("statusCode"))) {
			// 设置到内存中，用线程管理销毁该验证码
			putAuthCodeToMem(prefix + phone, content, Integer.valueOf(time));
			return true;
		} else {
			// 异常返回输出错误码和错误信息
			logger.info("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			return false;
		}
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
