package com.ef.jcpt.user.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.jcpt.core.digest.Digests;
import com.ef.jcpt.user.component.IUserInfoComponent;
import com.ef.jcpt.user.dao.model.UserInfo;
import com.ef.jcpt.user.service.IUserService;
import com.ef.jcpt.user.service.bo.UserInfoBo;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserInfoComponent userInfoComponentImpl;

	@Override
	public boolean regist(UserInfoBo bo) {
		// TODO Auto-generated method stub
		UserInfo info = new UserInfo();
		BeanUtils.copyProperties(bo, info);
		int ret = userInfoComponentImpl.saveUserInfo(info);
		if (ret == 1) {
			return true;
		}
		return false;
	}

	@Override
	public Integer findMemberExistCount(String userName) {
		// TODO Auto-generated method stub
		return userInfoComponentImpl.findMemberExistCount(userName);
	}

	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		String orgPwd = Digests.getSHA1(password);
		UserInfo ui = userInfoComponentImpl.findUserByUserName(username);
		if (null != ui) {
			String dbPwd = ui.getLoginPassword();
			if (orgPwd.equalsIgnoreCase(dbPwd)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public UserInfoBo findMemberExist(String username) {
		// TODO Auto-generated method stub
		UserInfoBo bo = new UserInfoBo();
		UserInfo ui = userInfoComponentImpl.findUserByUserName(username);
		BeanUtils.copyProperties(ui, bo);
		return bo;
	}

	@Override
	public void updatePwd(String username, String newPasswd) {
		// TODO Auto-generated method stub
		String orgPwd = Digests.getSHA1(newPasswd);
		userInfoComponentImpl.updatePwd(username, orgPwd);
	}

}
