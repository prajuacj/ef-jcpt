package com.ef.jcpt.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ef.jcpt.common.constant.FlowKeyConst;
import com.ef.jcpt.core.redis.IDProvider;
import com.ef.jcpt.trade.dao.OrderInfoMapper;
import com.ef.jcpt.trade.dao.PayInfoMapper;
import com.ef.jcpt.trade.dao.model.OrderInfo;
import com.ef.jcpt.trade.dao.model.PayInfo;
import com.ef.jcpt.trade.service.IOrderPayService;
import com.ef.jcpt.trade.service.bo.PayInfoBo;

@Service
public class OrderPayServiceImpl implements IOrderPayService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private PayInfoMapper payInfoMapper;

	@Transactional(rollbackFor = Exception.class)
	public PayInfoBo genPayInfoByOrderId(String orderId) {
		PayInfoBo bo = new PayInfoBo();
		String flowId = IDProvider.getFlowId(FlowKeyConst.PAY_FLOW);

		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);

		PayInfo payInfo = new PayInfo();
		payInfo.setCreateTime(new Date());
		payInfo.setFlowId(flowId);
		payInfo.setHandFee(BigDecimal.ZERO);
		payInfo.setOrderId(orderId);
		payInfo.setPayAmount(orderInfo.getPayAmount());
		payInfoMapper.insertSelective(payInfo);
		BeanUtils.copyProperties(payInfo, bo);
		return bo;
	}
}
