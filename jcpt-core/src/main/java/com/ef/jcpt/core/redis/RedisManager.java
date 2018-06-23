/**
 * Project Name:caifubao-core <br>
 * File Name:RedisManager.java <br>
 * Package Name:com.caifubao.jcpt.core.redis <br>
 *
 * @author xiezbmf
 * Date:2017年10月19日下午6:35:23 <br>
 * Copyright (c) 2017, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.core.redis;

import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.ef.jcpt.common.config.RedisCachePropertiesConfig;
import com.ef.jcpt.common.util.SerializableUtil;
import com.ef.jcpt.common.util.StringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * ClassName: RedisManager <br>
 * Description: RedisManager
 *
 * @author xiezbmf
 * @Date 2017年10月19日下午6:35:23 <br>
 * @since JDK 1.6
 */
public class RedisManager {

    private static String host = RedisCachePropertiesConfig.REDIS_HOST;
    private static int port = RedisCachePropertiesConfig.REDIS_PORT;
    // 0 - never expire
    private static int expire = RedisCachePropertiesConfig.REDIS_EXPIRE;
    //timeout for jedis try to connect to redis server, not expire time! In milliseconds
    private static int timeout = RedisCachePropertiesConfig.REDIS_CONNECT_TIMEOUT;

    private static String password = RedisCachePropertiesConfig.REDIS_PWD;

    private static JedisPool pool = null;

    public static JedisPool getPool() {
        if (pool == null) {

            JedisPoolConfig config = new JedisPoolConfig();
//            config.setMaxTotal(500);
//            config.setMaxIdle(5);
//            config.setMaxWaitMillis(1000*10);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
//            config.setTestOnBorrow(true);
            if (StringUtil.isNotEmpty(password)) {
                pool = new JedisPool(config, host, port, timeout, password);
            } else {
                pool = new JedisPool(config, host, port, timeout);
            }
        }
        return pool;
    }

    public synchronized static Jedis getResource() {
        if (pool == null) {
            pool = getPool();
        }
        return pool.getResource();
    }

    public static Object getObjectFromCache(String keyStr) {
        byte[] key = keyStr.getBytes();
        byte[] value = get(key);
        return SerializableUtil.deserialize(value);
    }

    public static void saveObjectToCache(String keyStr, Object obj) {
        byte[] key = keyStr.getBytes();
        byte[] value = SerializableUtil.serialize(obj);
        set(key, value);
    }
    
    
    /**
     * saveObjectToCache:保存. <br>
     *
     * @author xiezbmf
     * @Date 2018年3月17日下午2:38:48 <br>
     * @param keyStr
     * @param obj
     * @param minutes 分钟
     */
    public static void saveObjectToCache(String keyStr, Object obj,int minutes) {
        byte[] key = keyStr.getBytes();
        byte[] value = SerializableUtil.serialize(obj);
        set(key, value,minutes*60);
    }


    /**
     * get value from redis
     *
     * @param key
     * @return
     */
    public static byte[] get(byte[] key) {
        byte[] value = null;
        Jedis jedis = getResource();
        try {
            value = jedis.get(key);
        } finally {
//			pool.returnResource(jedis);
            jedis.close();
        }
        return value;
    }

    /**
     * set
     *
     * @param key
     * @param value
     * @return
     */
    public static byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getResource();
        try {
            jedis.set(key, value);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
        } finally {
            jedis.close();
        }
        return value;
    }

    public static void main(String[] args) {
		del("LOCK_LoanBidServiceImplautoDebt");
	}
    /**
     * set
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public static byte[] set(byte[] key, byte[] value, int expire) {
        Jedis jedis = getResource();
        try {
            jedis.set(key, value);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
        } finally {
            jedis.close();
        }
        return value;
    }


    public static Long getIncre(String key) {
        Jedis jedis = getResource();
        try {
            return jedis.incr(key);
        } finally {
            jedis.close();
        }
    }

    public static Long getIncre(String key, long n) {
        Jedis jedis = getResource();
        try {
            return jedis.incrBy(key, n);
        } finally {
            jedis.close();
        }
    }

    /**
     * del
     *
     * @param key
     */
    public static void del(byte[] key) {
        Jedis jedis = getResource();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    public static void del(String key) {
        Jedis jedis = getResource();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    /**
     * flush
     */
    public static void flushDB() {
        Jedis jedis = getResource();
        try {
            jedis.flushDB();
        } finally {
            jedis.close();
        }
    }

    /**
     * size
     */
    public static Long dbSize() {
        Long dbSize = 0L;
        Jedis jedis = getResource();
        try {
            dbSize = jedis.dbSize();
        } finally {
            jedis.close();
        }
        return dbSize;
    }

    /**
     * keys
     *
     * @param
     * @return
     */
    public static Set<byte[]> keys(String pattern) {
        Set<byte[]> keys = null;
        Jedis jedis = getResource();
        try {
            keys = jedis.keys(pattern.getBytes());
        } finally {
            jedis.close();
        }
        return keys;
    }

    public static void set(String key, Object val, int expire) {
        Jedis jedis = getResource();
        jedis.set(key, JSON.toJSONString(val));
        if (expire != 0) {
            jedis.expire(key, expire);
        }
        jedis.close();
    }

    public static void set(String key, Object val) {
        set(key, val, 0);
    }

    public static void set(String key, String val) {
        set(key, val, 0);
    }

    public static void set(String key, String val, int expire) {
        Jedis jedis = getResource();
        jedis.set(key, val);
        if (expire != 0) {
            jedis.expire(key, expire);
        }
        jedis.close();
    }

    public static String get(String key) {
        Jedis jedis = getResource();
        String val = jedis.get(key);
        jedis.close();
        return val;
    }
    
    public static Set<String> showKeys(String pattern){
    	Jedis jedis = getResource();
    	return jedis.keys(pattern);
    }
}

	