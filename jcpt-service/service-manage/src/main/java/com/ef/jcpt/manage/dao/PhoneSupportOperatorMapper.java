package com.ef.jcpt.manage.dao;

import org.springframework.stereotype.Repository;

import com.ef.jcpt.manage.dao.model.PhoneSupportOperator;

@Repository
public interface PhoneSupportOperatorMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(PhoneSupportOperator record);

	int insertSelective(PhoneSupportOperator record);

	PhoneSupportOperator selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(PhoneSupportOperator record);

	int updateByPrimaryKey(PhoneSupportOperator record);
}