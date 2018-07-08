package com.ef.jcpt.common.constant;

public class CacheConstant {

	/**
	 * 初始化用户缓存前缀
	 */
	public final static String CACHE_STARTUSER_PREFIX = "user_";
	
	/**
	 * 购物车缓存前缀
	 */
	public final static String CACHE_STARTCART_PREFIX = "cart_";
	
	/**
	 * 后台生成token的前缀
	 */
	public final static String TOKENKEY_PREFIX = "token_";
	
	/**
	 * token默认失效时间
	 */
	public final static int DEFAULT_TOKEN_LOSTTIME = 24 * 60 * 60 * 60;
	
	/**
	 * 前台传输参数中的token名字
	 */
	public final static String DEFAULT_TOKEN_KEYNAME = "tokenKey";
	
	/**
	 * APPID 参数名字
	 */
	public final static String APPID_PARAMNAME = "appID";
	
	public final static String CACHE_RROVINCEID_PREFIX = "provinceId_";
}
