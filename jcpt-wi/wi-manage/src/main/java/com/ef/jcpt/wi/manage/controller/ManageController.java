package com.ef.jcpt.wi.manage.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.core.cache.CacheUtil;
import com.ef.jcpt.trade.service.IOrderPayService;
import com.ef.jcpt.trade.service.bo.FlowProductBo;

@Controller
@RequestMapping("/manage")
public class ManageController extends BaseController {

	@Autowired
	IOrderPayService orderPayServiceImpl;

	@Autowired
	private CacheUtil cacheUtil;

	@Value("${product.file.upload.path}")
	private String path;

	@RequestMapping(value = "/publishProduct.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> publishProduct(HttpServletRequest req, @RequestParam("tokenKey") String tokenKey,
			@RequestParam("preferentialPrice") BigDecimal preferentialPrice, @RequestParam("price") BigDecimal price,
			@RequestParam("productInstruction") String productInstruction,
			@RequestParam("productName") String productName, @RequestParam("productNum") int productNum,
			@RequestParam("productStatus") String productStatus, @RequestParam("productTerm") int productTerm,
			@RequestParam("productType") String productType, @RequestParam("remark") String remark,
			@RequestParam("useArea") String useArea, @RequestParam("backFile") MultipartFile backFile) {
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
			bo.setPreferentialPrice(preferentialPrice);
			bo.setPrice(price);
			bo.setProductInstruction(productInstruction);
			bo.setProductName(productName);
			bo.setProductNum(productNum);
			bo.setProductStatus(productStatus);
			bo.setProductTerm(productTerm);
			bo.setProductType(productType);
			bo.setRemark(remark);
			bo.setUpdateTime(curTime);
			bo.setCreateTime(curTime);
			bo.setUseArea(useArea);

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
}
