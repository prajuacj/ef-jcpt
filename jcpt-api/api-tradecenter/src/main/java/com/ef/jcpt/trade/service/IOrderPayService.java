package com.ef.jcpt.trade.service;

import java.util.List;
import java.util.Map;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.trade.service.bo.OrderInfoBo;
import com.ef.jcpt.trade.service.bo.PayInfoBo;
import com.ef.jcpt.trade.service.bo.PhoneSupportOperatorBo;

public interface IOrderPayService {

	PayInfoBo genPayInfoByOrderId(String orderId);

	PayInfoBo queryPayInfoByKey(String flowId);

	PayInfoBo queryPayInfoByOrderId(String orderId);

	BasicServiceModel<Map<String, String>> toPay(OrderInfoBo bo, String code, String ip);

	BasicServiceModel<List<OrderInfoBo>> listOrder(String userId);

	BasicServiceModel<List<PhoneSupportOperatorBo>> listOperator(String phoneModel, String nationCode);
}
