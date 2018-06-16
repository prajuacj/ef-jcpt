/**
 * Project Name:caifubao-common <br>
 * File Name:BasicServiceModel.java <br>
 * Package Name:com.caifubao.jcpt.common.entity <br>
 * @author xiezbmf
 * Date:2017年10月17日下午2:30:53 <br>
 * Copyright (c) 2017, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.entity;

import java.io.Serializable;

import com.ef.jcpt.common.constant.ReqStatusConst;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ClassName: BasicServiceModel <br>
 * Description: service层返回实体类
 * 
 * @author xiezbmf
 * @Date 2017年10月17日下午2:30:53 <br>
 * @version
 * @since JDK 1.6
 */
public class BasicServiceModel<T> implements Serializable {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -6368480548309645176L;
	/**
	 * code:请求响应结果.
	 */
	private String code;
	/**
	 * errorCode:请求出错时，详细错误代码.
	 */
	@JsonInclude(Include.NON_NULL)
	private String errorCode;
	/**
	 * msg:请求返回消息摘要.
	 */
	@JsonInclude(Include.NON_NULL)
	private String msg;
	/**
	 * data:请求返回消息内容.
	 */
	@JsonInclude(Include.NON_NULL)
	private T data;

	/**
	 *
	 * Creates a new instance of BasicServiceModel. Description:成功实例
	 * 
	 * @author anxymf Date:2017年11月24日下午4:09:41 <br>
	 * @param errMsg
	 */
	public BasicServiceModel(String msg) {
		this.code = ReqStatusConst.OK;
		this.msg = msg;
	}

	/**
	 *
	 * Creates a new instance of BasicServiceModel. Description:默认成功实例
	 *
	 * @author xiezbmf
	 * @Date 2017年6月26日下午4:44:08 <br>
	 */
	public BasicServiceModel() {
		this.code = ReqStatusConst.OK;
	}

	/**
	 * 
	 * Creates a new instance of BasicServiceModel. Description:失败实例
	 * 
	 * @author xiezbmf
	 * @Date 2017年6月26日下午4:43:23 <br>
	 * @param errCode
	 *            失败具体异常码
	 * @param errMsg
	 *            失败描述
	 */
	public BasicServiceModel(String errCode, String errMsg) {
		this.code = ReqStatusConst.FAIL;
		this.errorCode = errCode;
		this.msg = errMsg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BasicServiceModel [code=" + code + ", errorCode=" + errorCode + ", msg=" + msg + ", data=" + data + "]";
	}

}
