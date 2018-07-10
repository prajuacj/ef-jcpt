package com.ef.jcpt.trade.service;

import java.util.Map;

import com.ef.jcpt.trade.service.bo.Alipayh5Bo;

public interface IAlipayh5Service {

	public String toPay(Alipayh5Bo bo);

	public String buildMobilePayData(Alipayh5Bo bo) throws Exception;

	public Map<String, Object> payfront(Map<String, String> reqs);

	public Map<String, Object> payback(Map<String, String> reqs);
}
