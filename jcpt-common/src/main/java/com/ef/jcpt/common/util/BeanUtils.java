package com.ef.jcpt.common.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ef.jcpt.common.entity.BasicServiceModel;

/**
 * Created by wuzhao on 2017/4/10 0010.
 */
public class BeanUtils {
	public static <T, O> T origToTarget(O orig, Class<T> targetClass) {
		T vo = null;
		try {
			String jsonString = JSONObject.toJSONString(orig);
			vo = JSONObject.parseObject(jsonString, targetClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	public static <O, T> List<T> origListToTargets(List<O> origList, Class<T> tClazz) {
		if (CollectionUtils.isEmpty(origList) || tClazz == null) {
			return null;
		}
		List<T> tList = new ArrayList<>();
		for (O orig : origList) {
			T t = origToTarget(orig, tClazz);
			tList.add(t);
		}
		return tList;
	}

	public static <O, T> BasicServiceModel<T> modelCopy(O orig) {
		String jsonString = JSONObject.toJSONString(orig);
		BasicServiceModel<T> reData = JSON.parseObject(jsonString, new TypeReference<BasicServiceModel<T>>() {
		});
		return reData;
	}
}
