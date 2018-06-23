package com.ef.jcpt.user.component;

import com.ef.jcpt.user.dao.model.UserInfo;

public interface IUserInfoComponent {

	public int saveUserInfo(UserInfo info);

	public Integer findMemberExistCount(String userName);

	public UserInfo findUserByUserName(String username);

	public void updatePwd(String username, String orgPwd);
}
