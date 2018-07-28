package com.ef.jcpt.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ef.jcpt.common.constant.FlowKeyConst;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.util.BeanUtil;
import com.ef.jcpt.core.redis.IDProvider;
import com.ef.jcpt.trade.component.OrderInfoComponent;
import com.ef.jcpt.trade.component.PayInfoComponent;
import com.ef.jcpt.trade.dao.OrderInfoMapper;
import com.ef.jcpt.trade.dao.PayInfoMapper;
import com.ef.jcpt.trade.dao.model.OrderInfo;
import com.ef.jcpt.trade.dao.model.PayInfo;
import com.ef.jcpt.trade.service.IOrderPayService;
import com.ef.jcpt.trade.service.IWechatpayh5Service;
import com.ef.jcpt.trade.service.bo.OrderInfoBo;
import com.ef.jcpt.trade.service.bo.PayInfoBo;

@Service
public class OrderPayServiceImpl implements IOrderPayService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private OrderInfoComponent orderInfoComponent;

	@Autowired
	private PayInfoComponent payInfoComponent;

	@Autowired
	private PayInfoMapper payInfoMapper;

	@Autowired
	private IWechatpayh5Service wechathpayh5ServiceImpl;

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
		payInfo.setUserId(orderInfo.getUserId());
		payInfo.setPayAmount(orderInfo.getPayAmount());
		payInfo.setOrderContent(orderInfo.getProductName());
		payInfoMapper.insertSelective(payInfo);
		BeanUtils.copyProperties(payInfo, bo);
		return bo;
	}

	public PayInfoBo queryPayInfoByKey(String flowId) {
		PayInfoBo bo = new PayInfoBo();
		PayInfo info = payInfoMapper.selectByPrimaryKey(flowId);
		BeanUtils.copyProperties(info, bo);
		return bo;
	}

	@Override
	public PayInfoBo queryPayInfoByOrderId(String orderId) {
		// TODO Auto-generated method stub
		PayInfoBo bo = new PayInfoBo();
		PayInfo info = payInfoMapper.selectByOrderId(orderId);
		BeanUtils.copyProperties(info, bo);
		return bo;
	}

	@Override
	public BasicServiceModel<Map<String, String>> toPay(OrderInfoBo origBo, String code, String ip) {
		// TODO Auto-generated method stub
		BasicServiceModel<Map<String, String>> bsm = new BasicServiceModel<Map<String, String>>();

		try {
			OrderInfo info = new OrderInfo();
			BeanUtil.copyProperties(origBo, info);
			OrderInfo orderInfo = orderInfoComponent.genOrderInfo(info);

			PayInfo payInfo = payInfoComponent.genPayInfo(orderInfo);
			PayInfoBo payBo = new PayInfoBo();
			BeanUtil.copyProperties(payInfo, payBo);

			Map<String, String> map = wechathpayh5ServiceImpl.toPay(payBo, code, ip);
			bsm.setCode(ReqStatusConst.OK);
			bsm.setData(map);
			return bsm;
		} catch (Exception e) {
			e.printStackTrace();
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg(e.getMessage());
			return bsm;
		}
	}

	@Override
	public BasicServiceModel<List<OrderInfoBo>> listOrder(String userId) {
		// TODO Auto-generated method stub
		return orderInfoComponent.listOrder(userId);
	}
}
