
package com.ef.jcpt.common.base;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.Set;

public class BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;
	

	public String validateFiled() {
		return validateFiled(null);
	}

	public String validateFiled(Class<?> groupClass) {
		String errorMsg = null;
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		Validator validator = vf.getValidator();
		Set<ConstraintViolation<BaseVo>> set = null;
		if (groupClass != null) {
			set = validator.validate(this, groupClass);
		} else {
			set = validator.validate(this);
		}
		for (ConstraintViolation<BaseVo> constraintViolation : set) {
			errorMsg = constraintViolation.getMessage();
			break;
		}
		return errorMsg;
	}

}
