package com.ef.jcpt.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ef.jcpt.common.log.LogTemplate;

public class SerializableUtil {

	private static final Log logger = LogFactory.getLog(SerializableUtil.class);

	/**
	 * 
	 * serialize:序列化对象. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年7月6日上午11:25:03 <br>
	 * @param t
	 *            待序列化对象
	 * @return byte[] 序列化后的字节码
	 */
	public static <T> byte[] serialize(T t) {
		byte[] result = null;

		if (t == null) {
			return result;
		}
		if (!(t instanceof Serializable)) {
			throw new IllegalArgumentException(t.getClass().getName() + " must implements Serializable");
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(t);
			oos.flush();
			result = bos.toByteArray();
			oos.close();
			bos.close();
			return result;
		} catch (Exception e) {
			String cmd = "utils:serialize";
			String msg = "serialize " + t.getClass().getName() + " failed," + e.getMessage();
			logger.error(LogTemplate.genCommonSysFailLogStr(cmd, msg));
			return null;
		}
	}

	/**
	 * 
	 * deserialize:反序列化对象. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年7月6日上午11:30:57 <br>
	 * @param bytes
	 *            字节码
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		Object result = null;
		if (bytes == null || bytes.length == 0) {
			return result;
		}
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			result = ois.readObject();
			bis.close();
			ois.close();
		} catch (Exception e) {
			String cmd = "utils:deserialize";
			String msg = "deserialize failed," + e.getMessage();
			logger.error(LogTemplate.genCommonSysFailLogStr(cmd, msg));
		}
		return result;
	}
}
