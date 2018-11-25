package com.ef.jcpt.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ef.jcpt.manage.dao.model.PopadsInfo;

@Repository
public interface PopadsInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(PopadsInfo record);

	int insertSelective(PopadsInfo record);

	PopadsInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(PopadsInfo record);

	int updateByPrimaryKey(PopadsInfo record);
	
	int audit(@Param("popadsId") int popadsId, @Param("auditUser") String auditUser,
			@Param("auditAdvise") String auditAdvise);
}