package com.ef.jcpt.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;

public class HttpClientUtils {
	private static final Logger logger = Logger.getLogger(HttpClientUtils.class);

	private static final String parameterJoin = "&";
	private static final String parameterEqual = "=";

	public static String makeUrl(String baseUrl, Map<String, String> parameterMap) {
		StringBuffer parasb = new StringBuffer(baseUrl);
		if (null != parameterMap) {
			parasb.append("?");
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				parasb.append(entry.getKey()).append(parameterEqual).append(entry.getValue() == null ? entry.getValue()
						: entry.getValue().replaceAll(" ", "%20").replaceAll("　", "")).append(parameterJoin);
			}
		}
		return parasb.substring(0, parasb.length() - 1);
	}

	public static String appendParameter(String baseUrl, Map<String, String> parameterMap) {
		StringBuffer parasb = new StringBuffer(baseUrl);
		if (null != parameterMap) {
			parasb.append(parameterJoin);
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				String val;
				try {
					val = URLEncoder.encode(entry.getValue(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage());
					val = entry.getValue();
				}
				parasb.append(entry.getKey()).append(parameterEqual).append(val).append(parameterJoin);

			}
		}
		return parasb.substring(0, parasb.length() - 1);
	}

	public static String get(String url) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}

	/**
	 * 流数据请求
	 * 
	 * @param url
	 *            地址（请求参数都写在地址里）
	 * @return
	 */
	public static BasicServiceModel<byte[]> getPdfToPic(String reqUrl) {
		logger.info("请求URL:>>>" + reqUrl);
		BasicServiceModel<byte[]> result = new BasicServiceModel<byte[]>();
		InputStream is = null;
		try {
			final URL url = new URL(reqUrl);
			final URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/octet-stream");
			is = con.getInputStream();
			byte pdfArr[] = PdfUtil.pdfToPic(is);
			if (pdfArr == null) {
				result.setCode(ReqStatusConst.FAIL);
			} else {
				result.setCode(ReqStatusConst.OK);
			}
			result.setMsg("success");
			result.setData(pdfArr);
		} catch (Exception e) {
			logger.error("HttpClient请求getInputStream方法异常", e);
			result = new BasicServiceModel<byte[]>();
			result.setCode(ReqStatusConst.FAIL);
			result.setMsg(e.getMessage());
		} finally {
			IOUtils.closeQuietly(is);
		}
		return result;

	}

	/**
	 * 流数据请求
	 * 
	 * @param url
	 *            地址（请求参数都写在地址里）
	 * @return
	 */
	public static BasicServiceModel<byte[]> getInputStream(String reqUrl) {
		logger.info("请求URL:>>>" + reqUrl);
		BasicServiceModel<byte[]> result = null;
		ByteArrayOutputStream baos = null;
		InputStream is = null;
		try {
			final URL url = new URL(reqUrl);
			final URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/octet-stream");
			is = con.getInputStream();
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			result = new BasicServiceModel<byte[]>();
			int len = -1;
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			if (baos != null) {
				String status = new String(baos.toByteArray());
				if ("-1".equals(status) || "-2".equals(status) || "-3".equals(status) || "".equals(status)) {
					result.setCode(ReqStatusConst.FAIL);
				} else {
					result.setCode(ReqStatusConst.OK);
				}
			} else {
				result.setCode(ReqStatusConst.FAIL);
			}
			result.setMsg("success");
			result.setData(baos.toByteArray());
		} catch (Exception e) {
			logger.error("HttpClient请求getInputStream方法异常", e);
			result = new BasicServiceModel<byte[]>();
			result.setCode(ReqStatusConst.FAIL);
			result.setMsg(e.getMessage());
		} finally {
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(is);
		}
		return result;

	}

	public static String get(String url, Map<String, String> params) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			String newUrl = appendParameter(url, params);
			logger.info("调用newUrl：" + newUrl);
			HttpGet httpGet = new HttpGet(newUrl);
			response = httpclient.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}

			logger.info(url + "回复:" + resultString.toString());

			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static String post(String url, Map<String, String> params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			response = httpclient.execute(httpPost);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static byte[] downloadFile(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpGet);
			if (response != null) {
				InputStream inputStream = response.getEntity().getContent();
				ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
				byte[] buff = new byte[100];
				int rc = 0;
				while ((rc = inputStream.read(buff, 0, 100)) > 0) {
					swapStream.write(buff, 0, rc);
				}
				byte[] in2b = swapStream.toByteArray();
				return in2b;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String requestPost(String url, Map<String, String> map) {
		CloseableHttpClient httpclient = null;
		if (url.startsWith("https://")) {
			try {
				SSLContext sslcontext = SSLContexts.createDefault();
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
						null, new HostnameVerifier() {
							@Override
							public boolean verify(String hostname, SSLSession arg1) {
								return true;
							}
						});
				httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			} catch (Exception e) {
				logger.error("证书异常" + e.getMessage());
				return null;
			}
		} else {
			httpclient = HttpClients.custom().build();
		}
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000) // 设置连接超时时间，单位毫秒
				.setConnectionRequestTimeout(6000) // 设置从connect Manager获取Connection
													// 超时时间，单位毫秒。这个属性是新加的属性,因为目前版本是可以共享连接池的
				.setSocketTimeout(30000) // 请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用
				.build();
		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(requestConfig);
		if (map != null && map.size() > 0) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Set keys = map.keySet();
			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
				String name = (String) iterator.next();
				String value = (String) map.get(name);
				params.add(new BasicNameValuePair(name, value));
			}
			try {
				httppost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		try {
			CloseableHttpResponse response = null;
			response = httpclient.execute(httppost);
			if (null != response) {
				HttpEntity entity = null;
				try {
					entity = response.getEntity();
					if (entity != null) {
						String ret = new String(IOUtils.toByteArray(entity.getContent()),
								getResCharset(response, "utf-8"));
						return ret;
					}
					EntityUtils.consume(entity);
				} finally {
					response.close();
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public static String getResCharset(CloseableHttpResponse response, String charset) {
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
}
