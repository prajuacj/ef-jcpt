package com.ef.jcpt.wi.trade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ef.jcpt.common.entity.BasicServiceModel;

@Controller
@RequestMapping("/orderTrade")
public class OrderTradeController extends BaseController {

	@RequestMapping("/submitOrder")
	@ResponseBody
	public BasicServiceModel<String> submitOrder(
			@RequestParam(required = false, value = "mobileCode") String mobileCode,
			@RequestParam(required = false, value = "mobile") String mobile,
			@RequestParam(required = false, value = "password") String password,
			@RequestParam(required = false, value = "refereeId") String refereeId,
			@RequestParam(required = false, value = "goodsId") String goodsId,
			@RequestParam(required = false, value = "memberType") String memberType) {
		return null;
	}

	@RequestMapping("/toPay")
	@ResponseBody
	public BasicServiceModel<String> toPay(@RequestParam(required = false, value = "mobileCode") String mobileCode,
			@RequestParam(required = false, value = "mobile") String mobile,
			@RequestParam(required = false, value = "password") String password,
			@RequestParam(required = false, value = "refereeId") String refereeId,
			@RequestParam(required = false, value = "goodsId") String goodsId,
			@RequestParam(required = false, value = "memberType") String memberType) {
		return null;
	}

	@RequestMapping("/toPay")
	@ResponseBody
	public BasicServiceModel<String> listOrder(@RequestParam(required = true, value = "userId") String userId) {
		return null;
	}
}
