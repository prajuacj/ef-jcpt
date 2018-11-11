package com.ef.jcpt.wi.trade.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.constant.RewardType;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.log.LogTemplate;
import com.ef.jcpt.core.cache.CacheUtil;
import com.ef.jcpt.core.entity.TokenVo;
import com.ef.jcpt.trade.service.ITaskService;
import com.ef.jcpt.trade.service.bo.ReceivedTaskInfoBo;
import com.ef.jcpt.trade.service.bo.TaskInfoBo;
import com.ef.jcpt.user.service.bo.UserInfoBo;

@Controller
@RequestMapping("/taskTrade")
public class TaskTradeController extends BaseController {

	@Autowired
	private CacheUtil cacheUtil;

	@Autowired
	private ITaskService taskServiceImpl;

	@RequestMapping(value = "/getTask.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> getTask(HttpServletRequest req, String sign, String params) {
		String cmd = "TaskTradeController:getTask";
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
					int next = jsonObj.getIntValue("next");
					return taskServiceImpl.getTask(userName, next);
				} else {
					bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
					bsm.setMsg("会话已过期，请重新登录！");
					logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
					return bsm;
				}
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取产品信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + params));
			return bsm;
		}
	}

	@RequestMapping(value = "/submitTask.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> submitTask(HttpServletRequest req, String sign, String tokenKey, String taskId,
			String taskExplain, MultipartFile taskImg1, MultipartFile taskImg2) {
		String cmd = "TaskTradeController:submitTask";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			TokenVo token = cacheUtil.getToken(tokenKey);
			if (null != token) {
				UserInfoBo bo = token.getUser();
				String userName = bo.getMobile();

				ReceivedTaskInfoBo rtbo = new ReceivedTaskInfoBo();
				rtbo.setFinishedTime(new Date(System.currentTimeMillis()));
				rtbo.setReceivedUser(userName);
				rtbo.setTaskId(Integer.parseInt(taskId));
				rtbo.setTaskStatus("2");
				rtbo.setValidImgs(validImgs);
				return taskServiceImpl.submitTask(rtbo);
			} else {
				bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
				bsm.setMsg("会话已过期，请重新登录！");
				logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + taskId));
				return bsm;
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取产品信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + taskId));
			return bsm;
		}
	}

	@RequestMapping(value = "/publishTask.json", method = RequestMethod.POST)
	@ResponseBody
	public BasicServiceModel<String> publishTask(HttpServletRequest req, String sign, String tokenKey, String taskName,
			String taskDesc, int taskNum, MultipartFile taskImg1, MultipartFile taskImg2, MultipartFile taskImg3,
			String contactPhone, String contackEmail, int taskReward, String taskUrl, String rewardType) {
		String cmd = "TaskTradeController:submitTask";
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();

		try {
			TokenVo token = cacheUtil.getToken(tokenKey);
			if (null != token) {
				UserInfoBo bo = token.getUser();
				String userName = bo.getMobile();

				TaskInfoBo rtbo = new TaskInfoBo();
				Date curTime = new Date(System.currentTimeMillis());
				rtbo.setCreateTime(curTime);
				rtbo.setPublishEmail(contackEmail);
				rtbo.setPublishPhone(contactPhone);
				rtbo.setPublishTime(curTime);
				rtbo.setPublishUser(userName);
				rtbo.setRemark("");
				rtbo.setRewardType(RewardType.REWARD_FLOW);
				rtbo.setTaskDesc(taskDesc);
				rtbo.setTaskImgs(taskImgs);
				rtbo.setTaskNum(taskNum);
				rtbo.setTaskReward(taskReward);
				rtbo.setTaskStatus("0");
				rtbo.setTaskTitle(taskName);
				rtbo.setTaskUrl(taskUrl);
				rtbo.setUpdateTime(curTime);
				return taskServiceImpl.publishTask(rtbo);
			} else {
				bsm.setCode(ReqStatusConst.SESSION_EXPIRED);
				bsm.setMsg("会话已过期，请重新登录！");
				logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + taskName));
				return bsm;
			}
		} catch (Exception e) {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("获取产品信息失败！" + e.getMessage());
			logger.error(LogTemplate.genCommonSysLogStr(cmd, bsm.getCode(), bsm.getMsg() + ",data=" + taskName));
			return bsm;
		}
	}
}
