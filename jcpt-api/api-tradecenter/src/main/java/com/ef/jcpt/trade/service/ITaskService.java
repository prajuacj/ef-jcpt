package com.ef.jcpt.trade.service;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.trade.service.bo.ReceivedTaskInfoBo;
import com.ef.jcpt.trade.service.bo.TaskInfoBo;

public interface ITaskService {

	BasicServiceModel<String> getTask(String userName, int next);

	BasicServiceModel<String> submitTask(ReceivedTaskInfoBo rtbo);

	BasicServiceModel<String> publishTask(TaskInfoBo rtbo);
}
