package com.ef.jcpt.wi.manage.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.common.util.StringUtil;
import com.ef.jcpt.core.cache.CacheUtil;
import com.ef.jcpt.core.entity.TokenVo;
import com.ef.jcpt.manage.service.IManageService;
import com.ef.jcpt.manage.service.bo.OperatorInfoBo;
import com.ef.jcpt.manage.service.bo.PhoneSupportStandardBo;
import com.ef.jcpt.trade.service.IOrderPayService;
import com.ef.jcpt.trade.service.bo.FlowProductBo;
import com.ef.jcpt.user.service.bo.UserInfoBo;

@Controller
@RequestMapping("/manage")
@CrossOrigin
public class ManageController extends BaseController {

	@Autowired
	IOrderPayService orderPayServiceImpl;

	@Autowired
	IManageService manageServiceImpl;

	@Autowired
	private CacheUtil cacheUtil;

	@Value("${product.file.upload.path}")
	private String path;

	@RequestMapping(value = "/publishProduct.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> publishProduct(HttpServletRequest req, @RequestParam("tokenKey") String tokenKey,
			@RequestParam("prices") String prices, @RequestParam("productInstruction") String productInstruction,
			@RequestParam("productName") String productName, @RequestParam("productNum") int productNum,
			@RequestParam("productStatus") String productStatus, @RequestParam("productTerm") short productTerm,
			@RequestParam("productType") String productType, @RequestParam("remark") String remark,
			@RequestParam("backFile") MultipartFile backFile) {
		String cmd = "ManageController:publishProduct";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			// TokenVo tokenVo = RedisUtil.getToken(token);
			// if (null != tokenVo) {
			String backFileName = backFile.getOriginalFilename();

			Date curTime = new Date(System.currentTimeMillis());

			String backFilePath = path + backFileName;

			File saveBackFile = new File(backFilePath);
			// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
			backFile.transferTo(saveBackFile);

			FlowProductBo bo = new FlowProductBo();

			bo.setBackFile(backFilePath);
			bo.setProductInstruction(productInstruction);
			bo.setProductName(productName);
			bo.setProductNum(productNum);
			bo.setProductStatus(productStatus);
			bo.setProductTerm(productTerm);
			bo.setProductType(productType);
			bo.setPrices(prices);
			bo.setRemark(remark);
			bo.setUpdateTime(curTime);
			bo.setCreateTime(curTime);

			bsm = orderPayServiceImpl.publishProduct(bo);
			logger.info(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), "return=" + JSON.toJSONString(bsm)));
			return bsm;
			// } else {
			// bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
			// bsm.setMsg("token已过期，请重新申请！");
			// logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg()
			// + ",data=" + params));
			// return bsm;
			// }
		} catch (Exception e) {
			e.printStackTrace();
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("上传文件失败！");
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg()));
			return bsm;
		}
	}

	@RequestMapping(value = "/getPhoneModelSupportStandard.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> getPhoneModel(HttpServletRequest req, String sign, String params) {
		String cmd = "ManageController:getPhoneModel";
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
//				if (null != token) {
//				UserInfoBo bo = token.getUser();
//				String userName = bo.getMobile();

				int pageIndex = jsonObj.getIntValue("pageIndex");
				int pageSize = jsonObj.getIntValue("pageSize");
				int total = 0;
				if (pageIndex == 1) {
					total = manageServiceImpl.countPhoneModelSupportStandardByPage();
				}
				BasicServiceModel<String> orderBsm = manageServiceImpl.listPhoneModelSupportStandardByPage(pageIndex,
						pageSize);
				if ((null != orderBsm) && (ReqStatusConst.OK.equals(orderBsm.getCode()))) {
					String listProStr = orderBsm.getData();
					JSONObject listjson = JSONObject.parseObject(listProStr);
					String arrays = listjson.getString("data");
					JSONObject data = new JSONObject();
					data.put("total", total);
					data.put("phoneModelList", arrays);
					bsm.setCode(ReqStatusConst.OK);
					bsm.setData(data.toJSONString());
					return bsm;
				} else {
					bsm.setCode(orderBsm.getCode());
					bsm.setMsg(orderBsm.getMsg());
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
			bsm.setMsg("获取信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping("/configPhoneSupportStandard.json")
	@ResponseBody
	public BasicServiceModel<String> configPhoneSupportStandard(HttpServletRequest req, String sign, String params) {
		String cmd = "ManageController:configPhoneSupportStandard";
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
//				if (null != token) {
//				UserInfoBo user = token.getUser();
//				String userName = user.getMobile();
//				String userNationCode = user.getNationCode();

				JSONArray phoneModels = jsonObj.getJSONArray("phoneModels");
				for (Object phoneModelStr : phoneModels) {
					JSONObject phoneObj = (JSONObject) phoneModelStr;

					String phoneModel = phoneObj.getString("phoneModel");
					String standards = phoneObj.getString("standards");
					if (StringUtil.isNotEmpty(standards)) {
						String[] standardStrs = standards.split("\\,");
						if ((null != standardStrs) && (standardStrs.length > 0)) {
							for (String standardStr : standardStrs) {
								PhoneSupportStandardBo bo = new PhoneSupportStandardBo();
								Date curTime = new Date(System.currentTimeMillis());

								bo.setCreateTime(curTime);
								bo.setMobileNetworkStandard(standardStr);
								bo.setPhoneModel(phoneModel);
								bo.setUpdateTime(curTime);

								manageServiceImpl.configPhoneSupportStandard(bo);
							}
						}
					}
				}
				bsm.setCode(ReqStatusConst.OK);
				return bsm;
//				} else {
//					bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
//					bsm.setMsg("会话已过期，请重新登录！");
//					logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
//					return bsm;
//				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("支付失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping("/configOperatorSupportStandard.json")
	@ResponseBody
	public BasicServiceModel<String> configOperatorSupportStandard(HttpServletRequest req, String sign, String params) {
		String cmd = "ManageController:configOperatorSupportStandard";
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
//				if (null != token) {
				UserInfoBo user = token.getUser();
				String userName = user.getMobile();
				String userNationCode = user.getNationCode();

				JSONArray phoneModels = jsonObj.getJSONArray("operators");
				for (Object phoneModelStr : phoneModels) {
					JSONObject phoneObj = (JSONObject) phoneModelStr;

					String operatorName = phoneObj.getString("operator");
					String nationCode = phoneObj.getString("nationCode");
					String standards = phoneObj.getString("standards");
					if (StringUtil.isNotEmpty(standards)) {
						String[] standardStrs = standards.split("\\,");
						if ((null != standardStrs) && (standardStrs.length > 0)) {
							for (String standardStr : standardStrs) {
								OperatorInfoBo bo = new OperatorInfoBo();
								Date curTime = new Date(System.currentTimeMillis());

								bo.setCreateTime(curTime);
								bo.setNationCode(nationCode);
								bo.setNetworkStandard(standards);
								bo.setOperatorName(operatorName);
								bo.setUpdateTime(curTime);

								manageServiceImpl.configOperatorSupportStandard(bo);
							}
						}
					}
				}
				bsm.setCode(ReqStatusConst.OK);
				return bsm;
//				} else {
//					bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
//					bsm.setMsg("会话已过期，请重新登录！");
//					logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
//					return bsm;
//				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("支付失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}
}
