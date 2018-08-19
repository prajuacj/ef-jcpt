package com.ef.jcpt.trade.component;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.jcpt.trade.dao.FlowProductMapper;
import com.ef.jcpt.trade.dao.model.FlowProduct;

@Component
public class FlowProductComponent {

	@Autowired
	private FlowProductMapper flowProductMapper;

	public int countOrderByPage(String userNationCode, String buyNationCode, String productType) {
		// TODO Auto-generated method stub
		return flowProductMapper.countProductByPage(userNationCode, buyNationCode, productType);
	}

	public List<Map> listProductByPage(String userNationCode, String buyNationCode, String productType, int start,
			int pageSize) {
		// TODO Auto-generated method stub
		return flowProductMapper.listProductByPage(userNationCode, buyNationCode, productType, start, pageSize);
	}

	public List<Map> selectProductAndPrice(String userNationCode, String buyNationCode, int productId) {
		// TODO Auto-generated method stub
		return flowProductMapper.selectProductAndPrice(userNationCode, buyNationCode, productId);
	}

}
