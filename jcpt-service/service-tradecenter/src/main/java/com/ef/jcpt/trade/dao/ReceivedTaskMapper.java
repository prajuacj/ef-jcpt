package com.ef.jcpt.trade.dao;

import com.ef.jcpt.trade.dao.model.ReceivedTask;

public interface ReceivedTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReceivedTask record);

    int insertSelective(ReceivedTask record);

    ReceivedTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReceivedTask record);

    int updateByPrimaryKey(ReceivedTask record);
}