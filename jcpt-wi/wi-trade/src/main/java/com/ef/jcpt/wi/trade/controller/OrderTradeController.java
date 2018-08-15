package com.ef.jcpt.wi.trade.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.core.cache.CacheUtil;
import com.ef.jcpt.core.entity.TokenVo;
import com.ef.jcpt.trade.service.IOrderPayService;
import com.ef.jcpt.trade.service.bo.OrderInfoBo;
import com.ef.jcpt.user.service.bo.UserInfoBo;

@Controller
@RequestMapping("/orderTrade")
public class OrderTradeController extends BaseController {

	@Autowired
	IOrderPayService orderPayServiceImpl;

	@Autowired
	private CacheUtil cacheUtil;

	@RequestMapping(value = "/getOperatorInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> getOperatorInfo(HttpServletRequest req, String sign, String params) {
		String cmd = "OrderTradeController:getOperatorInfo";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			BasicServiceModel<String> result = validateSign(sign, params);
			if (ReqStatusConst.FAIL.equals(result.getCode())) {
				logger.error(
						LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				JSONObject jsonObj = JSONObject.parseObject(params);
				String tokenKey = jsonObj.getString("tokenKey");
//				TokenVo token = cacheUtil.getToken(tokenKey);
//				if (null != token) {
//					UserInfoBo bo = token.getUser();
//					String userName = bo.getMobile();

					String phoneModel = jsonObj.getString("mobileModel");
					return orderPayServiceImpl.listOperator(phoneModel, "86");
//				} else {
//					bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
//					bsm.setMsg("会话已过期，请重新登录！");
//					logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
//					return bsm;
//				}

			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取运营商信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping("/toPay.json")
	@ResponseBody
	public BasicServiceModel<String> toPay(HttpServletRequest req, String sign, String params) {
		String cmd = "OrderTradeController:toPay";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			BasicServiceModel<String> result = validateSign(sign, params);
			if (ReqStatusConst.FAIL.equals(result.getCode())) {
				logger.error(
						LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				JSONObject jsonObj = JSONObject.parseObject(params);
				String tokenKey = jsonObj.getString("tokenKey");
				TokenVo token = cacheUtil.getToken(tokenKey);
				if (null != token) {
					UserInfoBo bo = token.getUser();
					String userName = bo.getMobile();
					OrderInfoBo orderBo = new OrderInfoBo();
					orderBo.setProductId(jsonObj.getString("productId"));
					orderBo.setProductName(jsonObj.getString("productName"));
					orderBo.setDiscountAmount(jsonObj.getBigDecimal("discountPrice"));
					orderBo.setProductNum(jsonObj.getIntValue("productNum"));
					orderBo.setPayAmount(jsonObj.getBigDecimal("payAmount"));
					String code = jsonObj.getString("code");
					String ip = jsonObj.getString("ip");

					return orderPayServiceImpl.toPay(orderBo, code, ip);
				} else {
					bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
					bsm.setMsg("会话已过期，请重新登录！");
					logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
					return bsm;
				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("支付失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping(value = "/listOrder.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> listOrder(HttpServletRequest req, String sign, String params) {
		String cmd = "OrderTradeController:getOperatorInfo";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			BasicServiceModel<String> result = validateSign(sign, params);
			if (ReqStatusConst.FAIL.equals(result.getCode())) {
				logger.error(
						LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				JSONObject jsonObj = JSONObject.parseObject(params);
				String tokenKey = jsonObj.getString("tokenKey");
				TokenVo token = cacheUtil.getToken(tokenKey);
				if (null != token) {
					UserInfoBo bo = token.getUser();
					String userName = bo.getMobile();

					String nationCode = jsonObj.getString("nationCode");
					String orderStatus = jsonObj.getString("orderStatus");
					int pageIndex = jsonObj.getIntValue("pageIndex");
					int pageSize = jsonObj.getIntValue("pageSize");
					int total = 0;
					if (pageIndex == 1) {
						total = orderPayServiceImpl.countOrderByPage(userName, nationCode, orderStatus);
					}
					BasicServiceModel<String> orderBsm = orderPayServiceImpl.listOrderByPage(userName, nationCode,
							orderStatus, pageIndex, pageSize);
					if ((null != orderBsm) && (ReqStatusConst.OK.equals(orderBsm.getCode()))) {
						String listProStr = orderBsm.getData();
						JSONObject listjson = JSONObject.parseObject(listProStr);
						String arrays = listjson.getString("data");
						JSONObject data = new JSONObject();
						data.put("total", total);
						data.put("orderList", arrays);
						bsm.setCode(ReqStatusConst.OK);
						bsm.setData(data.toJSONString());
						return bsm;
					} else {
						bsm.setCode(orderBsm.getCode());
						bsm.setMsg(orderBsm.getMsg());
						return bsm;
					}
				} else {
					bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
					bsm.setMsg("会话已过期，请重新登录！");
					logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
					return bsm;
				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取订单信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping(value = "/updateOrderOperator.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> updateOrderOperator(HttpServletRequest req, String sign, String params) {
		String cmd = "OrderTradeController:getOperatorInfo";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			BasicServiceModel<String> result = validateSign(sign, params);
			if (ReqStatusConst.FAIL.equals(result.getCode())) {
				logger.error(
						LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				JSONObject jsonObj = JSONObject.parseObject(params);
				String tokenKey = jsonObj.getString("tokenKey");
				TokenVo token = cacheUtil.getToken(tokenKey);
				if (null != token) {
					UserInfoBo bo = token.getUser();
					String userName = bo.getMobile();

					String orderId = jsonObj.getString("orderId");
					int operatorId = jsonObj.getIntValue("operatorId");
					return orderPayServiceImpl.updateOrderOperatorByOrderId(orderId, operatorId);
				} else {
					bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
					bsm.setMsg("会话已过期，请重新登录！");
					logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
					return bsm;
				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("修改订单运营商失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping(value = "/listProduct.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> listProduct(HttpServletRequest req, String sign, String params) {
		String cmd = "OrderTradeController:getOperatorInfo";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			BasicServiceModel<String> result = validateSign(sign, params);
			if (ReqStatusConst.FAIL.equals(result.getCode())) {
				logger.error(
						LogTemplate.genCommonSysLogStr(cmd, result.getCode(), result.getMsg() + ",data=" + params));
				return result;
			} else {
				JSONObject jsonObj = JSONObject.parseObject(params);
				String tokenKey = jsonObj.getString("tokenKey");
//				TokenVo token = cacheUtil.getToken(tokenKey);
//				if (null != token) {
//					UserInfoBo bo = token.getUser();
//					String userName = bo.getMobile();

					String orderNationCode = jsonObj.getString("orderNationCode");
					String productType = jsonObj.getString("productType");
					int pageIndex = jsonObj.getIntValue("pageIndex");
					int pageSize = jsonObj.getIntValue("pageSize");
					int total = 0;
					if (pageIndex == 1) {
						total = orderPayServiceImpl.countProductByPage("86", orderNationCode, productType);
					}
					BasicServiceModel<String> productBsm = orderPayServiceImpl.listProductByPage("86", orderNationCode,
							productType, pageIndex, pageSize);
					if ((null != productBsm) && (ReqStatusConst.OK.equals(productBsm.getCode()))) {
						String listProStr = productBsm.getData();
						JSONObject listjson = JSONObject.parseObject(listProStr);
						String arrays = listjson.getString("data");
						JSONObject data = new JSONObject();
						data.put("total", total);
						data.put("productList", arrays);
						bsm.setCode(ReqStatusConst.OK);
						bsm.setData(data.toJSONString());
						return bsm;
					} else {
						bsm.setCode(productBsm.getCode());
						bsm.setMsg(productBsm.getMsg());
						return bsm;
					}
//				} else {
//					bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
//					bsm.setMsg("会话已过期，请重新登录！");
//					logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
//					return bsm;
//				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取产品信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}
}
