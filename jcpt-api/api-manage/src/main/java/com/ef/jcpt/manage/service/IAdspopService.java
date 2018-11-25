package com.ef.jcpt.manage.service;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.manage.service.bo.AdspopPublishBo;

public interface IAdspopService {

	BasicServiceModel<String> getModeIdNamePair();

	BasicServiceModel<String> publishAdspop(AdspopPublishBo bo);

	BasicServiceModel<String> audit(int popadsId, String auditUser, String auditAdvise);

	BasicServiceModel<String> realse(int[] popadsIds);
}
