package com.ef.jcpt.trade.dao;

import org.springframework.stereotype.Repository;

import com.ef.jcpt.trade.dao.model.ChargingItem;

@Repository
public interface ChargingItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargingItem record);

    int insertSelective(ChargingItem record);

    ChargingItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargingItem record);

    int updateByPrimaryKey(ChargingItem record);
}