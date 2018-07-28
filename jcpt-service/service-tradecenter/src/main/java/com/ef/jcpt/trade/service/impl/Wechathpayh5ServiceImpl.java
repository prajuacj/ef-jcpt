package com.ef.jcpt.trade.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.trade.service.IOrderPayService;
import com.ef.jcpt.trade.service.IWechatpayh5Service;
import com.ef.jcpt.trade.service.bo.PayInfoBo;
import com.ef.jcpt.trade.wechat.h5.handler.GetWxOrderno;
import com.ef.jcpt.trade.wechat.h5.handler.HttpConnect;
import com.ef.jcpt.trade.wechat.h5.handler.HttpRespons;
import com.ef.jcpt.trade.wechat.h5.handler.MD5Util;
import com.ef.jcpt.trade.wechat.h5.handler.Sha1Util;
import com.ef.jcpt.trade.wechat.h5.handler.TenpayUtil;

@Service
public class Wechathpayh5ServiceImpl implements IWechatpayh5Service {

	private static final Logger log = LoggerFactory.getLogger(Wechathpayh5ServiceImpl.class);

	@Value("${wechat.pay.appid}")
	private String appid;

	@Value("${wechat.pay.appsecret}")
	private String appsecret;

	@Value("${wechat.pay.mch_id}")
	private String mch_id;

	@Value("${wechat.pay.notify_url}")
	private String notify_url;

	@Value("${wechat.pay.apikey}")
	private String apikey;

	@Autowired
	private IOrderPayService orderPayServiceImpl;

	@Override
	public Map<String, String> toPay(PayInfoBo payBo, String code, String ip) {
		// TODO Auto-generated method stub
		String openId = "";
		// 获取access_token跳转
		String URL = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appsecret
				+ "&js_code=" + code + "&grant_type=authorization_code";
		HttpRespons temp = HttpConnect.getInstance().doGetStr(URL);
		String tempValue = "";
		if (temp == null) {
			return null;
		} else {
			try {
				tempValue = temp.getStringResult();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			JSONObject jsonObj = JSONObject.parseObject(tempValue);
			if (jsonObj.containsKey("errcode")) {
				return null;
			}
			openId = jsonObj.getString("openid");
		}
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		// 随机数
		String nonce_str = strReq;
		// 商品描述
		// String body = describe;

		String trade_type = "JSAPI";
		String openid = openId;

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("device_info", "WEB");
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", "东流交易中心-" + payBo.getOrderContent());
		packageParams.put("attach", payBo.getUserId());
		packageParams.put("out_trade_no", payBo.getOrderId());
		packageParams.put("fee_type", "CNY");
		packageParams.put("total_fee", String.valueOf(payBo.getPayAmount()));
		packageParams.put("spbill_create_ip", ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openid);

		String sign = createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>"
				+ "<device_info>WEB</device_info>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign
				+ "</sign>" + "<sign_type>MD5</sign_type>" + "<body><![CDATA[" + "东流交易中心-" + payBo.getOrderContent()
				+ "]]></body>" + "<attach>" + payBo.getUserId() + "</attach>" + "<out_trade_no>" + payBo.getOrderId()
				+ "</out_trade_no>" + "<fee_type>CNY</fee_type>" + "<total_fee>" + String.valueOf(payBo.getPayAmount())
				+ "</total_fee>" + "<spbill_create_ip>" + ip + "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type + "</trade_type>" + "<openid>" + openid + "</openid>"
				+ "</xml>";

		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id = "";
		try {
			prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if (prepay_id.equals("")) {
				return null;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String appid2 = appid;
		String timestamp2 = Sha1Util.getTimeStamp();
		String nonceStr2 = nonce_str;
		String prepay_id2 = "prepay_id=" + prepay_id;
		String packages = prepay_id2;
		finalpackage.put("appId", appid2);
		finalpackage.put("timeStamp", timestamp2);
		finalpackage.put("nonceStr", nonceStr2);
		// packages=new String(packages.getBytes("ISO-8859-1"),"UTF-8");//转码
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		String finalsign = createSign(finalpackage);
		finalpackage.put("paySign", finalsign);
		return finalpackage;
	}

	public String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + apikey);
		// LogUtil.info("md5 sb:" + sb);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		// LogUtil.info("packge签名:" + sign);
		return sign;

	}
}
