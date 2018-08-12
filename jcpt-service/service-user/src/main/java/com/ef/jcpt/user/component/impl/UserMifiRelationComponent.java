package com.ef.jcpt.user.component.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.jcpt.user.component.IUserMifiRelationComponent;
import com.ef.jcpt.user.dao.UserMifiRelationMapper;
import com.ef.jcpt.user.dao.model.UserMifiRelation;

@Component("userMifiRelationComponent")
public class UserMifiRelationComponent implements IUserMifiRelationComponent {

	@Autowired
	UserMifiRelationMapper userMifiRelationMapper;

	@Override
	public int saveUserInfo(UserMifiRelation info) {
		// TODO Auto-generated method stub
		return userMifiRelationMapper.insertSelective(info);
	}

}
