package com.ynyes.lyz.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 辅助double数据类型进行高精度计算的工具类
 * 
 * @author dengxiao created on 2017-08-07
 */
public class CountUtil {

	// 正则表达式：整数或小数点后最多两位的小数
	private final static String REGEX = "^-?[0-9]+(.([0-9]{1,2}))?$";

	// 转换数字为2位小数的转换模板
	private final static DecimalFormat FORMATTER = new DecimalFormat("0.00");

	/**
	 * 转换数字为二位小数的方法 策略：舍去
	 * 
	 * @param number
	 *            被转换的数字
	 * @return 转换结果
	 * @author dengxiao
	 */
	public static final double HALF_UP_SCALE_2(Double number) {
		if (null == number) {
			return 0d;
		} else {
			String data = Double.toString(number);
			if (Pattern.matches(REGEX, data)) {
				return number;
			} else {
				return Double.parseDouble(FORMATTER.format(number));
			}
		}
	}

	/**
	 * 对一组数字进行求和的操作
	 * 
	 * @param data
	 *            求和数字组
	 * @return 计算结果
	 */
	public static final double add(double... data) {
		BigDecimal result = new BigDecimal(0);
		for (double number : data) {
			number = HALF_UP_SCALE_2(number);
			BigDecimal decimal = new BigDecimal(Double.toString(number));
			result = result.add(decimal);
		}
		return HALF_UP_SCALE_2(result.doubleValue());
	}

	/**
	 * 对一个基础数字基于一组数字进行连续求差的方法
	 * 
	 * @param origin
	 *            求差的基础数字
	 * @param data
	 *            数字组
	 * @return 计算结果
	 * @author dengxiao
	 */
	public static final double sub(double origin, double... data) {
		BigDecimal result = new BigDecimal(HALF_UP_SCALE_2(origin));
		for (double number : data) {
			number = HALF_UP_SCALE_2(number);
			BigDecimal decimal = new BigDecimal(Double.toString(number));
			result = result.subtract(decimal);
		}
		return HALF_UP_SCALE_2(result.doubleValue());
	}

	/**
	 * 对一组数字进行求积的方法
	 * 
	 * @param data
	 *            数字组
	 * @return 计算结果
	 * @author dengxiao
	 */
	public static final double mul(double... data) {
		if (data.length > 0) {
			BigDecimal result = new BigDecimal(HALF_UP_SCALE_2(data[0]));
			for (int i = 1; i < data.length; i++) {
				double number = data[i];
				if (0d == number) {
					return 0d;
				} else {
					BigDecimal decimal = new BigDecimal(Double.toString(number));
					result = result.multiply(decimal);
				}
			}
			return HALF_UP_SCALE_2(result.doubleValue());
		} else {
			return 0d;
		}
	}

	/**
	 * 对一个基础数字基于一组数字进行连续求商的方法
	 * 
	 * @param origin
	 *            基础数字
	 * @param data
	 *            数字组
	 * @return 计算结果
	 * @throws ArithmeticException
	 *             当某一次计算除数为0的时候抛出
	 * @author dengxiao
	 */
	public static final double div(double origin, double... data) throws ArithmeticException {
		if (0d == origin) {
			return 0d;
		} else {
			BigDecimal result = new BigDecimal(HALF_UP_SCALE_2(origin));
			for (double number : data) {
				if (0d == number) {
					throw new ArithmeticException("除数为0");
				} else {
					BigDecimal decimal = new BigDecimal(Double.toString(number));
					result = result.divide(decimal, 2, BigDecimal.ROUND_HALF_UP);
				}
			}
			return HALF_UP_SCALE_2(result.doubleValue());
		}
	}
}
