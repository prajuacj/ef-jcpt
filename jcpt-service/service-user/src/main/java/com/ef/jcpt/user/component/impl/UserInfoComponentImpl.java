package com.ef.jcpt.user.component.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ef.jcpt.user.component.IUserInfoComponent;
import com.ef.jcpt.user.dao.UserInfoMapper;
import com.ef.jcpt.user.dao.model.UserInfo;

@Component("userInfoComponentImpl")
public class UserInfoComponentImpl implements IUserInfoComponent {

	@Autowired
	UserInfoMapper userInfoMapper;

	@Override
	public int saveUserInfo(UserInfo info) {
		// TODO Auto-generated method stub
		return userInfoMapper.insertSelective(info);
	}

	@Override
	public Integer findMemberExistCount(String userName) {
		// TODO Auto-generated method stub
		return userInfoMapper.selectCountByUserName(userName);
	}

	@Override
	public UserInfo findUserByUserName(String username) {
		// TODO Auto-generated method stub
		return userInfoMapper.selectUserByUserName(username);
	}

	@Override
	public void updatePwd(String username, String orgPwd) {
		// TODO Auto-generated method stub
		userInfoMapper.updateUserPwd(username, orgPwd);
	}

	@Override
	public List<UserInfo> findUserByWechatId(String username) {
		// TODO Auto-generated method stub
		return userInfoMapper.selectUserByWechatId(username);
	}

}
