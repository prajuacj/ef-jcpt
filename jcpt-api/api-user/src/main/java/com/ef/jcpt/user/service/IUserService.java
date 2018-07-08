package com.ef.jcpt.user.service;

import com.ef.jcpt.user.service.bo.UserInfoBo;

public interface IUserService {

	public boolean regist(UserInfoBo bo);

	public Integer findMemberExistCount(String userName);

	public boolean login(String username, String password);

	public UserInfoBo findMemberExist(String username);

	public void updatePwd(String username, String newPasswd);
}
