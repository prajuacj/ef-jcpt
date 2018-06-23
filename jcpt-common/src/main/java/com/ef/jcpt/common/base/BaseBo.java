
package com.ef.jcpt.common.base;

import java.io.Serializable;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class BaseBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6005831061313006078L;

	/**
	 * wuzhao
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue,
				SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 
	 * validateFiled:验证属性. <br>
	 *
	 * @author anxymf Date:2017年10月17日上午9:13:01 <br>
	 * @return
	 */
	public String validateFiled() {
		return validateFiled(null);
	}

	/**
	 * 
	 * validateFiled:根据验证组验证. <br>
	 *
	 * @author anxymf Date:2017年10月17日上午9:12:44 <br>
	 * @param groupClass
	 * @return
	 */
	public String validateFiled(Class<?> groupClass) {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();
		Set<ConstraintViolation<BaseBo>> set = null;
		if (groupClass == null) {
			set = validator.validate(this);
		} else {
			set = validator.validate(this, groupClass);
		}
		StringBuffer buffer = new StringBuffer();
		for (ConstraintViolation<BaseBo> constraintViolation : set) {
			buffer.append(constraintViolation.getMessage());
		}
		return buffer.toString();
	}

}
