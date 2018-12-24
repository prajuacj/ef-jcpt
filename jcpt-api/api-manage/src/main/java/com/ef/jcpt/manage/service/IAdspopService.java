package com.ef.jcpt.manage.service;

import java.util.List;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.manage.service.bo.AdspopPublishBo;
import com.ef.jcpt.manage.service.bo.MixJSBo;

public interface IAdspopService {

	BasicServiceModel<String> getModeIdNamePair();

	BasicServiceModel<String> publishAdspop(AdspopPublishBo bo);

	BasicServiceModel<String> audit(int popadsId, String auditStatus, String auditUser, String auditAdvise);

	BasicServiceModel<String> realse(int[] popadsIds, List<MixJSBo> list);

	int countPopadsByPage(String taskName, String[] taskStatus, int modelId, String publishUser);

	BasicServiceModel<String> listPopadsByPage(String taskName, String[] taskStatus, int modelId, String publishUser,
			int pageIndex, int pageSize);

	BasicServiceModel<String> updatePopads(AdspopPublishBo bo);

	BasicServiceModel<String> getPopadsById(String popadsId);

	BasicServiceModel<String> addViewAndClickCountLog(String taskId, String mac, int countType);

	BasicServiceModel<String> updateOnAndDownLine(int[] ids, String finish);

	BasicServiceModel<String> autoReleaseTask();
}
