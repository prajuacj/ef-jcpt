package com.ef.jcpt.trade.service;

import com.ef.jcpt.trade.service.bo.PayInfoBo;

public interface IOrderPayService {

	PayInfoBo genPayInfoByOrderId(String orderId);
}
