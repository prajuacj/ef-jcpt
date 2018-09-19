package com.ef.jcpt.trade.component;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.jcpt.common.constant.FlowKeyConst;
import com.ef.jcpt.common.constant.OrderStatusConst;
import com.ef.jcpt.core.redis.IDProvider;
import com.ef.jcpt.trade.dao.OrderInfoMapper;
import com.ef.jcpt.trade.dao.model.FlowProduct;
import com.ef.jcpt.trade.dao.model.OrderInfo;

@Component
public class OrderInfoComponent {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	public OrderInfo genOrderInfo(OrderInfo info) {
		Date currDate = new Date(System.currentTimeMillis());
		String orderId = IDProvider.getFlowId(FlowKeyConst.PA_ORD_FLOW);
		info.setOrderId(orderId);
		info.setOrderStatus(OrderStatusConst.INIT);
		info.setCreateTime(currDate);
		info.setUpdateTime(currDate);
		orderInfoMapper.insert(info);
		return info;
	}

	public List<OrderInfo> listOrderByPage(String userId, String nationCode, String orderStatus, int start,
			int pageSize) {
		// TODO Auto-generated method stub
		return orderInfoMapper.listOrderByPage(userId, nationCode, orderStatus, start, pageSize);
	}

	public int updateOrderOperatorByOrderId(String orderId, int operatorId, String operatorName) {
		// TODO Auto-generated method stub
		OrderInfo info = new OrderInfo();
		info.setOrderId(orderId);
		info.setOperatorId(operatorId);
		info.setOperatorName(operatorName);
		info.setUpdateTime(new Date(System.currentTimeMillis()));
		return orderInfoMapper.updateByPrimaryKeySelective(info);
	}

	public int countOrderByPage(String userName, String nationCode, String orderStatus) {
		// TODO Auto-generated method stub
		return orderInfoMapper.countOrderByPage(userName, nationCode, orderStatus);
	}

	public void updateOrderInfo(OrderInfo retOrder) {
		// TODO Auto-generated method stub
		orderInfoMapper.updateByPrimaryKeySelective(retOrder);
	}

	public FlowProduct publishProduct(FlowProduct info) {
		// TODO Auto-generated method stub
		return null;
	}
}
