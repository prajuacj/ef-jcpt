/**
 * Project Name:caifubao-core <br>
 * File Name:DateUtil.java <br>
 * Package Name:com.caifubao.jcpt.core.utils <br>
 * @author xiezbmf
 * Date:2017年10月18日上午9:00:04 <br>
 * Copyright (c) 2017, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName: DateUtil <br>
 * Description: 日期工具类
 * 
 * @author xiezbmf
 * @Date 2017年10月18日上午9:00:04 <br>
 * @version
 * @since JDK 1.6
 */
public class DateUtil {
	public final static String DEFAULT_PATTERN = "yyyy-MM-dd";
	public final static String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
	public final static String YMD_HM = "yyyy-MM-dd HH:mm";
	public final static String YM = "yyyy-MM";
	public final static String YMD_HMS_NUM = "yyyyMMddHHmmss";
	public final static String YMD_HM_NUM = "yyyyMMddHHmm";
	public final static String YMD_NUM = "yyyyMMdd";
	public final static String YMD_H_NUM = "yyyyMMddHH";
	public final static String YYYYMM = "yyyyMM";
	public final static String YYYY = "yyyy";
	public final static String YYYYMMDD = "yyyyMMdd";
	public final static String YYYYMMDD_SEPARATOR = "yyyy/MM/dd";
	public final static String HHMMSS = "hhmmss";
	
	/**
	 * 
	 * format:默认format. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年10月18日上午9:23:29 <br>
	 * @param date
	 * @return date为空时返回空字符串
	 */
	public static String format(Date date) {
		return date == null ? "" : format(date, DEFAULT_PATTERN);
	}

	
	/**
	 * format:按pattern进行format. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年10月18日上午9:24:04 <br>
	 * @param date
	 * @param pattern
	 * @return date为空时返回空字符串
	 */
	public static String format(Date date, String pattern) {
		return date == null ? "" : new SimpleDateFormat(pattern).format(date);
	}

	public static Date parse(String strDate) throws ParseException {
		return StringUtil.isEmpty(strDate) ? null : parse(strDate, DEFAULT_PATTERN);
	}

	public static Date parse(String strDate, String pattern) throws ParseException {
		return StringUtil.isEmpty(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
	}
	
	public static Date addYear(Date date,int n){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, n);
		return cal.getTime();
	}
	
	
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, n);
		return cal.getTime();
	}
	
	
	/**
	 * 
	 * getDayStartStr:获取一天中开始日期的字符 yyyy-MM-dd HH:mm:ss. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年11月9日上午11:33:34 <br>
	 * @param ymd yyyy-MM-dd
	 * @return
	 */
	public static String getDayStartStr(String ymd) {
		if(StringUtil.isNotEmpty(ymd)) {
			return ymd + " 00:00:00";
		}
		return null;
	}
	/**
	 * 
	 * getDayStartStr:获取一天中结束日期的字符 yyyy-MM-dd HH:mm:ss. <br>
	 *
	 * @author xiezbmf
	 * @Date 2017年11月9日上午11:33:34 <br>
	 * @param ymd yyyy-MM-dd
	 * @return
	 */
	public static String getDayEndStr(String ymd) {
		if(StringUtil.isNotEmpty(ymd)) {
			return ymd + " 23:59:59";
		}
		return null;
	}
	
	public static String getFirstDayOfCurrentMonth() {
		 Calendar cal = Calendar.getInstance();    
		 cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	     return format(cal.getTime());
	}
	
	public static String getLastDayOfCurrentMonth() {
		 Calendar cal = Calendar.getInstance();    
		 cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
	     return format(cal.getTime());
	}

    /**
     * 判断是否是当天
     * @param date
     * @return
     * @throws Exception
     */
    public static boolean isToday(Date date) throws Exception{
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH)+1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        if(year1 == year2 && month1 == month2 && day1 == day2){
            return true;
        }
        return false;
    }
	
	 public static boolean compareDate(Date dt1, Date dt2) {
        try {
            if (dt1.getTime() >= dt2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
	
}
