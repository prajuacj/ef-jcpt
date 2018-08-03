package com.ef.jcpt.trade.config;

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = null;// Alipayh5Contents.partner;
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串
	public static String seller_id = null;// Alipayh5Contents.sellerid;
	// 商户的私钥
	public static String private_key = null;// Alipayh5Contents.privatekey;
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key = null;// Alipayh5Contents.alipublickey;

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "E:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 不需修改
	public static String sign_type = "RSA";
	public static int alipayh5backUrl;
	public static String alipayh5frontUrl;
}
