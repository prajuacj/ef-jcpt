package com.ef.jcpt.wi.trade.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.leimingtech.core.entity.PayCommon;
import com.leimingtech.core.entity.base.Pay;
import com.leimingtech.service.module.trade.common.PaymentTallyState;
import com.payment.core.alipay.h5.config.Alipayh5Contents;

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
	
	public void alipayH5Pay(HttpServletRequest request ,HttpServletResponse response){
		String paySn = request.getParameter("paySn");
		String backUrl = request.getParameter("backUrl");
		String os=request.getParameter("os");
		Pay pay = payService.findPayBySn(paySn);
		PayCommon payCommon = new PayCommon();
		payCommon.setOutTradeNo(pay.getPaySn());
		payCommon.setPayAmount(pay.getPayAmount());
		payCommon.setNotifyUrl(Alipayh5Contents.alipayh5backUrl);
		payCommon.setReturnUrl(Alipayh5Contents.alipayh5frontUrl);
		payCommon.setBackUrl(Alipayh5Contents.alipayh5BackUrl);
		try {
			String sHtmlText = "";
			if(StringUtils.isEmpty(paySn)){
				sHtmlText = "订单号不能为空";
			}else{
				//保存支付流水
				String osprefix=Alipayh5Contents.getOsBackUrl(os);
				cacheUtil.set(backurlcacheprefix+os+paySn, osprefix+backUrl);
				paymentTallyService.savePaymentTally("h5_alipay","支付宝", pay,PaymentTallyState.PAYMENTTALLY_TREM_H5);
				sHtmlText = alipayh5Service.toPay(payCommon);//构造提交支付宝的表单
				System.out.println("****"+sHtmlText);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(sHtmlText);
		}catch(IOException e) {
			log.error("",e);
		}
	}
}
