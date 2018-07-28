package com.ef.jcpt.trade.component;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.jcpt.common.constant.FlowKeyConst;
import com.ef.jcpt.common.constant.PayStatusConst;
import com.ef.jcpt.core.redis.IDProvider;
import com.ef.jcpt.trade.dao.PayInfoMapper;
import com.ef.jcpt.trade.dao.model.OrderInfo;
import com.ef.jcpt.trade.dao.model.PayInfo;

@Component
public class PayInfoComponent {

	@Autowired
	private PayInfoMapper payInfoMapper;

	public PayInfo genPayInfo(OrderInfo orderInfo) {
		String flowId = IDProvider.getFlowId(FlowKeyConst.PAY_FLOW);
		Date currDate = new Date(System.currentTimeMillis());

		PayInfo payInfo = new PayInfo();
		payInfo.setFlowId(flowId);
		payInfo.setHandFee(BigDecimal.ZERO);
		payInfo.setOrderId(orderInfo.getOrderId());
		payInfo.setPayAmount(orderInfo.getPayAmount());
		payInfo.setPayStatus(PayStatusConst.INIT);
		payInfo.setCreateTime(currDate);
		payInfo.setUpdateTime(currDate);
		payInfoMapper.insertSelective(payInfo);

		return payInfo;
	}
}
