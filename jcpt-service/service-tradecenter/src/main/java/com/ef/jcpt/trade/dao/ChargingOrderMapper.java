package com.ef.jcpt.trade.dao;

import org.springframework.stereotype.Repository;

import com.ef.jcpt.trade.dao.model.ChargingOrder;

@Repository
public interface ChargingOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargingOrder record);

    int insertSelective(ChargingOrder record);

    ChargingOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargingOrder record);

    int updateByPrimaryKey(ChargingOrder record);
}