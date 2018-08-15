package com.ef.jcpt.trade.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.jcpt.trade.dao.PhoneSupportOperatorMapper;
import com.ef.jcpt.trade.dao.model.PhoneSupportOperator;

@Component
public class PhoneSupportOperatorComponent {

	@Autowired
	private PhoneSupportOperatorMapper phoneSupportOperatorMapper;

	public List<PhoneSupportOperator> listOperator(String phoneModel, String nationCode) {
		// TODO Auto-generated method stub
		return phoneSupportOperatorMapper.seletOperatorByPhoneModelAndNationCode(phoneModel, nationCode);
	}

}
