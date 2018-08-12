package com.ef.jcpt.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.jcpt.core.digest.Digests;
import com.ef.jcpt.user.component.IUserInfoComponent;
import com.ef.jcpt.user.component.IUserMifiRelationComponent;
import com.ef.jcpt.user.dao.model.UserInfo;
import com.ef.jcpt.user.dao.model.UserMifiRelation;
import com.ef.jcpt.user.service.IUserService;
import com.ef.jcpt.user.service.bo.UserInfoBo;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserInfoComponent userInfoComponentImpl;

	@Autowired
	IUserMifiRelationComponent userMifiRelationComponent;

	@Override
	public boolean regist(UserInfoBo bo) {
		// TODO Auto-generated method stub
		Date curDate = new Date(System.currentTimeMillis());
		UserInfo info = new UserInfo();
		BeanUtils.copyProperties(bo, info);
		info.setUserName(bo.getMobile());
		info.setLoginPassword(Digests.getSHA1(bo.getLoginPassword()));
		info.setCreateTime(curDate);
		info.setUpdateTime(curDate);
		int ret = userInfoComponentImpl.saveUserInfo(info);
		if (ret == 1) {
			UserMifiRelation relationInfo = new UserMifiRelation();
			relationInfo.setCreateTime(curDate);
			relationInfo.setMifiSerial(bo.getMifiSerial());
			relationInfo.setUpdateTime(curDate);
			relationInfo.setUserName(bo.getMobile());
			relationInfo.setUseStatus("00");
			int miRet = userMifiRelationComponent.saveUserInfo(relationInfo);
			if (miRet == 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Integer findMemberExistCount(String userName) {
		// TODO Auto-generated method stub
		return userInfoComponentImpl.findMemberExistCount(userName);
	}

	@Override
	public boolean login(String username, String password, String loginType) {
		// TODO Auto-generated method stub
		if ("1".equals(loginType)) {
			try {
				List<UserInfo> list = userInfoComponentImpl.findUserByWechatId(username);
				if ((null != list) && (list.size() == 1)) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		} else {
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
