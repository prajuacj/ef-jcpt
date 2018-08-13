package com.ef.jcpt.core.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
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

	private static String smsSign = CloudPropertiesUtil.getProperty("wechat.sms.smssign");

	private static int templateId = CloudPropertiesUtil.getPropertyInt("wechat.sms.templateId");

	private final static String url = "https://yun.tim.qq.com/v3/tlssmssvr/sendsms";

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
		// sendTencentMsg("86", phone, content);
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
		String[] params = { validCode };
		// ArrayList<String> params = new ArrayList<String>(2);
		// params.add(0, validCode);
		// params.add(1, "3");
		try {
			SmsSingleSender ssender = new SmsSingleSender(appId, appkey);
			SmsSingleSenderResult result = ssender.sendWithParam("86", phone, templateId, params, smsSign, "", "");
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

	public static void sendTencentMsg(String nationCode, String phoneNumber, String content) {
		Random random = new Random();
		long rnd = random.nextInt(999999) % (999999 - 100000 + 1) + 100000;
		String wholeUrl = String.format("%s?sdkappid=%d&random=%d", url, appId, rnd);
		try {
			URL object = new URL(wholeUrl);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			JSONObject data = new JSONObject();
			JSONObject tel = new JSONObject();
			tel.put("nationcode", nationCode);
			String phone = phoneNumber;
			tel.put("phone", phone);
			data.put("type", "0");

			data.put("tpl_id", templateId);
			List<String> places = Arrays.asList(content, "3");
			data.put("params", places);

			String sig = stringMD5(appkey.concat(phone));
			data.put("sig", sig);
			data.put("tel", tel);
			data.put("sign", "东流科技");
			data.put("extend", "");
			data.put("ext", "");

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "utf-8");
			wr.write(data.toString());
			wr.flush();

			// 显示 POST 请求返回的内容
			StringBuilder sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "gbk"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				System.out.println("" + sb.toString());
			} else {
				System.out.println(con.getResponseMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String stringMD5(String input) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		byte[] inputByteArray = input.getBytes();
		messageDigest.update(inputByteArray);
		byte[] resultByteArray = messageDigest.digest();
		return byteArrayToHex(resultByteArray);
	}

	private static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}
}
