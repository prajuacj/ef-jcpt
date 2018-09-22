package com.ef.jcpt.manage.dao;

import org.springframework.stereotype.Repository;

import com.ef.jcpt.manage.dao.model.OperatorInfo;

@Repository
public interface OperatorInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(OperatorInfo record);

	int insertSelective(OperatorInfo record);

	OperatorInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(OperatorInfo record);

	int updateByPrimaryKey(OperatorInfo record);
}