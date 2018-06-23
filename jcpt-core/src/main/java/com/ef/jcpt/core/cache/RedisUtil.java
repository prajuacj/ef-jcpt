package com.ef.jcpt.core.cache;

import java.lang.reflect.Type;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.CacheConstant;
import com.ef.jcpt.core.digest.Digests;
import com.ef.jcpt.core.entity.TokenVo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/*
 * <p>Title: RedisUtil.java</p>
 *
 * <p>Description:Redis工具类</p>
 *
 * <p>Copyright: Copyright (c) gofar</p>
 *
 * @author ning.peng
 * @date 2016-4-21 下午3:04:05
 */
@Component
public class RedisUtil implements CacheUtil {
	
	JedisPool jedisPool;

	@Autowired
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	@Override
	public void set(String key, Object value) {
		//从redis池中获取redis实例，使用完毕后需要调用jedis.close();
		Jedis jedis = null;
		try {
		  jedis = jedisPool.getResource();
		  jedis.set(key, JSONObject.toJSONString(value));
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
	
	@Override
	public void set(String key, Object value, int time) {
		//从redis池中获取redis实例，使用完毕后需要调用jedis.close();
		Jedis jedis = null;
		try {
		  jedis = jedisPool.getResource();
		  jedis.setex(key, time, JSONObject.toJSONString(value));
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
	@Override
	public <T> T get(String key, Type returnType) throws Exception {
		Jedis jedis = null;
		try {
		  jedis = jedisPool.getResource();
		  String text = jedis.get(key);
		  T result = JSON.parseObject(text, returnType);
		  return result;
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}

	@Override
	public void del(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} finally {
			if (jedis != null) {
			    jedis.close();
			}
		}
	}

	@Override
	public String setToken(TokenVo tokenVo, String username) {
		//生成tokenKey
		String tokenKey = CacheConstant.TOKENKEY_PREFIX + UUID.randomUUID().toString().replaceAll("-", "") + "_" + Digests.getMD5(username);
		tokenVo.setTokenKey(tokenKey);
		set(tokenKey, tokenVo, CacheConstant.DEFAULT_TOKEN_LOSTTIME);
		return tokenKey;
	}

	@Override
	public TokenVo getToken(String key) throws Exception{
		return get(key, TokenVo.class);
	}

	

	@Override
	public String setToken(TokenVo tokenVo,String username, Integer expretime) {
		if (expretime != null && expretime > 0) {
			String tokenKey = CacheConstant.TOKENKEY_PREFIX + UUID.randomUUID().toString().replaceAll("-", "") + "_" + Digests.getMD5(username);
			tokenVo.setTokenKey(tokenKey);
			set(tokenKey, tokenVo, expretime);
			return tokenKey;
		} else {
			return setToken(tokenVo, username);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(JSONObject.toJSONString(System.currentTimeMillis()));
	}

	@Override
	public String getString(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} finally {
			if (jedis != null) {
			    jedis.close();
			}
		}
	}
}
