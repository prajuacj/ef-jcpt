package com.ef.jcpt.manage.service;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.manage.service.bo.AdspopPublishBo;

public interface IAdspopService {

	BasicServiceModel<String> getModeIdNamePair();

	BasicServiceModel<String> publishAdspop(AdspopPublishBo bo);

	BasicServiceModel<String> audit(int popadsId, String auditStatus, String auditUser, String auditAdvise);

	BasicServiceModel<String> realse(int[] popadsIds);

	int countPopadsByPage(String taskName, String taskStatus, int modelId);

	BasicServiceModel<String> listPopadsByPage(String taskName, String taskStatus, int modelId, int pageIndex,
			int pageSize);

	BasicServiceModel<String> updatePopads(AdspopPublishBo bo);

	BasicServiceModel<String> getPopadsById(String popadsId);
}
