package com.common.util;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class RandomUtil {
	private final static Random random = new Random(System.currentTimeMillis());

	private static final String[] NUM_ARR = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	private static final String[] UPPER_ALPHA_ARR = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	private static final String[] ALPHA_NUM_ARR = { "1", "A", "B", "2", "C", "D", "3", "E", "F", "G", "7", "H", "I", "5", "J",
			"K", "L", "M", "4", "N", "O", "P", "Q", "9", "R", "S", "T", "0", "U", "8", "V", "W", "X", "6", "Y", "Z" };

	/**
	 * 求指定范围的随机数
	 * 
	 * @param range 指定的int值，即随机的范围
	 * @return 返回一个0到int值（不包括int值）之间的随机整数
	 */
	public static int rand(int range) {
		return Math.abs(random.nextInt()) % range;
	}

	/**
	 * 生成指定长度的字符串
	 * 
	 * @param strarray 字符库数组
	 * @param codeLength 字符串长度
	 * @return 在strarray数组中随机抽取codeLength个元素，并按抽取的顺序连接成字符串返回
	 */
	private static String randomCode(String[] strarray, int codeLength) {
		StringBuilder sb = new StringBuilder();
		int len = strarray.length;
		Random random = new Random();
		for (int i = 0; i < codeLength; i++) {
			sb.append(strarray[random.nextInt(len)]);
		}
		return sb.toString();
	}

	/**
	 * 生成指定长度的随机字符串（数字+大写英文字母，不保证字符串会出现纯数字和纯字母的情况）
	 * 
	 * @param codeLength 字符串长度
	 * @return 在静态成员strs数组中随机抽取codeLength个元素，并按抽取的顺序连接成字符串返回
	 */
	public static String randomStrCode(int codeLength) {
		return randomCode(ALPHA_NUM_ARR, codeLength);
	}

	/**
	 * 生成指定长度随机字符串（必须包含大小写字母和数字），保证不会出现纯数字和纯字母的情况
	 *
	 * @param length 随机数长度
	 * @return 随机字符串
	 */
	public static String randomAlphaAndNumStr(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= length; i++) {
			if (i == length) {
				if (StringUtils.isAlpha(sb.toString())) {
					sb.append(getRandom("num"));
				} else if (StringUtils.isNumeric(sb.toString())) {
					sb.append(getRandom("upper"));
				} else {
					sb.append(getRandom(""));
				}
			} else {
				sb.append(getRandom(""));
			}
		}
		return sb.toString();
	}

	/**
	 * 生成一个随机字符
	 *
	 * @param charOrNum 随机生成类型：upper大写字母，lower小写字母，num数字，否则三种类型随机生成
	 * @return 随机字符
	 */
	private static String getRandom(String charOrNum) {
		Random random = new Random();
		// 输出字母还是数字
		if ("upper".equalsIgnoreCase(charOrNum)) {
			// 大写字母
			return UPPER_ALPHA_ARR[random.nextInt(UPPER_ALPHA_ARR.length)];
		} else if ("num".equalsIgnoreCase(charOrNum)) {
			// 数字
			return NUM_ARR[random.nextInt(NUM_ARR.length)];
		} else {
			return ALPHA_NUM_ARR[random.nextInt(ALPHA_NUM_ARR.length)];
		}
	}

	/**
	 * 生成指定长度的随机字符串(数字)
	 * 
	 * @param codeLength 字符串长度
	 * @return 在静态成员ints数组中随机抽取codeLength个元素，并按抽取的顺序连接成字符串返回
	 */
	public static String randomNumCode(int codeLength) {
		return randomCode(NUM_ARR, codeLength);
	}

	/**
	 * 
	 * @param min 生成随机数的最小值
	 * @param max 生成随机数的最大值
	 * @return 生成min(不包括min值)到max(不包括max值)之间的随机整数
	 */
	public static int randomNumRange(int min, int max) {
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	/**
	 * 
	 * @param range 随机数的最大值
	 * @return 利用正太分布的随机小数来生成正太分布的指定范围的随机整数；返回0（包含）到range（不包含）之间的整数
	 */
	public static int nextGaussian(int range) {
		double randData = Math.abs(random.nextGaussian());
		int gendata = (int) (randData * range);
		if (gendata == 0) {
			return 0;
		}

		return gendata / (int) Math.ceil(randData);
	}
}
