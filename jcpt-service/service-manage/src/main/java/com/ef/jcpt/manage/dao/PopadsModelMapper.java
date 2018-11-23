package com.ef.jcpt.manage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ef.jcpt.manage.dao.model.PopadsModel;

@Repository
public interface PopadsModelMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(PopadsModel record);

	int insertSelective(PopadsModel record);

	PopadsModel selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(PopadsModel record);

	int updateByPrimaryKey(PopadsModel record);

	List<Map<Integer, String>> getModelIdNamePair();
}