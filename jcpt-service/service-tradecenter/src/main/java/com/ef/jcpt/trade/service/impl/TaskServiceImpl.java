package com.ef.jcpt.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ef.jcpt.common.constant.ReqStatusConst;
import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.common.util.BeanUtil;
import com.ef.jcpt.trade.dao.ReceivedTaskMapper;
import com.ef.jcpt.trade.dao.TaskInfoMapper;
import com.ef.jcpt.trade.dao.model.ReceivedTask;
import com.ef.jcpt.trade.dao.model.TaskInfo;
import com.ef.jcpt.trade.service.ITaskService;
import com.ef.jcpt.trade.service.bo.ReceivedTaskInfoBo;
import com.ef.jcpt.trade.service.bo.TaskInfoBo;

@Service
public class TaskServiceImpl implements ITaskService {

	@Autowired
	private TaskInfoMapper taskInfoMapper;

	@Autowired
	private ReceivedTaskMapper receivedTaskMapper;

	@Override
	public BasicServiceModel<String> getTask(String userName, int next) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		// TODO Auto-generated method stub

		TaskInfo taskInfo = taskInfoMapper.obtainTask(userName, next);
		if (null != taskInfo) {
			bsm.setData(JSONObject.toJSONString(taskInfo));
		}
		bsm.setCode(ReqStatusConst.OK);

		return bsm;
	}

	@Override
	public BasicServiceModel<String> submitTask(ReceivedTaskInfoBo rtbo) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		// TODO Auto-generated method stub
		ReceivedTask record = new ReceivedTask();
		BeanUtil.copyProperties(rtbo, record);
		int ret = receivedTaskMapper.updateByPrimaryKeySelective(record);
		if (ret == 1) {
			bsm.setCode(ReqStatusConst.OK);
		} else {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("提交失败！");
		}

		return bsm;
	}

	@Override
	public BasicServiceModel<String> publishTask(TaskInfoBo rtbo) {
		// TODO Auto-generated method stub
		BasicServiceModel<String> bsm = new BasicServiceModel<String>();
		// TODO Auto-generated method stub
		TaskInfo record = new TaskInfo();
		BeanUtil.copyProperties(rtbo, record);
		int ret = taskInfoMapper.insertSelective(record);
		if (ret == 1) {
			bsm.setCode(ReqStatusConst.OK);
		} else {
			bsm.setCode(ReqStatusConst.FAIL);
			bsm.setMsg("提交失败！");
		}

		return bsm;
	}

}
