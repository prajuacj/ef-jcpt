package com.ef.jcpt.trade.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ef.jcpt.trade.dao.model.TaskInfo;

@Repository
public interface TaskInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(TaskInfo record);

	int insertSelective(TaskInfo record);

	TaskInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TaskInfo record);

	int updateByPrimaryKey(TaskInfo record);

	TaskInfo obtainTask(@Param("userName") String userName, @Param("next") int next);
}