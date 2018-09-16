package com.ef.jcpt.wi.manage.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.ef.jcpt.common.constant.FlowKeyConst;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.core.cache.CacheUtil;
import com.ef.jcpt.core.redis.IDProvider;
import com.ef.jcpt.trade.service.IOrderPayService;

@Controller
@RequestMapping("/manage")
public class ManageController extends BaseController {

	@Autowired
	IOrderPayService orderPayServiceImpl;

	@Autowired
	private CacheUtil cacheUtil;

	@RequestMapping(value = "/publishProduct.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> publishProduct(HttpServletRequest req, @RequestParam("token") String token,
			@RequestParam("merchantCode") String merchantCode, @RequestParam("merchantName") String merchantName,
			@RequestParam("idType") String idType, @RequestParam("idNo") String idNo,
			@RequestParam("idName") String idName, @RequestParam("requestId") String requestId,
			@RequestParam("facadeIdFile") MultipartFile facadeIdFile,
			@RequestParam("backIdFile") MultipartFile backIdFile) {
		String cmd = "ManageController:publishProduct";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			// TokenVo tokenVo = RedisUtil.getToken(token);
			// if (null != tokenVo) {

			String facadeIdFileName = facadeIdFile.getOriginalFilename();
			String backIdFileName = backIdFile.getOriginalFilename();

			Date curTime = new Date(System.currentTimeMillis());
			String facadeIdFilePath = path + "/facade-" + idNo
					+ facadeIdFileName.substring(facadeIdFileName.indexOf("."));
			String backIdFilePath = path + "/back-" + idNo + backIdFileName.substring(backIdFileName.indexOf("."));

			File saveFacadeIdFile = new File(facadeIdFilePath);
			File saveBackIdFile = new File(backIdFilePath);
			// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
			facadeIdFile.transferTo(saveFacadeIdFile);
			backIdFile.transferTo(saveBackIdFile);

			OrderTicketUploadIdImgBo bo = new OrderTicketUploadIdImgBo();
			bo.setMerchantCode(merchantCode);
			bo.setMerchantName(merchantName);
			bo.setBackIdFileName(backIdFilePath);
			bo.setFacadeIdFileName(facadeIdFilePath);
			bo.setIdName(idName);
			bo.setIdNo(idNo);
			bo.setIdType(idType);
			bo.setRequestId(requestId);
			bo.setFlowId(IDProvider.getFlowId(FlowKeyConst.ORDER_TICKET_OTHER));
			bo.setCreateTime(curTime);
			bo.setUpdateTime(curTime);
			bo.setFacadeIdFileName(facadeIdFileName);
			bo.setBackIdFileName(backIdFileName);
			bsm = orderTicketApplyServiceImpl.saveOrderTicketUploadIdImg(bo);
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
