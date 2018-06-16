/**
 * Project Name:caifubao-common <br>
 * File Name:ExcelColMapper.java <br>
 * Package Name:com.caifubao.jcpt.common.util <br>
 * @author xiezbmf
 * Date:2017年11月9日下午2:33:25 <br>
 * Copyright (c) 2017, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;

import java.util.Map;

/**
 * ClassName: ExcelColMapper <br>
 * Description: excel列映射关系
 * @author xiezbmf
 * @Date 2017年11月9日下午2:33:25 <br>
 * @version
 * @since JDK 1.6
 */
public class ExcelColMapper {
	
	public static enum ExcelColEnum{
		STRING,
		DATE,
		CODELIST,
		NUMBER
	}
	
	
	private String key;
	private String name;
	private ExcelColEnum type;
	private String format;
	private Map<String,String> codelist;
	
	public ExcelColMapper() {}
	
	public ExcelColMapper(String key,String name,ExcelColEnum type) {
		this.key = key;
		this.name = name;
		this.type = type;
	}
	
	public ExcelColMapper(String key,String name,ExcelColEnum type,String format) {
		this.key = key;
		this.name = name;
		this.type = type;
		this.format = format;
	}
	
	public ExcelColMapper(String key,String name,ExcelColEnum type,Map<String,String> codelist) {
		this.key = key;
		this.name = name;
		this.type = type;
		this.codelist = codelist;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ExcelColEnum getType() {
		return type;
	}
	public void setType(ExcelColEnum type) {
		this.type = type;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Map<String, String> getCodelist() {
		return codelist;
	}
	public void setCodelist(Map<String, String> codelist) {
		this.codelist = codelist;
	}
}



	