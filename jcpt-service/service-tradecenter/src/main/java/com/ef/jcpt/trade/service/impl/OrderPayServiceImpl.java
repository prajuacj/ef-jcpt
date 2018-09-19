package com.ef.jcpt.trade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.FlowKeyConst;
import com.ef.jcpt.common.constant.OrderStatusConst;
import com.ef.jcpt.common.constant.PayChannelConst;
import com.ef.jcpt.common.constant.PayStatusConst;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.util.BeanUtil;
import com.ef.jcpt.common.util.DateUtil;
import com.ef.jcpt.core.redis.IDProvider;
import com.ef.jcpt.trade.component.FlowProductComponent;
import com.ef.jcpt.trade.component.OrderInfoComponent;
import com.ef.jcpt.trade.component.PayInfoComponent;
import com.ef.jcpt.trade.component.PhoneSupportOperatorComponent;
import com.ef.jcpt.trade.dao.OperatorInfoMapper;
import com.ef.jcpt.trade.dao.OrderInfoMapper;
import com.ef.jcpt.trade.dao.PayInfoMapper;
import com.ef.jcpt.trade.dao.model.FlowProduct;
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

	private Logger logger = LoggerFactory.getLogger(OrderPayServiceImpl.class);

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
	public BasicServiceModel<String> toPay(OrderInfoBo origBo, String code, String ip) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			String productId = origBo.getProductId();
			BigDecimal origdiscountPrice = origBo.getDiscountAmount();
			int productNum = origBo.getProductNum();
			BigDecimal origpayAmount = origBo.getPayAmount();
			String userNationCode = origBo.getUserNationCode();
			String productNationCode = origBo.getProductNationCode();
			String userId = origBo.getUserId();
			String mobile = origBo.getMobile();

			List<Map> productList = flowProductComponent.selectProductAndPrice(userNationCode, productNationCode,
					Integer.parseInt(productId));
			if ((null != productList) && (productList.size() == 1)) {
				Map prodMap = productList.get(0);
				String productName = (String) prodMap.get("product_name");
				String productType = (String) prodMap.get("product_type");
				BigDecimal productPrice = (BigDecimal) prodMap.get("price");
				BigDecimal poductDisPrice = (BigDecimal) prodMap.get("discount_price");
				int productTerm = (int) prodMap.get("product_term");
				int productFlows = (int) prodMap.get("product_num");
				String productInstruction = (String) prodMap.get("product_instruction");

				BigDecimal payAmount = poductDisPrice.multiply(BigDecimal.valueOf(productNum));
				long remainFlow = productFlows * productNum;
				BigDecimal totalAmount = productPrice.multiply(BigDecimal.valueOf(productNum));

				OrderInfo info = new OrderInfo();
				info.setDiscountAmount(poductDisPrice);
				info.setMobile(mobile);
				// info.setOperatorId(operatorId);
				// info.setOperatorName(operatorName);
				info.setOperatorNationCode(productNationCode);
				// info.setOperatorNationName(operatorNationName);
				info.setPayAmount(payAmount);
				// info.setPayTime(payTime);
				info.setPrice(productPrice);
				info.setProductId(productId);
				info.setProductName(productName);
				info.setProductNum(productNum);
				info.setRemainFlow(BigDecimal.valueOf(remainFlow));
				info.setTotalAmount(totalAmount);
				info.setUserId(userId);
				// info.setValidTime(validTime);

				OrderInfo retOrder = null;
				try {
					retOrder = orderInfoComponent.genOrderInfo(info);

					PayInfo payInfo = payInfoComponent.genPayInfo(retOrder);
					PayInfoBo payBo = new PayInfoBo();
					BeanUtil.copyProperties(payInfo, payBo);

					Map<String, String> map = wechathpayh5ServiceImpl.toPay(payBo, code, ip);
					if (null != map) {
						bsm.setCode(ReqStatusConst.OK);
						JSONObject json = new JSONObject();
						json.put("timeStamp", map.get("timeStamp"));
						json.put("nonceStr", map.get("nonceStr"));
						json.put("package", map.get("package"));
						json.put("signType", map.get("signType"));
						json.put("paySign", map.get("paySign"));
						bsm.setData(json.toString());

						Date curDate = new Date(System.currentTimeMillis());
						retOrder.setPayTime(curDate);
						retOrder.setValidTime(DateUtil.addDay(curDate, (int) productTerm));
						orderInfoComponent.updateOrderInfo(retOrder);
						return bsm;
					} else {
						bsm.setCode(ReqStatusConst.FAIL);
						bsm.setMsg("获取支付数据失败！");
						return bsm;
					}
				} catch (Exception e) {
					e.printStackTrace();
					bsm.setCode(ReqStatusConst.FAIL);
					bsm.setMsg(e.getMessage());
					return bsm;
				}
			} else {
				bsm.setCode(ReqStatusConst.FAIL);
				bsm.setMsg("购买的产品不正确！");
				return bsm;
			}
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
	public BasicServiceModel<String> listProductByPage(String userNationCode, String buyNationCode, String productType,
			int indexPage, int pageSize) {
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
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
				bo.setId((Long) map.get("id"));
				bo.setProductName((String) map.get("product_name"));
				bo.setProductType((String) map.get("product_type"));
				bo.setPrice((BigDecimal) map.get("price"));
				bo.setPreferentialPrice((BigDecimal) map.get("discount_price"));
				bo.setProductTerm((int) map.get("product_term"));
				bo.setProductInstruction((String) map.get("product_instruction"));
				bo.setRemark((String) map.get("remark"));
				boList.add(bo);
			}
			JSONObject data = new JSONObject();
			data.put("data", boList);
			bsm.setData(data.toString());
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
	public BasicServiceModel<String> listOrderByPage(String userName, String nationCode, String orderStatus,
			int indexPage, int pageSize) {
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
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
			JSONObject data = new JSONObject();
			data.put("data", boList);
			bsm.setData(data.toString());
		}
		bsm.setCode(ReqStatusConst.OK);

		return bsm;
	}

	@Override
	public BasicServiceModel<String> listOperator(String phoneModel, String nationCode) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		List<PhoneSupportOperator> list = phoneSupportOperatorComponent.listOperator(phoneModel, nationCode);

		if (null != list) {
			List<PhoneSupportOperatorBo> boList = new ArrayList<PhoneSupportOperatorBo>();
			for (PhoneSupportOperator info : list) {
				PhoneSupportOperatorBo bo = new PhoneSupportOperatorBo();
				BeanUtils.copyProperties(info, bo);
				boList.add(bo);
			}
			String listStr = JSONObject.toJSONString(boList);
			bsm.setData(listStr);
		}
		bsm.setCode(ReqStatusConst.OK);
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BasicServiceModel<String> updateWXPayResult(String sn, String orderAmtStr, String settleAmtStr,
			String wxorderid, String endTime, String bankType) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(sn);
		PayInfo payInfo = payInfoMapper.selectByOrderId(sn);

		if ((null != payInfo) && (null != orderInfo)) {
			String payStatus = payInfo.getPayStatus();
			String orderStatus = orderInfo.getOrderStatus();
			if ((PayStatusConst.INIT.equals(payStatus)) && (OrderStatusConst.INIT.equals(orderStatus))) {

				BigDecimal payAmt = payInfo.getPayAmount();
				BigDecimal settleAmt = new BigDecimal(settleAmtStr).divide(BigDecimal.valueOf(100));
				if (payAmt.compareTo(settleAmt) == 0) {
					payInfo.setPayChannel(PayChannelConst.WX);
					payInfo.setReturnFlow(wxorderid);
					payInfo.setUpdateTime(new Date(System.currentTimeMillis()));
					payInfo.setPayStatus(PayStatusConst.SUCCESS);
					payInfo.setPayMemo(bankType);

					payInfoMapper.updateByPrimaryKeySelective(payInfo);

					orderInfo.setOrderStatus(OrderStatusConst.PAYED);
					orderInfo.setPayTime(new Date(System.currentTimeMillis()));
					orderInfoMapper.updateByPrimaryKeySelective(orderInfo);

					bsm.setCode(ReqStatusConst.OK);
					return bsm;
				}
			} else {
				logger.info("已经更新了支付结果：updateWXPayResult:" + payStatus);
				bsm.setCode(ReqStatusConst.OK);
				return bsm;
			}
		}

		bsm.setCode(ReqStatusConst.FAIL);
		bsm.setMsg("修改失败");

		return bsm;
	}

	@Override
	public BasicServiceModel<String> publishProduct(FlowProductBo bo) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		FlowProduct info = new FlowProduct();
		BeanUtils.copyProperties(bo, info);
		info = flowProductComponent.publishProduct(info);
		int key = info.getId();
		bsm.setCode(ReqStatusConst.OK);
		bsm.setData(String.valueOf(key));
		return bsm;
	}
}
