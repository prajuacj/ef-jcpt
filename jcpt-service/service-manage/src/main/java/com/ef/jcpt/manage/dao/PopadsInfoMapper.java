package com.ef.jcpt.manage.dao;

import java.util.List;

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

	int audit(@Param("popadsId") int popadsId, @Param("auditStatus") String auditStatus,
			@Param("auditUser") String auditUser, @Param("auditAdvise") String auditAdvise);

	void updateRealseStatusByBatch(List<Integer> list);

	int countPopadsByPage(@Param("taskName") String taskName, @Param("taskStatus") String taskStatus,
			@Param("modelId") int modelId);

	List<PopadsInfo> listPopadsByPage(@Param("taskName") String taskName, @Param("taskStatus") String taskStatus,
			@Param("modelId") int modelId, @Param("start") int start, @Param("pageSize") int pageSize);
}