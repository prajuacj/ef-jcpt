package com.ef.jcpt.trade.dao;

import com.ef.jcpt.trade.dao.model.PayInfo;

public interface PayInfoMapper {
    int deleteByPrimaryKey(String flowId);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(String flowId);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

	PayInfo selectByOrderId(String orderId);
}