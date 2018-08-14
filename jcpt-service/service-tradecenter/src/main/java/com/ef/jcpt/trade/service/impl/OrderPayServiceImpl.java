package com.ef.jcpt.trade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.ef.jcpt.trade.component.FlowProductComponent;
import com.ef.jcpt.trade.component.OrderInfoComponent;
import com.ef.jcpt.trade.component.PayInfoComponent;
import com.ef.jcpt.trade.component.PhoneSupportOperatorComponent;
import com.ef.jcpt.trade.dao.OperatorInfoMapper;
import com.ef.jcpt.trade.dao.OrderInfoMapper;
import com.ef.jcpt.trade.dao.PayInfoMapper;
import com.ef.jcpt.trade.dao.model.OperatorInfo;
import com.ef.jcpt.trade.dao.model.OrderInfo;
import com.ef.jcpt.trade.dao.model.PayInfo;
import com.ef.jcpt.trade.dao.model.PhoneSupportOperator;
import com.ef.jcpt.trade.service.IOrderPayService;
import com.ef.jcpt.trade.service.IWechatpayh5Service;
import com.ef.jcpt.trade.service.bo.FlowProductBo;
import com.ef.jcpt.trade.service.bo.OrderInfoBo;
import com.ef.jcpt.trade.service.bo.PayInfoBo;
import com.ef.jcpt.trade.service.bo.PhoneSupportOperatorBo;

@Service
public class OrderPayServiceImpl implements IOrderPayService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private OperatorInfoMapper operatorInfoMapper;

	@Autowired
	private OrderInfoComponent orderInfoComponent;

	@Autowired
	private FlowProductComponent flowProductComponent;

	@Autowired
	private PhoneSupportOperatorComponent phoneSupportOperatorComponent;

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
	public int countProductByPage(String userNationCode, String buyNationCode, String productType) {
		// TODO Auto-generated method stub
		return flowProductComponent.countOrderByPage(userNationCode, buyNationCode, productType);

	}

	@Override
	public BasicServiceModel<List<FlowProductBo>> listProductByPage(String userNationCode, String buyNationCode,
			String productType, int indexPage, int pageSize) {
		BasicServiceModel<List<FlowProductBo>> bsm = new BasicServiceModel<List<FlowProductBo>>();
		// TODO Auto-generated method stub
		int start = (indexPage - 1) * pageSize;
		if (start < 0) {
			start = 0;
		}
		List<Map> list = flowProductComponent.listProductByPage(userNationCode, buyNationCode, productType, start,
				pageSize);
		if (null != list) {
			List<FlowProductBo> boList = new ArrayList<FlowProductBo>();
			for (Map map : list) {
				FlowProductBo bo = new FlowProductBo();
				bo.setId((Integer) map.get("id"));
				bo.setProductName((String) map.get("product_name"));
				bo.setProductType((String) map.get("product_type"));
				bo.setPrice((BigDecimal) map.get("price"));
				bo.setPreferentialPrice((BigDecimal) map.get("discount_price"));
				bo.setProductTerm((Date) map.get("product_term"));
				bo.setProductInstruction((String) map.get("product_instruction"));
				bo.setRemark((String) map.get("remark"));
				boList.add(bo);
			}
			bsm.setData(boList);
		}
		bsm.setCode(ReqStatusConst.OK);

		return bsm;
	}

	@Override
	public int countOrderByPage(String userName, String nationCode, String orderStatus) {
		// TODO Auto-generated method stub
		return orderInfoComponent.countOrderByPage(userName, nationCode, orderStatus);

	}

	@Override
	public BasicServiceModel<List<OrderInfoBo>> listOrderByPage(String userName, String nationCode, String orderStatus,
			int indexPage, int pageSize) {
		BasicServiceModel<List<OrderInfoBo>> bsm = new BasicServiceModel<List<OrderInfoBo>>();
		// TODO Auto-generated method stub
		int start = (indexPage - 1) * pageSize;
		if (start < 0) {
			start = 0;
		}
		List<OrderInfo> list = orderInfoComponent.listOrderByPage(userName, nationCode, orderStatus, start, pageSize);
		if (null != list) {
			List<OrderInfoBo> boList = new ArrayList<OrderInfoBo>();
			for (OrderInfo info : list) {
				OrderInfoBo bo = new OrderInfoBo();
				BeanUtils.copyProperties(info, bo);
				boList.add(bo);
			}
			bsm.setData(boList);
		}
		bsm.setCode(ReqStatusConst.OK);

		return bsm;
	}

	@Override
	public BasicServiceModel<List<PhoneSupportOperatorBo>> listOperator(String phoneModel, String nationCode) {
		// TODO Auto-generated method stub
		BasicServiceModel<List<PhoneSupportOperatorBo>> bsm = new BasicServiceModel<List<PhoneSupportOperatorBo>>();

		BasicServiceModel<List<PhoneSupportOperator>> bsmRet = phoneSupportOperatorComponent.listOperator(phoneModel,
				nationCode);
		if ((null != bsmRet) && (ReqStatusConst.OK.equals(bsmRet.getCode()))) {
			List<PhoneSupportOperator> list = bsmRet.getData();
			if (null != list) {
				List<PhoneSupportOperatorBo> boList = new ArrayList<PhoneSupportOperatorBo>();
				for (PhoneSupportOperator info : list) {
					PhoneSupportOperatorBo bo = new PhoneSupportOperatorBo();
					BeanUtils.copyProperties(info, bo);
					boList.add(bo);
				}
				bsm.setData(boList);
			}
			bsm.setCode(ReqStatusConst.OK);
		} else {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg(bsmRet.getMsg());
		}
		return bsm;
	}

	@Override
	public BasicServiceModel<String> updateOrderOperatorByOrderId(String orderId, int operatorId) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		OperatorInfo operInfo = operatorInfoMapper.selectByPrimaryKey(operatorId);
		String operatorName = "";
		if (null != operInfo) {
			operatorName = operInfo.getOperatorName();
		}
		int ret = orderInfoComponent.updateOrderOperatorByOrderId(orderId, operatorId, operatorName);
		if (ret == 1) {
			bsm.setCode(ReqStatusConst.OK);
		} else {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("修改失败");
		}
		return bsm;
	}
}
