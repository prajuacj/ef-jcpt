package com.ef.jcpt.user.dao;

import org.springframework.stereotype.Repository;

import com.ef.jcpt.user.dao.model.UserMifiRelation;

@Repository
public interface UserMifiRelationMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(UserMifiRelation record);

	int insertSelective(UserMifiRelation record);

	UserMifiRelation selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserMifiRelation record);

	int updateByPrimaryKey(UserMifiRelation record);
}