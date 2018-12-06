package com.ef.jcpt.wi.manage.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ef.jcpt.common.constant.PopadsCountKeyConst;
import com.ef.jcpt.common.constant.PopadsCountTypeConst;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.common.util.StringUtil;
import com.ef.jcpt.core.cache.CacheUtil;
import com.ef.jcpt.core.entity.TokenVo;
import com.ef.jcpt.manage.service.IAdspopService;
import com.ef.jcpt.manage.service.bo.AdspopPublishBo;
import com.ef.jcpt.manage.service.bo.MixJSBo;

@Controller
@RequestMapping("/adspop")
@CrossOrigin
public class AdsPopManageController extends BaseController {

	@Autowired
	IAdspopService adspopServiceImpl;

	@Autowired
	private CacheUtil cacheUtil;

	@Value("${adspop.file.upload.path}")
	private String path;

	@RequestMapping(value = "/publish.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> publish(HttpServletRequest req, @RequestParam("tokenKey") String tokenKey,
			@RequestParam("modelId") String modelId, @RequestParam("modelName") String modelName,
			@RequestParam("taskName") String taskName, @RequestParam("taskDesc") String taskDesc,
			@RequestParam("taskUrl") String taskUrl, @RequestParam("publishUser") String publishUser,
			@RequestParam("publishPhone") String publishPhone, @RequestParam("popMode") String popMode,
			@RequestParam("remark") String remark, @RequestParam("intervalTime") String intervalTime,
			@RequestParam("validEndTime") String validEndTime, @RequestParam("validStartTime") String validStartTime,
			@RequestParam("province") String province, @RequestParam("city") String city,
			@RequestParam("taskImageFile") MultipartFile taskImageFile) {
		String cmd = "AdsPopManageController:publish";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			// TokenVo tokenVo = RedisUtil.getToken(token);
			// if (null != tokenVo) {
			String origTaskImageFileName = taskImageFile.getOriginalFilename();

			long curTime = System.currentTimeMillis();

			File pathDir = new File(path);
			if (!pathDir.exists()) {// 如果文件夹不存在
				pathDir.mkdirs();// 创建文件夹
			}

			String taskImageFileName = curTime + "_" + origTaskImageFileName;

			String taskImageFilePath = path + taskImageFileName;

			File saveBackFile = new File(taskImageFilePath);
			// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
			taskImageFile.transferTo(saveBackFile);

			AdspopPublishBo bo = new AdspopPublishBo();

			bo.setModelId(modelId);
			bo.setModelName(modelName);
			bo.setPopMode(popMode);
			bo.setPublishPhone(publishPhone);
			bo.setPublishUser(publishUser);
			bo.setRemark(remark);
			bo.setTaskDesc(taskDesc);
			bo.setTaskImageFilePath(taskImageFilePath);
			bo.setTaskImageFileName(taskImageFileName);
			bo.setTaskName(taskName);
			bo.setTaskUrl(taskUrl);
			bo.setIntervalTime(intervalTime);
			bo.setValidEndTime(validEndTime);
			bo.setValidStartTime(validStartTime);
			bo.setProvince(province);
			bo.setCity(city);

			bsm = adspopServiceImpl.publishAdspop(bo);
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

	@RequestMapping(value = "/getModelIdAndNamePair.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> getModelIdAndNamePair(HttpServletRequest req, String sign, String params) {
		String cmd = "AdsPopManageController:getModelIdAndNamePair";
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
				// if (null != token) {
				// UserInfoBo bo = token.getUser();
				// String userName = bo.getMobile();

				return adspopServiceImpl.getModeIdNamePair();
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping(value = "/audit.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> audit(HttpServletRequest req, String sign, String params) {
		String cmd = "AdsPopManageController:audit";
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
				String popadsId = jsonObj.getString("popadsId");
				String auditUser = jsonObj.getString("auditUser");
				String auditResult = jsonObj.getString("auditResult");
				String auditAdvise = jsonObj.getString("auditAdvise");

				// TokenVo token = cacheUtil.getToken(tokenKey);
				// if (null != token) {
				// UserInfoBo bo = token.getUser();
				// String userName = bo.getMobile();

				return adspopServiceImpl.audit(Integer.parseInt(popadsId), auditResult, auditUser, auditAdvise);
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping(value = "/realse.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> realse(HttpServletRequest req, String sign, String params) {
		String cmd = "AdsPopManageController:realse";
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
				JSONArray popadsIds = jsonObj.getJSONArray("popadsIds");
				JSONArray mixjss = jsonObj.getJSONArray("mixjss");

				int len = popadsIds.size();
				int[] ids = new int[len];
				for (int i = 0; i < len; i++) {
					ids[i] = (int) popadsIds.get(i);
				}

				List<MixJSBo> list = null;
				if ((null != mixjss) && (mixjss.size() > 0)) {
					list = mixjss.toJavaList(MixJSBo.class);
				}

				// TokenVo token = cacheUtil.getToken(tokenKey);
				// if (null != token) {
				// UserInfoBo bo = token.getUser();
				// String userName = bo.getMobile();

				return adspopServiceImpl.realse(ids, list);
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping(value = "/listPopads.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> listPopads(HttpServletRequest req, String sign, String params) {
		String cmd = "AdsPopManageController:listPopads";
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
				// TokenVo token = cacheUtil.getToken(tokenKey);
				// if (null != token) {
				// UserInfoBo bo = token.getUser();
				// String userName = bo.getMobile();

				String taskNameParam = jsonObj.getString("taskName");
				String taskStatusParam = jsonObj.getString("taskStatus");
				String modelIdStr = jsonObj.getString("modelId");

				String taskName = null;
				String taskStatus = null;
				int modelId = -1;
				if (StringUtil.isNotEmpty(taskNameParam)) {
					taskName = taskNameParam;
				}
				if (StringUtil.isNotEmpty(taskStatusParam)) {
					taskStatus = taskStatusParam;
				}

				if (StringUtil.isNotEmpty(modelIdStr)) {
					modelId = Integer.parseInt(modelIdStr);
				}
				int pageIndex = jsonObj.getIntValue("pageIndex");
				int pageSize = jsonObj.getIntValue("pageSize");
				int total = 0;
				if (pageIndex == 1) {
					total = adspopServiceImpl.countPopadsByPage(taskName, taskStatus, modelId);
				}
				BasicServiceModel<String> orderBsm = adspopServiceImpl.listPopadsByPage(taskName, taskStatus, modelId,
						pageIndex, pageSize);
				if ((null != orderBsm) && (ReqStatusConst.OK.equals(orderBsm.getCode()))) {
					String listProStr = orderBsm.getData();
					JSONObject listjson = JSONObject.parseObject(listProStr);
					String arrays = listjson.getString("data");
					JSONObject data = new JSONObject();
					data.put("total", total);
					data.put("popadsList", arrays);
					bsm.setCode(ReqStatusConst.OK);
					bsm.setData(data.toJSONString());
					return bsm;
				} else {
					bsm.setCode(orderBsm.getCode());
					bsm.setMsg(orderBsm.getMsg());
					return bsm;
				}
				// } else {
				// bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
				// bsm.setMsg("会话已过期，请重新登录！");
				// logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg()
				// + ",data=" + params));
				// return bsm;
				// }
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取广告信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping(value = "/getPopadsById.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> getPopadsById(HttpServletRequest req, String sign, String params) {
		String cmd = "AdsPopManageController:getPopadsById";
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
				// TokenVo token = cacheUtil.getToken(tokenKey);
				// if (null != token) {
				// UserInfoBo bo = token.getUser();
				// String userName = bo.getMobile();

				String popadsId = jsonObj.getString("popadsId");

				return adspopServiceImpl.getPopadsById(popadsId);

				// } else {
				// bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
				// bsm.setMsg("会话已过期，请重新登录！");
				// logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg()
				// + ",data=" + params));
				// return bsm;
				// }
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg(e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping(value = "/updatePopads.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> updatePopads(HttpServletRequest req, @RequestParam("popadsId") int popadsId,
			@RequestParam("tokenKey") String tokenKey, @RequestParam("modelId") String modelId,
			@RequestParam("modelName") String modelName, @RequestParam("taskName") String taskName,
			@RequestParam("taskDesc") String taskDesc, @RequestParam("taskUrl") String taskUrl,
			@RequestParam("publishUser") String publishUser, @RequestParam("publishPhone") String publishPhone,
			@RequestParam("popMode") String popMode, @RequestParam("remark") String remark,
			@RequestParam("intervalTime") String intervalTime, @RequestParam("validEndTime") String validEndTime,
			@RequestParam("validStartTime") String validStartTime, @RequestParam("province") String province,
			@RequestParam("city") String city,
			@RequestParam(value = "taskImageFile", required = false) MultipartFile taskImageFile) {
		String cmd = "AdsPopManageController:updatePopads";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			// TokenVo tokenVo = RedisUtil.getToken(token);
			// if (null != tokenVo) {
			AdspopPublishBo bo = new AdspopPublishBo();
			bo.setId(popadsId);
			if (null != taskImageFile) {
				String origTaskImageFileName = taskImageFile.getOriginalFilename();

				long curTime = System.currentTimeMillis();

				File pathDir = new File(path);
				if (!pathDir.exists()) {// 如果文件夹不存在
					pathDir.mkdirs();// 创建文件夹
				}

				String taskImageFileName = curTime + "_" + origTaskImageFileName;
				String taskImageFilePath = path + taskImageFileName;

				File saveBackFile = new File(taskImageFilePath);
				// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
				taskImageFile.transferTo(saveBackFile);
				bo.setTaskImageFilePath(taskImageFilePath);
				bo.setTaskImageFileName(taskImageFileName);
			}

			if (StringUtil.isNotEmpty(modelId)) {
				bo.setModelId(modelId);
			}
			if (StringUtil.isNotEmpty(modelName)) {
				bo.setModelName(modelName);
			}
			if (StringUtil.isNotEmpty(popMode)) {
				bo.setPopMode(popMode);
			}
			if (StringUtil.isNotEmpty(publishPhone)) {
				bo.setPublishPhone(publishPhone);
			}
			if (StringUtil.isNotEmpty(publishUser)) {
				bo.setPublishUser(publishUser);
			}
			if (StringUtil.isNotEmpty(remark)) {
				bo.setRemark(remark);
			}
			if (StringUtil.isNotEmpty(taskDesc)) {
				bo.setTaskDesc(taskDesc);
			}
			if (StringUtil.isNotEmpty(taskName)) {
				bo.setTaskName(taskName);
			}
			if (StringUtil.isNotEmpty(taskUrl)) {
				bo.setTaskUrl(taskUrl);
			}
			if (StringUtil.isNotEmpty(intervalTime)) {
				bo.setIntervalTime(intervalTime);
			}
			if (StringUtil.isNotEmpty(validEndTime)) {
				bo.setValidEndTime(validEndTime);
			}
			if (StringUtil.isNotEmpty(validStartTime)) {
				bo.setValidStartTime(validStartTime);
			}
			if (StringUtil.isNotEmpty(province)) {
				bo.setProvince(province);
			}
			if (StringUtil.isNotEmpty(city)) {
				bo.setCity(city);
			}

			bsm = adspopServiceImpl.updatePopads(bo);
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

	@RequestMapping(value = "/viewpop.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> viewpop(HttpServletRequest req, @RequestParam("mac") String mac,
			@RequestParam("taskId") String taskId) {
		String cmd = "AdsPopManageController:viewpop";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		int intervalTime = 3 * 24 * 60 * 60;
		try {
			String key = PopadsCountKeyConst.VIEW + taskId;
			Integer viewCount = cacheUtil.get(key, Integer.TYPE);
			if ((null != viewCount) && (viewCount > 0)) {
				cacheUtil.set(key, viewCount + 1, intervalTime);
			} else {
				cacheUtil.set(key, 1, intervalTime);
				viewCount = 0;
			}
			try {
				adspopServiceImpl.addViewAndClickCountLog(taskId, mac, PopadsCountTypeConst.VIEW);
			} catch (Exception e) {
				e.printStackTrace();
			}
			bsm.setCode(ReqStatusConst.OK);
			bsm.setData(String.valueOf(viewCount + 1));
			return bsm;
		} catch (Exception e) {
			e.printStackTrace();
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg(e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg()));
			return bsm;
		}
	}

	@RequestMapping(value = "/clickpop.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> clickpop(HttpServletRequest req, @RequestParam("mac") String mac,
			@RequestParam("taskId") String taskId) {
		String cmd = "AdsPopManageController:clickpop";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		int intervalTime = 3 * 24 * 60 * 60;
		try {
			String key = PopadsCountKeyConst.CLICK + taskId;
			Integer clickCount = cacheUtil.get(key, Integer.TYPE);
			if ((null != clickCount) && (clickCount > 0)) {
				cacheUtil.set(key, clickCount + 1, intervalTime);
			} else {
				cacheUtil.set(key, 1, intervalTime);
				clickCount = 0;
			}
			try {
				adspopServiceImpl.addViewAndClickCountLog(taskId, mac, PopadsCountTypeConst.CLICK);
			} catch (Exception e) {
				e.printStackTrace();
			}
			bsm.setCode(ReqStatusConst.OK);
			bsm.setData(String.valueOf(clickCount + 1));
			return bsm;

		} catch (Exception e) {
			e.printStackTrace();
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg(e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg()));
			return bsm;
		}
	}

	@RequestMapping(value = "/getViewAndClickCount.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> getViewAndClickCount(HttpServletRequest req, String sign, String params) {
		String cmd = "AdsPopManageController:getViewAndClickCount";
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
				String taskId = jsonObj.getString("taskId");
				// TokenVo token = cacheUtil.getToken(tokenKey);
				// if (null != token) {
				// UserInfoBo bo = token.getUser();
				// String userName = bo.getMobile();

				String viewKey = PopadsCountKeyConst.VIEW + taskId;
				String clickKey = PopadsCountKeyConst.CLICK + taskId;
				Integer viewCount = cacheUtil.get(viewKey, Integer.TYPE);
				Integer clickCount = cacheUtil.get(clickKey, Integer.TYPE);
				Map<String, String> map = new HashMap<String, String>();
				map.put("viewCount", String.valueOf(viewCount));
				map.put("clickCount", String.valueOf(clickCount));
				bsm.setCode(ReqStatusConst.OK);
				bsm.setData(JSONObject.toJSONString(map));
				return bsm;
			}
		} catch (Exception e) {
			e.printStackTrace();
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg(e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg()));
			return bsm;
		}
	}
}
