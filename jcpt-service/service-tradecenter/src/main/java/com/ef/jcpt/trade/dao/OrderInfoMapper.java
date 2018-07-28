package com.ef.jcpt.trade.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.trade.dao.model.OrderInfo;
import com.ef.jcpt.trade.service.bo.OrderInfoBo;

@Repository
public interface OrderInfoMapper {

	int deleteByPrimaryKey(String orderId);

	int insert(OrderInfo record);

	int insertSelective(OrderInfo record);

	OrderInfo selectByPrimaryKey(String orderId);

	int updateByPrimaryKeySelective(OrderInfo record);

	int updateByPrimaryKey(OrderInfo record);

	BasicServiceModel<List<OrderInfoBo>> listOrderByUserId(String userId);
}