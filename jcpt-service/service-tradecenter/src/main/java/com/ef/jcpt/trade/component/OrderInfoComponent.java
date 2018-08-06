package com.ef.jcpt.trade.component;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.jcpt.common.constant.FlowKeyConst;
import com.ef.jcpt.common.constant.OrderStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.util.BeanUtil;
import com.ef.jcpt.core.redis.IDProvider;
import com.ef.jcpt.trade.dao.OrderInfoMapper;
import com.ef.jcpt.trade.dao.model.OrderInfo;
import com.ef.jcpt.trade.service.bo.OrderInfoBo;

@Component
public class OrderInfoComponent {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	public OrderInfo genOrderInfo(OrderInfo bo) {
		Date currDate = new Date(System.currentTimeMillis());
		String orderId = IDProvider.getFlowId(FlowKeyConst.PA_ORD_FLOW);
		OrderInfo info = new OrderInfo();
		BeanUtil.copyProperties(bo, info);
		info.setOrderId(orderId);
		info.setOrderStatus(OrderStatusConst.INIT);
		info.setCreateTime(currDate);
		info.setUpdateTime(currDate);
		orderInfoMapper.insert(info);
		bo.setOrderId(orderId);
		return info;
	}

	public BasicServiceModel<List<OrderInfoBo>> listOrder(String userId) {
		// TODO Auto-generated method stub
		return orderInfoMapper.listOrderByUserId(userId);
	}
}