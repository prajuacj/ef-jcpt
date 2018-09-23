package com.ef.jcpt.manage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ef.jcpt.manage.dao.model.PhoneSupportStandard;

@Repository
public interface PhoneSupportStandardMapper {
	int deleteByPrimaryKey(String phoneModel);

	int insert(PhoneSupportStandard record);

	int insertSelective(PhoneSupportStandard record);

	PhoneSupportStandard selectByPrimaryKey(String phoneModel);

	int updateByPrimaryKeySelective(PhoneSupportStandard record);

	int updateByPrimaryKey(PhoneSupportStandard record);

	List<Map<String, String>> selectPhoneSupportOperator(@Param("phoneModel") String phoneModel,
			@Param("standard") String standard);

	List<PhoneSupportStandard> listPhoneModelSupportStandardByPage(@Param("start") int start,
			@Param("pageSize") int pageSize);

	int countPhoneModelSupportStandardByPage();

	int selectPhoneSupportStandard(@Param("phoneModel") String phoneModel);
}