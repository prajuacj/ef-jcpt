package com.ef.jcpt.trade.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ef.jcpt.trade.dao.model.FlowProduct;

public interface FlowProductMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(FlowProduct record);

	int insertSelective(FlowProduct record);

	FlowProduct selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(FlowProduct record);

	int updateByPrimaryKey(FlowProduct record);

	int countProductByPage(@Param("userNationCode") String userNationCode, @Param("buyNationCode") String buyNationCode,
			@Param("productType") String productType);

	List<Map> listProductByPage(@Param("userNationCode") String userNationCode,
			@Param("buyNationCode") String buyNationCode, @Param("productType") String productType,
			@Param("start") int start, @Param("pageSize") int pageSize);
}