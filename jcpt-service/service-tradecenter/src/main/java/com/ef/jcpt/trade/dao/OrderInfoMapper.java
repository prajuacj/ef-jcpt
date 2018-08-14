package com.ef.jcpt.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ef.jcpt.trade.dao.model.OrderInfo;

@Repository
public interface OrderInfoMapper {

	int deleteByPrimaryKey(String orderId);

	int insert(OrderInfo record);

	int insertSelective(OrderInfo record);

	OrderInfo selectByPrimaryKey(String orderId);

	int updateByPrimaryKeySelective(OrderInfo record);

	int updateByPrimaryKey(OrderInfo record);

	List<OrderInfo> listOrderByPage(@Param("userId") String userId, @Param("nationCode") String nationCode,
			@Param("orderStatus") String orderStatus, @Param("start") int start, @Param("pageSize") int pageSize);

	int updateOrderOperatorByOrderId(@Param("orderId") String orderId, @Param("operatorId") String operatorId,
			@Param("operatorName") String operatorName);

	int countOrderByPage(@Param("userId") String userId, @Param("nationCode") String nationCode,
			@Param("orderStatus") String orderStatus);
}