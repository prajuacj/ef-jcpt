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
			@Param("modelId") int modelId, @Param("publishUser") String publishUser);

	List<PopadsInfo> listPopadsByPage(@Param("taskName") String taskName, @Param("taskStatus") String taskStatus,
			@Param("modelId") int modelId, @Param("publishUser") String publishUser, @Param("start") int start,
			@Param("pageSize") int pageSize);

	int getExeTypeById(@Param("popadsId") int popadsId);

	void updateDownStatus();

	void updateOnStatusByBatch(@Param("list") List<Integer> list);

	void updateDownStatusByBatch(@Param("list") List<Integer> list);

	List<PopadsInfo> selectOnlineReleaseTask();
}