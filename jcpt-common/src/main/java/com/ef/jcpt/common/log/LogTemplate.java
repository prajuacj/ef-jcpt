package com.ef.jcpt.common.log;

public class LogTemplate {

	public static final String COMMON_BIZ = "cmd=%s | result=%s | sid=%s | tid=%s | uid=%s | msg=%s |";

	public static final String COMMON_BIZ_REQ = "cmd=%s | result=%s | sid=%s | tid=%s | uid=%s | msg=%s | data=%s |";

	public static final String COMMON_SYS = "cmd=%s | result=%s | msg=%s |";

	public static final String COMMON_SYS_OK = "cmd=%s | result=OK | msg=%s |";

	public static final String COMMON_SYS_FAIL = "cmd=%s | result=FAIL | msg=%s |";

	public static final String COMMON_SYS_OK_1 = "cmd=%s | result=OK | tid=%s | msg=%s |";

	public static final String COMMON_SYS_FAIL_1 = "cmd=%s | result=FAIL | tid=%s | msg=%s |";

	/**
	 * genCommonBizLogStr:生成通用业务日志输出内容. <br>
	 * 
	 * @author xiezbmf
	 * @Date 2016年12月7日上午9:27:38 <br>
	 * @param cmd
	 *            调用方法
	 * @param result
	 *            结果
	 * @param sid
	 *            会话id
	 * @param tid
	 *            请求唯一标识
	 * @param uid
	 *            操作员id
	 * @param msg
	 *            日志详细信息
	 * @return 日志内容
	 */
	public static final String genCommonBizLogStr(String cmd, String result, String sid, String tid, String uid,
			String msg) {
		return String.format(COMMON_BIZ, cmd, result, sid, tid, uid, msg);
	}

	/**
	 * genCommonBizReqLogStr:生成web通用业务请求日志输出内容. <br>
	 * 
	 * @author xiezbmf
	 * @Date 2016年12月7日上午9:27:38 <br>
	 * @param cmd
	 *            调用方法
	 * @param result
	 *            结果
	 * @param sid
	 *            会话id
	 * @param tid
	 *            请求唯一标识
	 * @param uid
	 *            操作员id
	 * @param msg
	 *            日志详细信息
	 * @param data
	 *            请求数据内容
	 * @return 日志内容
	 */
	public static final String genCommonBizReqLogStr(String cmd, String result, String sid, String tid, String uid,
			String msg, String data) {
		return String.format(COMMON_BIZ_REQ, cmd, result, sid, tid, uid, msg, data);
	}

	/**
	 * genCommonSysLogStr:生成通用系统日志输出内容. <br>
	 * 
	 * @author xiezbmf
	 * @Date 2016年12月7日上午9:27:38 <br>
	 * @param cmd
	 *            调用方法
	 * @param result
	 *            结果
	 * @param msg
	 *            日志详细信息
	 * @return 日志内容
	 */
	public static final String genCommonSysLogStr(String cmd, String result, String msg) {
		return String.format(COMMON_SYS, cmd, result, msg);
	}

	/**
	 * genCommonBizLogStr:生成通用系统成功日志输出内容. <br>
	 * 
	 * @author xiezbmf
	 * @Date 2016年12月7日上午9:27:38 <br>
	 * @param cmd
	 *            调用方法
	 * @param msg
	 *            日志详细信息
	 * @return 日志内容
	 */
	public static final String genCommonSysOkLogStr(String cmd, String msg) {
		return String.format(COMMON_SYS_OK, cmd, msg);
	}

	/**
	 * genCommonSysFailLogStr:生成通用系统失败日志输出内容. <br>
	 * 
	 * @author xiezbmf
	 * @Date 2016年12月7日上午9:27:38 <br>
	 * @param cmd
	 *            调用方法
	 * @param msg
	 *            日志详细信息
	 * @return 日志内容
	 */
	public static final String genCommonSysFailLogStr(String cmd, String msg) {
		return String.format(COMMON_SYS_FAIL, cmd, msg);
	}

	/**
	 * genCommonBizLogStr:生成通用系统成功日志输出内容. <br>
	 * 
	 * @author xiezbmf
	 * @Date 2016年12月7日上午9:27:38 <br>
	 * @param cmd
	 *            调用方法
	 * @param msg
	 *            日志详细信息
	 * @param tid
	 *            请求唯一标识
	 * @return 日志内容
	 */
	public static final String genCommonSysOkLogStr(String cmd, String msg, String tid) {
		return String.format(COMMON_SYS_OK_1, cmd, tid, msg);
	}

	/**
	 * genCommonSysFailLogStr:生成通用系统失败日志输出内容. <br>
	 * 
	 * @author xiezbmf
	 * @Date 2016年12月7日上午9:27:38 <br>
	 * @param cmd
	 *            调用方法
	 * @param msg
	 *            日志详细信息
	 * @param tid
	 *            请求唯一标识
	 * @return 日志内容
	 */
	public static final String genCommonSysFailLogStr(String cmd, String msg, String tid) {
		return String.format(COMMON_SYS_FAIL_1, cmd, tid, msg);
	}
}
