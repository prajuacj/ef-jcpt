package com.ef.jcpt.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ef.jcpt.trade.dao.model.PhoneSupportOperator;

@Repository
public interface PhoneSupportOperatorMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(PhoneSupportOperator record);

	int insertSelective(PhoneSupportOperator record);

	PhoneSupportOperator selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(PhoneSupportOperator record);

	int updateByPrimaryKey(PhoneSupportOperator record);

	List<PhoneSupportOperator> seletOperatorByPhoneModelAndNationCode(@Param("phoneModel") String phoneModel,
			@Param("nationCode") String nationCode);
}