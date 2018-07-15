package com.ef.jcpt.trade.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ef.jcpt.trade.service.bo.Alipayh5Bo;

public interface IAlipayh5Service {

	public String toPay(Alipayh5Bo bo);

	public Map<String, Object> payfront(HttpServletRequest request);

	public Map<String, Object> payback(HttpServletRequest request);
}
