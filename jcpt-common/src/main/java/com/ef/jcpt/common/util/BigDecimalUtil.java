/**
 * Project Name:caifubao-common <br>
 * File Name:BigDecimalUtil.java <br>
 * Package Name:com.caifubao.jcpt.common.util <br>
 * @author xiezbmf
 * Date:2018年4月27日下午6:12:11 <br>
 * Copyright (c) 2018, 深圳市彩付宝科技有限公司 All Rights Reserved.
 */

package com.ef.jcpt.common.util;
/**
 * Project Name:jcpt-common <br>
 * File Name:BigDecimalUtil.java <br>
 * Package Name:com.hehenian.jcpt.util <br>
 * @author xiezbmf
 * Date:2016年11月18日下午3:38:30 <br>
 * Copyright (c) 2016, 深圳市彩付宝网络技术有限公司 All Rights Reserved.
 */


import java.math.BigDecimal;

/**
 * ClassName: BigDecimalUtil <br>
 * Description: BigDecimal工具类
 * @author xiezbmf
 * @Date 2016年11月18日下午3:38:30 <br>
 * @version
 * @since JDK 1.6
 */
public class BigDecimalUtil {

  /**
   * add:多个double相加，并返回其和. <br>
   *
   * @author xiezbmf
   * @Date 2016年11月18日下午3:43:03 <br>
   * @param value 需相加的double数组
   * @return BigDecimal 相加后的值
   */
  public static BigDecimal add(double ...value) {
    BigDecimal bd = BigDecimal.ZERO;
    for (double d : value) {
      bd = new BigDecimal(Double.toString(d)).add(bd);
    }
    return bd;
  }

  /**
   * add:多个double相加，并返回其和. <br>
   *
   * @author xiezbmf
   * @Date 2016年11月18日下午3:43:34 <br>
   * @param value 需相加的数组
   * @return BigDecimal 相加后的值
   */
  public static BigDecimal add(String ...value) {
    BigDecimal bd = BigDecimal.ZERO;
    for (String d : value) {
      bd = new BigDecimal(d).add(bd);
    }
    return bd;
  }
  
  /**
   * add:多个double相加，并返回其和. <br>
   *
   * @author xiezbmf
   * @Date 2016年11月18日下午3:43:34 <br>
   * @param value 需相加的数组
   * @return BigDecimal 相加后的值
   */
  public static BigDecimal add(BigDecimal ...value) {
    BigDecimal bd = BigDecimal.ZERO;
    for (BigDecimal d : value) {
      if (d != null) {
        bd = d.add(bd);
      }
    }
    return bd;
  }

  /**
   * ifNull:判断对象是否为空，如果为空返回new BigDecimal(dftValue)，否则返回自身. <br>
   *
   * @author xiezbmf
   * @Date 2016年12月9日下午3:33:14 <br>
   * @param value 需判断的对象
   * @param dftValue 默认值 此值若为非法数字，则返回BigDecimal.ZERO
   * @return BigDecimal 处理后的值
   */
  public static BigDecimal ifNull(BigDecimal value,String dftValue) {
    if (value == null) {
      try {
        value = new BigDecimal(dftValue);
      } catch (Exception ex) {
        return BigDecimal.ZERO;
      }
    }
    return value;
  }
  
  /**
   * ifNullZero:判断对象是否为空，如果为空返回BigDecimal.ZERO，否则返回自身. <br>
   *
   * @author xiezbmf
   * @Date 2016年12月9日下午3:32:01 <br>
   * @param value 需判断的对象
   * @return BigDecimal 值
   */
  public static BigDecimal ifNullZero(BigDecimal value) {
    if (value == null) {
      return BigDecimal.ZERO;
    }
    return value;
  }
  
  /**
   * lessThan:判断两个BigDecimal的值大小，左边的值是否小于右边的值 .<br>
   * 形如 left &lt; right. <br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:41:15 <br>
   * @param left 小于号左边的数 not null
   * @param right 小于号右边的数 not null
   * @return boolean left &lt; right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean lessThan(BigDecimal left,BigDecimal right) {
    AssertUtil.notNull(left);
    AssertUtil.notNull(right);
    boolean result = left.doubleValue() < right.doubleValue();
    return result;
  }
  
  /**
   * lessThan:判断当前BigDecimal的值大小是否小于右边指定的double值 .<br>
   * 形如 left &lt; right. <br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:41:15 <br>
   * @param left 小于号左边的数 not null
   * @param right 小于号右边的数 not null
   * @return boolean left &lt; right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean lessThan(BigDecimal left,double right) {
    AssertUtil.notNull(left);
    boolean result = left.doubleValue() < right;
    return result;
  }
  
  /**
   * lessOrEqual:判断两个BigDecimal的值大小，左边的值是否小于等于右边的值 . <br>
   * 形如 left &lt;= right.<br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:45:08 <br>
   * @param left 小于等于号左边的数 not null
   * @param right 小于等于号右边的数 not null
   * @return boolean left &lt;= right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean lessOrEqual(BigDecimal left,BigDecimal right) {
    AssertUtil.notNull(left);
    AssertUtil.notNull(right);
    boolean result = left.doubleValue() <= right.doubleValue();
    return result;
  }
  
  /**
   * lessOrEqual:判断当前BigDecimal的值大小是否小于等于右边指定的double值 . <br>
   * 形如 left &lt;= right.<br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:45:08 <br>
   * @param left 小于等于号左边的数 not null
   * @param right 小于等于号右边的数 not null
   * @return boolean left &lt;= right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean lessOrEqual(BigDecimal left,double right) {
    AssertUtil.notNull(left);
    AssertUtil.notNull(right);
    boolean result = left.doubleValue() <= right;
    return result;
  }
  
  /**
   * equal:判断两个BigDecimal的值大小是否相等.<br>
   * 形如 left == right. <br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:46:16 <br>
   * @param left 等于号左边的值
   * @param right 等于号右边的值
   * @return boolean left == right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean equal(BigDecimal left,BigDecimal right) {
    AssertUtil.notNull(left);
    AssertUtil.notNull(right);
    boolean result = left.doubleValue() == right.doubleValue();
    return result;
  }
  
  /**
   * equal:判断当前BigDecimal的值大小是否等于右边指定的double值.<br>
   * 形如 left == right. <br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:46:16 <br>
   * @param left 等于号左边的值
   * @param right 等于号右边的值
   * @return boolean left == right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean equal(BigDecimal left,double right) {
    AssertUtil.notNull(left);
    AssertUtil.notNull(right);
    boolean result = left.doubleValue() == right;
    return result;
  }
  
  /**
   * unEqual:判断两个BigDecimal的值大小是否不相等.<br>
   * 形如 left != right. <br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:46:16 <br>
   * @param left 等于号左边的值
   * @param right 等于号右边的值
   * @return boolean left != right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean unEqual(BigDecimal left,BigDecimal right) {
    return !equal(left,right);
  }
  
  /**
   * unEqual:判断当前BigDecimal的值大小是否不等于右边指定的double值.<br>
   * 形如 left != right. <br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:46:16 <br>
   * @param left 等于号左边的值
   * @param right 等于号右边的值
   * @return boolean left != right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean unEqual(BigDecimal left,double right) {
    return !equal(left,right);
  }
  
  /**
   * greaterThan:判断两个BigDecimal的值大小，左边的值是否大于右边的值 .<br>
   * 形如 left > right. <br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:41:15 <br>
   * @param left 大于号左边的数 not null
   * @param right 大于号右边的数 not null
   * @return boolean left > right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean greaterThan(BigDecimal left,BigDecimal right) {
    AssertUtil.notNull(left);
    AssertUtil.notNull(right);
    boolean result = left.doubleValue() > right.doubleValue();
    return result;
  }
  
  /**
   * lessThan:判断当前BigDecimal的值大小是否大于右边指定的double值 .<br>
   * 形如 left > right. <br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:41:15 <br>
   * @param left 大于号左边的数 not null
   * @param right 大于号右边的数 not null
   * @return boolean left &lt; right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean greaterThan(BigDecimal left,double right) {
    AssertUtil.notNull(left);
    boolean result = left.doubleValue() > right;
    return result;
  }
  
  /**
   * lessOrEqual:判断两个BigDecimal的值大小，左边的值是否大于等于右边的值 . <br>
   * 形如 left >= right.<br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:45:08 <br>
   * @param left 小于等于号左边的数 not null
   * @param right 小于等于号右边的数 not null
   * @return boolean left >= right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean greaterOrEqual(BigDecimal left,BigDecimal right) {
    AssertUtil.notNull(left);
    AssertUtil.notNull(right);
    boolean result = left.doubleValue() >= right.doubleValue();
    return result;
  }
  
  /**
   * lessOrEqual:判断当前BigDecimal的值大小是否大于等于右边指定的double值 . <br>
   * 形如 left &lt;= right.<br>
   *
   * @author xiezbmf
   * @Date 2016年12月13日下午3:45:08 <br>
   * @param left 大于等于号左边的数 not null
   * @param right 大于等于号右边的数 not null
   * @return boolean left >= right
   * @throws IllegalArgumentException 参数为空时抛出
   */
  public static boolean greaterOrEqual(BigDecimal left,double right) {
    AssertUtil.notNull(left);
    boolean result = left.doubleValue() >= right;
    return result;
  }
  
  /**
   * 多个非空值相乘，如果所有的空为null，则返回null;
   * @param value
   * @return
   * @author ljc
   */
  public static BigDecimal multiply(BigDecimal ...value)
  {
	  BigDecimal result = null;
	    for (BigDecimal d : value) {
	    	if(d!=null)
	    	{
	    		if(result==null)
	    		{
	    			result=d;
	    		}else{
	    			result=result.multiply(d);
	    		}
	    	}
	    }
	    return result;
  }
}

	