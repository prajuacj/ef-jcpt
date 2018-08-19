package com.ef.jcpt.user.service;

import com.ef.jcpt.common.entity.BasicServiceModel;
import com.ef.jcpt.user.service.bo.UserInfoBo;

public interface IUserService {

	public boolean regist(UserInfoBo bo);

	public Integer findMemberExistCount(String userName);

	public BasicServiceModel<UserInfoBo> login(String username, String password, String loginType);

	public UserInfoBo findMemberExist(String username);

	public void updatePwd(String username, String newPasswd);
}
