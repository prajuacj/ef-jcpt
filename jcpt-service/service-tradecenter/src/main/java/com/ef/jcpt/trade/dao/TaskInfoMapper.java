package com.ef.jcpt.trade.dao;

import com.ef.jcpt.trade.dao.model.TaskInfo;

public interface TaskInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskInfo record);

    int insertSelective(TaskInfo record);

    TaskInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskInfo record);

    int updateByPrimaryKeyWithBLOBs(TaskInfo record);

    int updateByPrimaryKey(TaskInfo record);
}