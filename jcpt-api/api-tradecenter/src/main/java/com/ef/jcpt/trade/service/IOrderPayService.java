package com.ef.jcpt.trade.service;

import java.util.List;
import java.util.Map;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.trade.service.bo.FlowProductBo;
import com.ef.jcpt.trade.service.bo.OrderInfoBo;
import com.ef.jcpt.trade.service.bo.PayInfoBo;
import com.ef.jcpt.trade.service.bo.PhoneSupportOperatorBo;

public interface IOrderPayService {

	PayInfoBo genPayInfoByOrderId(String orderId);

	PayInfoBo queryPayInfoByKey(String flowId);

	PayInfoBo queryPayInfoByOrderId(String orderId);

	BasicServiceModel<Map<String, String>> toPay(OrderInfoBo bo, String code, String ip);

	BasicServiceModel<List<OrderInfoBo>> listOrderByPage(String userName, String nationCode, String orderStatus,
			int indexPage, int pageSize);

	int countOrderByPage(String userName, String nationCode, String orderStatus);

	BasicServiceModel<List<PhoneSupportOperatorBo>> listOperator(String phoneModel, String nationCode);

	BasicServiceModel<String> updateOrderOperatorByOrderId(String orderId, int operatorId);

	int countProductByPage(String userNationCode, String buyNationCode, String productType);

	BasicServiceModel<List<FlowProductBo>> listProductByPage(String userNationCode, String buyNationCode,
			String productType, int indexPage, int pageSize);
}
