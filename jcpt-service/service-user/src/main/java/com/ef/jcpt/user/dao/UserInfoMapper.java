package com.ef.jcpt.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ef.jcpt.user.dao.model.UserInfo;

@Repository
public interface UserInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserInfo record);

	int insertSelective(UserInfo record);

	UserInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserInfo record);

	int updateByPrimaryKey(UserInfo record);

	Integer selectCountByUserName(@Param("userName") String userName);

	UserInfo selectUserByUserName(@Param("userName") String userName);

	void updateUserPwd(@Param("username") String username, @Param("loginPassword") String orgPwd);
}