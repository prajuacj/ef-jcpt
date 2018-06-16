/**
 * Project Name:caifubao-common <br>
 * File Name:HttpUtil.java <br>
 * Package Name:com.caifubao.jcpt.common.util <br>
 * @author xiezbmf
 * Date:2017年11月13日上午11:15:57 <br>
 * Copyright (c) 2017, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * ClassName: HttpUtil <br>
 * Description: http请求工具类
 * 
 * @author xiezbmf
 * @Date 2017年11月13日上午11:15:57 <br>
 * @version
 * @since JDK 1.6
 */
public class HttpUtil {
	final static String ENCODE = "UTF-8";
	
	/**
	 * 
	 * requestPost:post方式http请求. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年11月13日下午2:55:46 <br>
	 * @param url 请求url
	 * @param map 请求参数
	 * @return String 请求结果
	 * @throws Exception
	 */
	public static String requestPost(String url, Map<String, String> map) throws Exception {
		CloseableHttpClient httpclient = null;
		if (url.startsWith("https://")) {
			try {
				SSLConnectionSocketFactory socketfactory = SSLConnectionSocketFactory.getSocketFactory();
				httpclient = HttpClients.custom().setSSLSocketFactory(socketfactory).build();
			} catch (Exception e) {
				throw new Exception("构建https协议失败",e);
			}
		} else {
			httpclient = HttpClients.custom().build();
		}
		HttpPost httppost = new HttpPost(url);
		if (map != null && map.size() > 0) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Set<String> keys = map.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String name = (String) iterator.next();
				String value = (String) map.get(name);
				params.add(new BasicNameValuePair(name, value));
			}
			try {
				httppost.setEntity(new UrlEncodedFormEntity(params, ENCODE));
			} catch (UnsupportedEncodingException e) {
				throw new Exception("不支持的字符编码类型"+ENCODE,e);
			}
		}
		setRequestHttpPostTimeout(httppost,3*1000,15*1000);
		try {
			CloseableHttpResponse response = null;
			response = httpclient.execute(httppost);
			if (null != response) {
				HttpEntity entity = null;
				try {
					entity = response.getEntity();
					if (entity != null) {
						String ret = new String(IOUtils.toByteArray(entity.getContent()),
								getResCharset(response, ENCODE));
						return ret;
					}
					EntityUtils.consume(entity);
				} finally {
					response.close();
				}
			}
		} catch (Exception e) {
			throw new Exception("http连接请求出错"+url,e);
		}
		return null;
	}

	static String getResCharset(CloseableHttpResponse response, String charset) {
		Header[] contentTypes = response.getHeaders("Content-Type");
		String resCharset = charset;
		int totalTypes = null != contentTypes ? contentTypes.length : 0;
		for (int i = 0; i < totalTypes; i++) {
			String[] value = contentTypes[i].getValue().toLowerCase().split(";");
			for (int j = 0; j < value.length; j++) {
				if (value[j].startsWith("charset=")) {
					resCharset = value[j].substring("charset=".length());
				}
			}
		}
		return resCharset;
	}
	 private static RequestConfig setRequestHttpPostTimeout(HttpPost httpPost,Integer connectTimeout,Integer connectionRequestTimeout)
		{
			
			RequestConfig requestConfig = RequestConfig.custom()  
			        .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout)  
			        .setSocketTimeout(connectionRequestTimeout).build();  
			httpPost.setConfig(requestConfig);
			return requestConfig;
		}
}
