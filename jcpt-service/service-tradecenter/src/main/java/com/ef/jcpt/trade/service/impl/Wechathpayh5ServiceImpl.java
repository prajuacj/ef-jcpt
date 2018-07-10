package com.ef.jcpt.trade.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import com.ef.jcpt.trade.service.IWechatpayh5Service;
import com.ef.jcpt.trade.service.bo.SNSUserInfo;
import com.gofar.pay.unionopay.pc.sdk.LogUtil;
import com.google.common.collect.Maps;
import com.leimingtech.core.entity.SNSUserINfo;
import com.leimingtech.core.entity.WeiXinOauth2Token;
import com.leimingtech.core.entity.base.Pay;
import com.leimingtech.service.module.trade.common.PaymentTallyState;
import com.payment.core.ComContents;
import com.payment.core.wechat.h5.config.WXMallPayConfig;
import com.payment.core.wechat.h5.config.WachatContent;
import com.payment.core.wechat.h5.config.WXPayConstants.SignType;
import com.payment.core.wechat.h5.handler.GetWxOrderno;
import com.payment.core.wechat.h5.handler.HttpConnect;
import com.payment.core.wechat.h5.handler.HttpRespons;
import com.payment.core.wechat.h5.handler.RequestHandler;
import com.payment.core.wechat.h5.handler.Sha1Util;
import com.payment.core.wechat.h5.handler.TenpayUtil;
import com.payment.core.wechat.h5.util.WXPay;
import com.payment.core.wechat.h5.util.WXPayUtil;
import com.payment.core.wechat.h5.util.WeixinUtil;

import net.sf.json.JSONObject;

public class Wechathpayh5ServiceImpl implements IWechatpayh5Service {

	@Override
	public String toPay(Map<String, String> requestParams) {
		// TODO Auto-generated method stub
		// 微信加密签名  
	    String signature = request.getParameter("signature");  
	    // 时间戳  
	    String timestamp = request.getParameter("timestamp");  
	    // 随机数  
	    String nonce = request.getParameter("nonce");  
	    // 随机字符串  
	    String echostr = request.getParameter("echostr");

	    //Member member  = null;
	    
	    PrintWriter out = null; 
	    if(StringUtils.isNotEmpty(timestamp)){
	    	try {
		        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
				request.setCharacterEncoding("UTF-8");
		        response.setCharacterEncoding("UTF-8"); 
		        
		        out = response.getWriter();
		        if (WeixinUtil.checkSignature(signature, timestamp, nonce)) {
		        	out.print(echostr);
		        }
			} catch (Exception e) {
				LogUtil.writeErrorLog(e.getMessage(), e);
			}finally{ 
		        out.close();
			}
	    }
	}

	@Override
	public Map<String, Object> submitwhchat(String paySn) {
		// TODO Auto-generated method stub
		Map<String,Object> map=Maps.newHashMap();
		try {
			//应用ID
			String appid = WachatContent.appid;
			String backUri = ComContents.payserviceurl+WachatContent.backUri;

			// 授权后要跳转的链接所需的参数一般有会员号，金额，订单号之类，
			// 最好自己带上一个加密字符串将金额加上一个自定义的key用MD5签名或者自己写的签名,
			// 比如 Sign = %3D%2F%CS%
			Pay orderPay = payService.findPayBySn(paySn);
			if(orderPay!=null){
				//String order=new String("订单号:".getBytes(),"UTF-8");
				String order="订单号:";
				backUri = backUri + "?userId=" + "" + "&orderId=" + orderPay.getPaySn() + "&goodstype="+"7"+"&goodsname="+order+orderPay.getPaySn()+"&supportvalue="+orderPay.getPayAmount();
				// URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
				backUri = URLEncoder.encode(backUri , "UTF-8");
				// scope 参数视各自需求而定，这里用scope=snsapi_base
				// 不弹出授权页面直接授权目的只获取统一支付接口的openid
				String url = WachatContent.oauth2 + "appid="+appid+ "&redirect_uri=" + backUri + "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
				//HttpConnect.getInstance().doGetStr(url);
//				System.out.println(url);
				
				map.put("code",1);
				map.put("msg",url);
			}
			// response.sendRedirect(url);
			}catch(Exception e) {
				LogUtil.writeErrorLog(e.getMessage(), e);
//				result.setResultCode("0");
//				result.setResultMsg("服务器端异常!");
				map.put("code",0);
				map.put("msg","服务器端异常!");
		}
		return map;
	}

	@Override
	public String toPay2(Map<String, String> requestParams) throws IOException {
		// TODO Auto-generated method stub
		// 判断是否微信环境, 5.0 之后的支持微信支付
				boolean isweixin = isWeiXin(request);
				if (isweixin) {
					//应用ID
					String appid = WachatContent.appid;
					//应用密钥
					String appsecret = WachatContent.appsecret;
					//微信支付商户号
					String partner = WachatContent.partner;
					//API密钥，在商户平台设置
					String apikey = WachatContent.apikey;
					
					String userId=request.getParameter("userId");
					String orderId=request.getParameter("orderId");
					String goodsType=request.getParameter("goodsType");//物品类型
					String supportvalue=request.getParameter("supportvalue");//物品价值
					String goodsname=request.getParameter("goodsname");//物品名称
					//String sendpay="1";//运费
					if(StringUtils.isEmpty(goodsname)){
						goodsname = goodsType;
					}
					String code = request.getParameter("code");
					//金额转化为分为单位
					float sessionmoney = Float.parseFloat(supportvalue);
					String finalmoney = String.format("%.2f", sessionmoney);
					int i = finalmoney.indexOf(".");
					int pay = 0;
					if(Integer.parseInt(finalmoney.substring(i+1,finalmoney.length()))==0){
						pay = Integer.parseInt(finalmoney.substring(0, i))*100;
					}else{
						pay = Integer.parseInt(finalmoney.replace(".", ""));
					}
					String total_fee = String.valueOf(pay);
					
					String openId ="";
					//获取access_token跳转
					String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?token=111&appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
					HttpRespons temp = HttpConnect.getInstance().doGetStr(URL);		
					String tempValue="";
					if( temp == null){
			//		    response.sendRedirect("/weChatpay/error.jsp");
						response.sendRedirect(ComContents.payserviceurl+"/cart/payerror");
					    return;
					}else
					{
						try {
							tempValue = temp.getStringResult();
						} catch (Exception e) {
							LogUtil.writeErrorLog(e.getMessage(), e);
						}
						JSONObject  jsonObj = JSONObject.fromObject(tempValue);
						
						String requestPath = request.getRequestURI() + "?" + request.getQueryString();
						if (requestPath.indexOf("&") > -1) {// 去掉其他参数
							requestPath = requestPath.substring(0, requestPath.indexOf("&"));
						}
						requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
						////LogUtil.info(requestPath);
						if(jsonObj.containsKey("errcode")){
							////LogUtil.info(tempValue);
			//				response.sendRedirect("/weChatpay/error.jsp");
							response.sendRedirect(ComContents.payserviceurl+"/cart/payerror");
							return;
						}
						if(!jsonObj.containsKey("openid")){
							////LogUtil.info("openid 出现null");
						}
							openId = jsonObj.getString("openid");
					}
					String currTime = TenpayUtil.getCurrTime();
					//8位日期
					String strTime = currTime.substring(8, currTime.length());
					//四位随机数
					String strRandom = TenpayUtil.buildRandom(4) + "";
					//10位序列号,可以自行调整。
					String strReq = strTime + strRandom;
					//商户号
					String mch_id = partner;
					//子商户号  非必须
			//		String sub_mch_id="";
					//设备号   非必须
			//		String device_info="";
					//随机数 
					String nonce_str = strReq;
					//商品描述
			//		String body = describe;
					
					//商品描述根据情况修改
			//		String body = "美食";
					//附加数据
			//		String attach = userId;
					//商户订单号
//					String out_trade_no = orderNo;
					//商品金额
//					int intMoney = Integer.parseInt(finalmoney);
					//总金额以分为单位，不带小数点
//					String total_fee = String.valueOf(intMoney);
					//订单生成的机器 IP
//					String spbill_create_ip = request.getRemoteAddr();
					String spbill_create_ip = getIp(request);
					int j = spbill_create_ip.indexOf(",");
					spbill_create_ip = spbill_create_ip.substring(j+1).trim();
					
//					if (spbill_create_ip.equals("0:0:0:0:0:0:0:1")) {
//						spbill_create_ip = "127.0.0.1";
//					}
					
					//订 单 生 成 时 间   非必须
			//		String time_start ="";
					//订单失效时间      非必须
			//		String time_expire = "";
					//商品标记   非必须
			//		String goods_tag = "";
					
					//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
					String notify_url =ComContents.payserviceurl+WachatContent.updateorderstate;
					
					
					String trade_type = "JSAPI";
					String openid = openId;
					//LogUtil.info(openid);
					//非必需
			//		String product_id = "";
					SortedMap<String, String> packageParams = new TreeMap<String, String>();
					packageParams.put("appid", appid);  
					packageParams.put("mch_id", mch_id);  
					packageParams.put("nonce_str", nonce_str);  
					packageParams.put("body", goodsname);  
					packageParams.put("attach", userId);  
					packageParams.put("out_trade_no", orderId);  
			//		
			//		
					//这里写的金额为string型
					packageParams.put("total_fee", total_fee);  
					packageParams.put("spbill_create_ip", spbill_create_ip);  
					packageParams.put("notify_url", notify_url);  
					
					packageParams.put("trade_type", trade_type);  
					packageParams.put("openid", openid);  
			
					RequestHandler reqHandler = new RequestHandler(request, response);
					reqHandler.init(appid, appsecret, apikey);
					
					String sign = reqHandler.createSign(packageParams);
					String xml="<xml>"+
							"<appid>"+appid+"</appid>"+
							"<mch_id>"+mch_id+"</mch_id>"+
							"<nonce_str>"+nonce_str+"</nonce_str>"+
							"<sign>"+sign+"</sign>"+
							"<body><![CDATA["+goodsname+"]]></body>"+
							"<attach>"+userId+"</attach>"+
							"<out_trade_no>"+orderId+"</out_trade_no>"+
							//金额，这里写的1 分到时修改
//							"<total_fee>"+1+"</total_fee>"+
							"<total_fee>"+total_fee+"</total_fee>"+
							"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
							"<notify_url>"+notify_url+"</notify_url>"+
							"<trade_type>"+trade_type+"</trade_type>"+
							"<openid>"+openid+"</openid>"+
							"</xml>";
					//LogUtil.info(xml);
//					String allParameters = "";
//					try {
//						allParameters =  reqHandler.genPackage(packageParams);
//						//LogUtil.info(allParameters);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
					String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
					String prepay_id="";
					try {
						prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
						if(prepay_id.equals("")){
							request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
			//				response.sendRedirect("/weChatpay/error.jsp");
							response.sendRedirect(ComContents.payserviceurl+"/cart/payerror");
//							System.out.println("*****a3&&&&&&&&&&:"+prepay_id);
							return;
						}
					} catch (Exception e1) {
						LogUtil.writeErrorLog(e1.getMessage(), e1);
					}
					SortedMap<String, String> finalpackage = new TreeMap<String, String>();
					String appid2 = appid;
					String timestamp2 = Sha1Util.getTimeStamp();
					String nonceStr2 = nonce_str;
					String prepay_id2 = "prepay_id="+prepay_id;
					String packages = prepay_id2;
					finalpackage.put("appId", appid2);  
					finalpackage.put("timeStamp", timestamp2);  
					finalpackage.put("nonceStr", nonceStr2);  
					//packages=new String(packages.getBytes("ISO-8859-1"),"UTF-8");//转码
					finalpackage.put("package", packages);
					finalpackage.put("signType", "MD5");
					String finalsign = reqHandler.createSign(finalpackage);
					String url=ComContents.payserviceurl+"/weChat/pay.html?appId="+appid2+"&timeStamp="+timestamp2+"&nonceStr="+nonceStr2+"&packageValue="+packages+"&paySign="+finalsign;
					//LogUtil.info(url);
					response.sendRedirect(url);
			//		response.sendRedirect("front/orderController.do?confirm&appid="+appid2+"&timeStamp="+timestamp2+"&nonceStr="+nonceStr2+"&package="+packages+"&sign="+finalsign);
				}else{
					response.getWriter().write("<script language='javascript'>alert('您的微信不是5.0以上版本，无法使用微信支付！');history.go(-1);</script>");
				}
	}
	
	/**
     * 判断是否来自微信, 5.0 之后的支持微信支付
     *
     * @param request
     * @return
     */
    public static boolean isWeiXin(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isNotBlank(userAgent)) {
            Pattern p = Pattern.compile("MicroMessenger/(\\d+).+");
            Matcher m = p.matcher(userAgent);
            String version = null;
            if (m.find()) {
                version = m.group(1);
            }
            return (null != version && NumberUtils.toInt(version) >= 5);
        }
        return false;
    }
    
    /**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		if (request == null)
			return "";
		String ip = request.getHeader("X-Requested-For");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@Override
	public SNSUserInfo getOauth2AccessToken(String Appid, String appSecret, String code) {
		// TODO Auto-generated method stub
		WeiXinOauth2Token wat=null;
		SNSUserINfo snsUserINfo=null;
		String requestUrl=WachatContent.getaccesstoken+"appid="+Appid+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
		//获取网页授权凭证
		JSONObject jsonobject=WeixinUtil.httpRequest(requestUrl,"GET",null);
		if(jsonobject!=null){
			try{
			wat=new WeiXinOauth2Token();
			wat.setAccessToken(jsonobject.getString("access_token"));//网页授权接口调用凭证
			wat.setExpriesIn(jsonobject.getInt("expires_in"));//access_token接口调用凭证超时时间，单位（秒）
			wat.setRefreshToken(jsonobject.getString("refresh_token"));//用户刷新access_token
			wat.setOpenId(jsonobject.getString("openid"));//用户唯一标识
			wat.setScope(jsonobject.getString("scope"));//用户授权的作用域，使用逗号（,）分隔
			}catch(Exception e){
				wat=null;
				int errorCode=jsonobject.getInt("errcode");
				String errorMsg=jsonobject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode,errorMsg);
			}	
		}
		if(wat!=null){
			snsUserINfo=getSNSUSerInfo(wat.getAccessToken(),wat.getOpenId());
		}
		return snsUserINfo;
	}

	@Override
	public SNSUserInfo refreshOauth2AccessToken(String Appid, String refreshToken) {
		// TODO Auto-generated method stub
		WeiXinOauth2Token wat=null;
		SNSUserINfo snsUserINfo=null;
		String requestUrl=WachatContent.refreshtoken+"appid="+Appid+"&grant_type=refresh_token&refresh_token="+refreshToken;
		//获取网页授权凭证
		JSONObject jsonobject=WeixinUtil.httpRequest(requestUrl,"GET",null);
		if(jsonobject!=null){
			try{
			wat=new WeiXinOauth2Token();
			wat.setAccessToken(jsonobject.getString("access_token"));//网页授权接口调用凭证
			wat.setExpriesIn(jsonobject.getInt("expires_in"));//access_token接口调用凭证超时时间，单位（秒）
			wat.setRefreshToken(jsonobject.getString("refresh_token"));//用户刷新access_token
			wat.setOpenId(jsonobject.getString("openid"));//用户唯一标识
			wat.setScope(jsonobject.getString("scope"));//用户授权的作用域，使用逗号（,）分隔
			}catch(Exception e){
				wat=null;
				int errorCode=jsonobject.getInt("errcode");
				String errorMsg=jsonobject.getString("errmsg");
				log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}", errorCode,errorMsg);
			}	
		}
		if(wat!=null){
			snsUserINfo=getSNSUSerInfo(wat.getAccessToken(),wat.getOpenId());
		}
		return snsUserINfo;
	}

	@Override
	public SNSUserInfo getSNSUSerInfo(String accessToken, String openid) {
		// TODO Auto-generated method stub
		SNSUserINfo snsUserInfo=null;
		//拼接链接地址
		String requestUrl=WachatContent.getuserifo+"access_token="+accessToken+"&openid="+openid+"&lang=zh_CN";
		JSONObject jsonobject=WeixinUtil.httpRequest(requestUrl,"GET",null);
		if(jsonobject!=null){
			try{
				snsUserInfo=new SNSUserINfo();
				snsUserInfo.setOpenId(jsonobject.getString("openid"));//用户的标示
				snsUserInfo.setNickname(jsonobject.getString("nickname"));//昵称
				snsUserInfo.setSex(jsonobject.getInt("sex"));//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
				snsUserInfo.setCountry(jsonobject.getString("country"));//国家，如中国为CN
				snsUserInfo.setProvince(jsonobject.getString("province"));//用户个人资料填写的省份
				snsUserInfo.setCity(jsonobject.getString("city"));//普通用户个人资料填写的城市
				snsUserInfo.setHeadImgUrl(jsonobject.getString("headimgurl"));//用户头像
				snsUserInfo.setUnionid(jsonobject.getString("unionid"));//如果 用户绑定用户将公众号绑定到微信开放平台帐号后，才会出现该字段
				//snsUserInfo.setPrivilegeList(JSONArray.toList(jsonobject.getJSONArray(jsonobject.getString("privilegeList")),List.class));
			}catch(Exception e){
				snsUserInfo=null;
				int errorCode=jsonobject.getInt("errcode");
				String errorMsg=jsonobject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode,errorMsg);
			}
		}
		return snsUserInfo;
	}

	@Override
	public String getisSq(String accessToken, String openid) {
		// TODO Auto-generated method stub
		String requestUrl=WachatContent.istoken+"="+accessToken+"&openid="+openid;
		JSONObject jsonobject=WeixinUtil.httpRequest(requestUrl,"GET",null);
		return jsonobject;
	}

	@Override
	public String getopenIdbysnsapibase(String Appid, String appSecret, String code) {
		// TODO Auto-generated method stub
		String requestUrl=WachatContent.getaccesstoken+"appid="+Appid+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
		String openid="";
		//获取网页授权凭证
		JSONObject jsonobject=WeixinUtil.httpRequest(requestUrl,"GET",null);
		if(jsonobject!=null && jsonobject.containsKey("openid")){
			openid=jsonobject.getString("openid");
		}
		return openid;
	}

	@Override
	public Map<String, String> unifiedOrder(String paySn, String os, String openCode, String openId, String spbillIp) {
		// TODO Auto-generated method stub
		Map<String, String> result = new HashMap<String, String>();
		try{
			Pay pay = payService.findPayBySn(paySn);
			if (pay != null) {
				paymentTallyService.savePaymentTally(os.equals(WachatContent.PAYMENT_OS_APP) ? "mp_wechatpay" : os.equals(WachatContent.PAYMENT_TRADE_TYPE_MWEB)? "h5_wechat" : "public_wechat", "微信支付", pay, os.equals(WachatContent.PAYMENT_OS_APP) ? PaymentTallyState.PAYMENTTALLY_TREM_MB : PaymentTallyState.PAYMENTTALLY_TREM_H5);
				// String order=new String("订单号:".getBytes(),"UTF-8");
				String orderId = pay.getPaySn();
				String notifyurl = WachatContent.getPropertyValue(WachatContent.WXPAY_NOTIFYURL_PREFIX + (os.equals(WachatContent.PAYMENT_TRADE_TYPE_MWEB) ? WachatContent.PAYMENT_OS_WBMALL : os));
				BigDecimal payAmount = pay.getPayAmount();
				//乘100
		        BigDecimal v2 = new BigDecimal("100");
		        int totalFee = payAmount.multiply(v2).intValue();
				WXMallPayConfig config = new WXMallPayConfig(os.equals(WachatContent.PAYMENT_TRADE_TYPE_MWEB) ? WachatContent.PAYMENT_OS_WBMALL : os, false);
				WXPay wxpay = new WXPay(config);

				if (StringUtils.isEmpty(openId) && !os.equals(WachatContent.PAYMENT_OS_APP) && !os.equals(WachatContent.PAYMENT_TRADE_TYPE_MWEB)) {
					openId = wxpay.getOpenId(openCode);
					if (StringUtils.isEmpty(openId)) {
						result.put("result", "FAILURE");
						result.put("msg", "openId 获取失败");
						return result;
					}
				}
				Map<String, String> data = new HashMap<String, String>();
				data.put("body", "富农汇" + os + "订单支付");
				data.put("out_trade_no", orderId);
				data.put("device_info", "");
				data.put("fee_type", "CNY");
				data.put("total_fee", String.valueOf(totalFee));
				data.put("spbill_create_ip", spbillIp);
				data.put("notify_url", notifyurl);
				data.put("trade_type", os.equals(WachatContent.PAYMENT_OS_APP) ? WachatContent.PAYMENT_OS_APP : os.equals(WachatContent.PAYMENT_TRADE_TYPE_MWEB) ? WachatContent.PAYMENT_TRADE_TYPE_MWEB : WachatContent.PAYMENT_TRADE_TYPE_WXPAY); // 此处指定微信的支付方式
				if (!os.equals(WachatContent.PAYMENT_OS_APP) && !os.equals(WachatContent.PAYMENT_TRADE_TYPE_MWEB)) {
					data.put("openid", openId);
				}/*
				if (tradeType.equals("APP")) {
					data.put("appid", "wxd6fbba723e1911b1");
					data.put("mch_id", "1358604902");//fd8f23f09dab11e78dae5cb9018d93ec
				}*/
				data.put("attach", os);
				if (os.equals(WachatContent.PAYMENT_TRADE_TYPE_MWEB)) { //微信的H5支付时参数
					//{"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}
					JSONObject jsonObject = new JSONObject();
					JSONObject jsonDetail = new JSONObject();
					jsonDetail.put("type", "Wap");
					jsonDetail.put("wap_url", this.getWapUrlByOs(WachatContent.PAYMENT_OS_WBMALL)); //暂时固定为wbmall，可能需要新增参数trade_type 调用的支付方式如为H5支付时可能会有多种跳转可能
					jsonDetail.put("wap_name", "富农汇" + os + "订单支付");
					jsonObject.put("h5_info", jsonDetail);
					data.put("scene_info", jsonObject.toString());
				}

				Map<String, String> uniResult = wxpay.unifiedOrder(data);
				if((null!=uniResult)&&("SUCCESS".equals(uniResult.get("return_code")))){
					if (os.equals(WachatContent.PAYMENT_TRADE_TYPE_MWEB)) {
						result.put("result", "SUCCESS");
						result.put("mweb_url", uniResult.get("mweb_url"));
						return result;
					}
					String appid=uniResult.get("appid");
					String nonce_str=WXPayUtil.generateUUID();
					String prepay_id=uniResult.get("prepay_id");
					String timestamp = Sha1Util.getTimeStamp();
					
					String packageStr = "prepay_id=" + prepay_id;
					result.put("package", !os.equals(WachatContent.PAYMENT_OS_APP) ? packageStr : "Sign = WXPay");
					if (!os.equals(WachatContent.PAYMENT_OS_APP)) {
						result.put("appId", appid);
						result.put("timeStamp", timestamp);
						result.put("nonceStr", nonce_str);
						result.put("signType", SignType.HMACSHA256.toString());
					}
					
					if (os.equals(WachatContent.PAYMENT_OS_APP)) {
						result.put("appid", appid);
						result.put("partnerid", uniResult.get("mch_id"));
						result.put("prepayid", prepay_id);
						result.put("noncestr", nonce_str);
						result.put("timestamp", timestamp);
					}
					
					String paySign = WXPayUtil.generateSignature(result, config.getKey(),SignType.HMACSHA256);
					if (!os.equals(WachatContent.PAYMENT_OS_APP)) {
						result.put("paySign", paySign);
						result.put("openId", openId);
					}
					if (os.equals(WachatContent.PAYMENT_OS_APP)) {
						result.put("sign", paySign);
					}
					result.put("result", "SUCCESS");
					return result;
				}else{
					result.put("result", "FAILURE");
					result.put("msg", "统一下单失败"+uniResult);
					return result;
				}
			} else {
				result.put("result", "FAILURE");
				result.put("msg", "订单查询失败");
				return result;
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
			result.put("result", "FAILURE");
			result.put("msg", "统一下单失败");
			return result;
		}
	}

	@Override
	public String getKeyByOs(String os) {
		// TODO Auto-generated method stub
		return WachatContent.getPropertyValue(WachatContent.WXPAY_KEY_PREFIX + (os.equals(WachatContent.PAYMENT_TRADE_TYPE_MWEB) ? WachatContent.PAYMENT_OS_WBMALL : os));
	}

}
