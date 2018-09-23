package com.ef.jcpt.trade.dao;

import org.springframework.stereotype.Repository;

import com.ef.jcpt.trade.dao.model.ProductPrice;

@Repository
public interface ProductPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductPrice record);

    int insertSelective(ProductPrice record);

    ProductPrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductPrice record);

    int updateByPrimaryKey(ProductPrice record);
}