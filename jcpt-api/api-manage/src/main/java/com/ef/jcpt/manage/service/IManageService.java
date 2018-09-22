package com.ef.jcpt.manage.service;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.manage.service.bo.OperatorInfoBo;
import com.ef.jcpt.manage.service.bo.PhoneSupportStandardBo;

public interface IManageService {

	BasicServiceModel<String> savePhoneSupportStandard(PhoneSupportStandardBo bo);

	BasicServiceModel<String> configPhoneSupportStandard(PhoneSupportStandardBo bo);

	int countPhoneModelSupportStandardByPage();

	BasicServiceModel<String> listPhoneModelSupportStandardByPage(int pageIndex, int pageSize);

	BasicServiceModel<String> configOperatorSupportStandard(OperatorInfoBo bo);
}
