package com.ef.jcpt.trade.dao;

import java.util.List;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.trade.dao.model.PhoneSupportOperator;

public interface PhoneSupportOperatorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PhoneSupportOperator record);

    int insertSelective(PhoneSupportOperator record);

    PhoneSupportOperator selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PhoneSupportOperator record);

    int updateByPrimaryKey(PhoneSupportOperator record);

	BasicServiceModel<List<PhoneSupportOperator>> seletOperatorByPhoneModelAndNationCode(String phoneModel,
			String nationCode);
}