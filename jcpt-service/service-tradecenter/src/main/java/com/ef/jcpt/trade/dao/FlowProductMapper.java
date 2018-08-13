package com.ef.jcpt.trade.dao;

import com.ef.jcpt.trade.dao.model.FlowProduct;

public interface FlowProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowProduct record);

    int insertSelective(FlowProduct record);

    FlowProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowProduct record);

    int updateByPrimaryKeyWithBLOBs(FlowProduct record);

    int updateByPrimaryKey(FlowProduct record);
}