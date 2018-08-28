package com.ef.jcpt.trade.service;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.trade.service.bo.OrderInfoBo;
import com.ef.jcpt.trade.service.bo.PayInfoBo;

public interface IOrderPayService {

	PayInfoBo genPayInfoByOrderId(String orderId);

	PayInfoBo queryPayInfoByKey(String flowId);

	PayInfoBo queryPayInfoByOrderId(String orderId);

	BasicServiceModel<String> toPay(OrderInfoBo bo, String code, String ip);

	BasicServiceModel<String> listOrderByPage(String userName, String nationCode, String orderStatus, int indexPage,
			int pageSize);

	int countOrderByPage(String userName, String nationCode, String orderStatus);

	BasicServiceModel<String> listOperator(String phoneModel, String nationCode);

	BasicServiceModel<String> updateOrderOperatorByOrderId(String orderId, int operatorId);

	int countProductByPage(String userNationCode, String buyNationCode, String productType);

	BasicServiceModel<String> listProductByPage(String userNationCode, String buyNationCode, String productType,
			int indexPage, int pageSize);

	BasicServiceModel<String> updateWXPayResult(String sn, String orderAmt, String settleAmt, String wxorderid,
			String endTime);
}
