package com.ef.jcpt.core.cache;

import java.lang.reflect.Type;

import com.ef.jcpt.core.entity.TokenVo;

/*
 * <p>Title: CacheUtil.java</p>
 *
 * <p>Description:缓存工具接口</p>
 *
 * <p>Copyright: Copyright (c) gofar</p>
 *
 * @author ning.peng
 * @date 2016-4-26 上午11:48:47
 */
public interface CacheUtil {

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            缓存key
	 * @param value
	 *            方法返回的数据类型，内部会被转换成json
	 */
	public void set(String key, Object value);

	/**
	 * 设置缓存，有过期时间
	 * 
	 * @param key
	 *            缓存key
	 * @param value
	 *            方法返回的数据类型，内部会被转换成json
	 * @param time
	 *            过期时间，单位为秒
	 */
	public void set(String key, Object value, int time);

	/**
	 * 读取缓存中数据，将数据转换成对象
	 * 
	 * @param key
	 *            缓存key
	 * @param returnType
	 *            方法返回的数据类型
	 */
	public <T> T get(String key, Type returnType) throws Exception;

	/**
	 * 读取缓存中的数据
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key);

	/**
	 * 删除缓存中的数据
	 * 
	 * @param key
	 */
	public void del(String key);

	/**
	 * 设置token到缓存中
	 * 
	 * @param tokenVo
	 * @param username
	 * @return tokenKey
	 */
	public String setToken(TokenVo tokenVo, String username);

	/**
	 * 设置token到缓存中，并设置缓存
	 * 
	 * @param tokenVo
	 * @param username
	 * @param expretime
	 * @return
	 */
	public String setToken(TokenVo tokenVo, String username, Integer expretime);

	/**
	 * 从缓存中获取token
	 * 
	 * @param key
	 * @return
	 */
	public TokenVo getToken(String key) throws Exception;
}
