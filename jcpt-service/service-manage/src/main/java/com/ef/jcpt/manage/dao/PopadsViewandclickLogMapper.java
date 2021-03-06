package com.ef.jcpt.manage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ef.jcpt.manage.dao.model.PopadsViewandclickLog;

@Repository
public interface PopadsViewandclickLogMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(PopadsViewandclickLog record);

	int insertSelective(PopadsViewandclickLog record);

	PopadsViewandclickLog selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(PopadsViewandclickLog record);

	int updateByPrimaryKey(PopadsViewandclickLog record);

	List<Map<String, Object>> countViewAndClick(int parseInt);
}