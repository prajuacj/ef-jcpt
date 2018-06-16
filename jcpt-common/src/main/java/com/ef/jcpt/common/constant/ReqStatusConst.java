/**
 * Project Name:caifubao-common <br>
 * File Name:ReqStatusConst.java <br>
 * Package Name:com.caifubao.jcpt.common.constant <br>
 *
 * @author xiezbmf
 * Date:2017年10月17日下午2:29:15 <br>
 * Copyright (c) 2017, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.constant;

/**
 * ClassName: ReqStatusConst <br>
 * Description: service请求返回常量类
 *
 * @author xiezbmf
 * @Date 2017年10月17日下午2:29:15 <br>
 * @since JDK 1.6
 */
public class ReqStatusConst {
	// 全局

	/**
	 * 超时
	 */
	public static final String TIME_OUT = "TIME_OUT";

	/**
	 * 操作成功，符合预期。
	 */
	public static final String OK = "OK";

	/**
	 * 成功
	 */
	public static final String SUCCESS = "SUCCESS";

	/**
	 * 操作失败
	 */
	public static final String FAIL = "FAIL";

	/**
	 * 不确定
	 */
	public static final String UNCERTAIN = "UNCERTAIN";

	/***
	 * 转发
	 */
	public static final String FORWARD = "FORWARD";

	/**
	 * 初始化状态
	 */
	public static final String INIT = "INIT";

	/**
	 * 处理中
	 */
	public static final String PROCESSING = "PROCESSING";

	/**
	 * 未知异常
	 */
	public static final String UNKNOW_EXCEPTION = "UNKNOW_EXCEPTION";

	/**
	 * 没有登录
	 */
	public static final String FA_INVALID_SESSION = "FA_INVALID_SESSION";

	/**
	 * 错误信息的key前缀
	 */
	public static final String ERROR_KEY_PREFIX = "errors.";

	/**
	 * 填写的长度字段值或字段名称太长，超出长度范围
	 */
	public static final String TOO_LONG = "TOO_LONG";

	/**
	 * 无效的命令
	 */
	public static final String INVALID_COMMAND = "INVALID_COMMAND";

	/**
	 * 错误的字段名
	 */
	public static final String INVALID_FILED = "INVALID_FILED";

	/**
	 * 错误的字段值
	 */
	public static final String INVALID_VALUE = "INVALID_VALUE";

	/**
	 * 字段值为空
	 */
	public static final String VALUE_NOT_NULL = "VALUE_NOT_NULL";

	/**
	 * 记录没找到
	 */
	public static final String RECORD_NOTFOUND = "RECORD_NOTFOUND";

	/**
	 * 数据异常
	 */
	public static final String DATA_EXCEPTION = "DATA_EXCEPTION";

	/**
	 * 系统繁忙
	 */
	public static final String SYSTEM_BUSY = "SYSTEM_BUSY";

	/**
	 * 必须图片验证
	 */
	public static final String IMAGE_VERIFICATION = "IMAGE_VERIFICATION";

	/**
	 * 必须图片验证
	 */
	public static final String IMAGE_VERIFICATION_ERROR = "IMAGE_VERIFICATION_ERROR";

	/**
	 * 需要校验码
	 */
	public static final String NEED_VERIFY_CODE = "NEED_VERIFY_CODE";

	/**
	 * 校验码不正确
	 */
	public static final String INVALID_VERIFY_CODE = "INVALID_VERIFY_CODE";

	/**
	 * 内存不足
	 */
	public static final String OUT_OF_MEMORY = "OUT_OF_MEMORY";

	/**
	 * 网络连接错误
	 */
	public static final String IO_EXCEPTION = "IO_EXCEPTION";

	/**
	 * 文件不存在
	 */
	public static final String FILE_NOT_EXIST = "FILE_NOT_EXIST";

	/**
	 * 网络写操作错误
	 */
	public static final String IO_WRITE_EXCEPTION = "IO_WRITE_EXCEPTION";

	/**
	 * 网络读操作错误
	 */
	public static final String IO_READ_EXCEPTION = "IO_READ_EXCEPTION";

	/**
	 * 系统内部错误
	 */
	public static final String SYS_INTERNAL_ERROR = "SYS_INTERNAL_ERROR";

	/**
	 * 数据库操作出错
	 */
	public static final String DB_EXCEPTION = "DB_EXCEPTION";

	/**
	 * 业务操作出错
	 */
	public static final String BIZ_EXCEPTION = "BIZ_EXCEPTION";

	/**
	 * 业务检查异常
	 */
	public static final String CHECK_EXCEPTION = "CHECK_EXCEPTION";

	/**
	 * 解释文件出错
	 */
	public static final String PARSE_FILE_ERROR = "PARSE_FILE_ERROR";

	/**
	 * 验证数据失败
	 */
	public static final String VALIDATE_ERROR = "VALIDATE_ERROR";

	/**
	 * 验证数据数量失败
	 */
	public static final String VALIDATE_MAXCOUNT_ERROR = "VALIDATE_MAXCOUNT_ERROR";

	/**
	 * 过期
	 */
	public static final String OUT_OF_DATE = "OUT_OF_DATE";

	/**
	 * 不支持此服务
	 */
	public static final String NOT_SUPPORT_SERVICE = "NOT_SUPPORT_SERVICE";

	/**
	 * 重复的服务等级
	 */
	public static final String SERVICE_REPEAT = "SERVICE_REPEAT";

	/**
	 * 非法请求
	 */
	public static final String INVALID_FUNC = "INVALID_REQUEST";

	/**
	 * 用户名不符合规范
	 */
	public static final String INVALID_USERID = "INVALID_USERID";

	/**
	 * 密码不符合规范
	 */
	public static final String INVALID_PWD = "INVALID_PWD";

	/**
	 * 新的密码不符合规范
	 */
	public static final String INVALID_PWD_NEW = "INVALID_PWD_NEW";

	/**
	 * 弱密码
	 */
	public static final String WEAK_PWD = "WEAK_PWD";

	/**
	 * 重复请求
	 */
	public static final String REPEAT_REQUEST = "REPEAT_REQUEST";

	/**
	 * 签名错误
	 */
	public static final String SIGN_CHECK_FAIL = "SIGN_CHECK_FAIL";

	/**
	 * 响应为空
	 */
	public static final String EMPTY_RESPONSE = "EMPTY_RESPONSE";

	// 用户登录

	/**
	 * 用户名或者密码不匹配，或者用户不存在
	 */
	public static final String UNAUTHORIZED = "UNAUTHORIZED";

	/**
	 * 无此用户，用户UIN错误。
	 */
	public static final String USER_NOT_FOUND = "USER_NOT_FOUND";

	/**
	 * Session过期或找不到
	 */
	public static final String SESSION_TIMEOUT = "SESSION_TIMEOUT";

	/**
	 * 用户帐号被禁止
	 */
	public static final String USERDISABLE = "USERDISABLE";

	/**
	 * 密码错误
	 */
	public static final String PASSWD_ERR = "PASSWD_ERR";

	/**
	 * 被限制的用户(或IP),进入黑名单
	 */
	public static final String LIMIT_USER = "LIMIT_USER";

	/**
	 * 密码被人多次尝试，被限制，并提醒用户修改密码
	 */
	public static final String LIMIT_PASSWD = "LIMIT_PASSWD";

	/**
	 * 用户处于锁定状态
	 */
	public static final String USER_LOCKED = "USER_LOCKED";

	/**
	 * 用户被禁用
	 */
	public static final String USER_SUSPENDED = "USER_SUSPENDED";

	/**
	 * 用户状态错误
	 */
	public static final String USER_WRONG_STATUS = "USER_WRONG_STATUS";

	/**
	 * 用户处于自锁定状态
	 */
	public static final String USER_SELF_LOCK = "USER_SELF_LOCK";

	/**
	 * 没有权限使用
	 */
	public static final String FORBIDDEN = "FORBIDDEN";

	/**
	 * json 格式错误
	 */
	public static final String JSON_PARSE_EXCEPTION = "JSON_PARSE_EXCEPTION";

	/**
	 * data 数据操作异常
	 */
	public static final String DO_DATA_EXCEPTION = "DO_DATA_EXCEPTION";

	/**
	 * 登录失败（其他原因导致异常）
	 */
	public static final String LOGIN_FAIL = "LOGIN_FAIL";

	/**
	 * NO_LOGIN:未登录.
	 */
	public static final String NO_LOGIN = "NO_LOGIN";

	// 用户注册

	/**
	 * 用户已存在
	 */
	public static final String USER_EXIST = "USER_EXIST";

	/**
	 * 添加用户成功，但添加别名时失败
	 */
	public static final String USER_ALIAS_OPERA_FAILED = "USER_ALIAS_OPERA_FAILED";

	/**
	 * 用户注销
	 */
	public static final String USER_OFF = "USER_OFF";

	/**
	 * 注册名为空
	 */
	public static final String REGISTERID_NULL = "REGISTERID_NULL";

	/**
	 * 邮箱名不合法，名字过长或不包括域名
	 */
	public static final String INVALID_EMAIL_NAME = "INVALID_EMAIL_NAME";

	/**
	 * 邮箱名重复
	 */
	public static final String EMAIL_NAME_REPEAT = "EMAIL_NAME_REPEAT";

	/**
	 * 需要域名
	 */
	public static final String NEED_DOMAIN = "NEED_DOMAIN";

	// 认证

	/**
	 * Session不合法
	 */
	public static final String INVALID_SESSION = "INVALID_SESSION";

	// SSO错误码
	/**
	 * 暂不支持的跳转类型
	 */
	public static final String SSO_LT_NOSUPPORT = "SSO_LT_NOSUPPORT";

	/**
	 * 非法请求参数
	 */
	public static final String SSO_LT_ILLEGAL = "SSO_LT_ILLEGAL";

	/**
	 * ip被限制
	 */
	public static final String IP_NOT_ALLOWED = "IP_NOT_ALLOWED";

	// 用户参数设置

	/**
	 * 电话号码不存在
	 */
	public static final String MOBILEPHONE_NOT_EXIST = "MOBILEPHONE_NOT_EXIST";

	/**
	 * 用户安全提示问题不存在
	 */
	public static final String QUESTION_NOT_EXIST = "QUESTION_NOT_EXIST";

	/**
	 * 用户安全提示问题不存在
	 */
	public static final String NO_LOCK_FOLDER = "NO_LOCK_FOLDER";

	/**
	 * //首次登陆需要修改密码
	 */
	public static final String MODIFY_PASS_FIRST_LOGIN = "MODIFY_PASS_FIRST_LOGIN";
	/**
	 * 密码强度不符合要求
	 */
	public static final String MODIFY_PASS_SEC_LEVEL = "MODIFY_PASS_SEC_LEVEL";
	/**
	 * 密码在弱密码库中
	 */
	public static final String MODIFY_PASS_IN_WEAKLIB = "MODIFY_PASS_IN_WEAKLIB";
	/**
	 * 密码为禁用密码
	 */
	public static final String MODIFY_PASS_FORBID = "MODIFY_PASS_FORBID";
	/**
	 * 密码过期
	 */
	public static final String MODIFY_PASS_EXPIRE = "MODIFY_PASS_EXPIRE";

	/**
	 * 余额不足
	 */
	public static final String LACK_OF_BALANCE = "LACK_OF_BALANCE";

	/**
	 * 查询记录过多
	 */
	public static final String TOO_MANY_RECORD = "TOO_MANY_RECORD";

	// 文件校验

	/**
	 * 不支持的文件类型
	 */
	public static final String NOT_SUPPORT_FILE_TYPE = "NOT_SUPPORT_FILE_TYPE";

	/**
	 * 开户子账户未授权
	 */
	public static final String ACCOUNT__NOT_AUTHORIZE = "ACCOUNT_NOT_AUTHORIZE";

	/**
	 * 空文件
	 */
	public static final String EMPTY_FILE = "EMPTY_FILE";

	/**
	 * 文件过大，超过最大设置
	 */
	public static final String EXCEED_MAX_SIZE = "EXCEED_MAX_SIZE";

	/**
	 * 实名已经存在
	 */
	public static final String REAL_NAME_EXIST = "REAL_NAME_EXIST";

	/**
	 * 银行卡已经存在
	 */
	public static final String BANK_CARD_EXIST = "BANK_CARD_EXIST";

	/**
	 * 开户子账户已经存在
	 */
	public static final String ACCOUNT_SUBACCOUNT_AUTH_RETURN_FAIL = "SUBACCOUNT_AUTH_RETURN_FAIL";

	/**
	 * 开户子账户不存在
	 */
	public static final String ACCOUNT_SUBACCOUNT_NOT_EXIST = "SUBACCOUNT_NOT_EXIST";

	/**
	 * 开户子账户不存在
	 */
	public static final String ACCOUNT_USERINFO_NOT_EXIST = "USERINFO_NOT_EXIST";

	/**
	 * TRY_LOCK_FAIL:加锁失败.
	 */
	public static final String TRY_LOCK_FAIL = "TRY_LOCK_FAIL";

	/**
	 * SERVICE_ERROR:服务异常.
	 */
	public static final String SERVICE_ERROR = "SERVICE_ERROR";
}
