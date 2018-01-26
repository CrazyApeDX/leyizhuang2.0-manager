package com.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_DATE = "yyyy-MM-dd";

	public static final String FORMAT_MONTH = "yyyy-MM";

	public static final String FORMAT_TIME = "HH:mm:ss";

	public static long SECOND_MILLIS = 1000L;

	public static long MINUTE_MILLIS = 60 * SECOND_MILLIS;

	public static long HOUR_MILLIS = 60 * MINUTE_MILLIS;

	public static long DAY_MILLIS = 24 * HOUR_MILLIS;

	public static long WEEK_MILLIS = 7 * DAY_MILLIS;

	/**
	 * 格式化时间，转换成int类型形如 20140705
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Integer formatDate2Int(Date date, String pattern) {
		try {
			String result = formatDate(date, pattern);
			return Integer.parseInt(result);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 用户自己定义日期和格式，进行格式化
	 *
	 * @param date 用户指定的日期
	 * @param pattern 用户指定的时间格式
	 * @return 返回指定的格式化后的时间字符串
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null || StringUtils.isEmpty(pattern)) {
			return null;
		}
		SimpleDateFormat datePattern = new SimpleDateFormat(pattern);

		return datePattern.format(date);
	}

	/**
	 * 对指定的日期，使用 yyyy-MM 形式进行格式化
	 *
	 * @param date 指定的日期
	 * @return 返回 yyyy-MM 格式的字符串
	 */
	public static String getMonthStr(Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(FORMAT_MONTH).format(date);
	}

	/**
	 * 对指定的日期，使用 yyyy-MM-dd 形式进行格式化
	 *
	 * @param date指定的日期
	 * @return 返回 yyyy-MM-dd 格式的字符串
	 */
	public static String getDateStr(Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(FORMAT_DATE).format(date);
	}

	/**
	 * 对指定的毫秒数，使用 yyyy-MM-dd 形式进行格式化
	 *
	 * @param timeMillis 指定的毫秒数
	 * @return 返回 yyyy-MM-dd 格式的字符串
	 */
	public static String getDateStr(long timeMillis) {
		return getDateStr(new Date(timeMillis));
	}

	/**
	 * 对指定的日期，使用 yyyy-MM-dd HH:mm:ss 形式进行格式化
	 *
	 * @param date 指定的日期
	 * @return 返回 yyyy-MM-dd HH:mm:ss 格式的字符串
	 */
	public static String getDateTimeStr(Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(FORMAT_DATETIME).format(date);
	}

	/**
	 * 对指定的毫秒数，使用 yyyy-MM-dd HH:mm:ss 形式进行格式化
	 *
	 * @param timeMillis 指定的毫秒数
	 * @return 返回 yyyy-MM-dd HH:mm:ss 格式的字符串
	 */
	public static String getDateTimeStr(long timeMillis) {
		return getDateTimeStr(new Date(timeMillis));
	}

	/**
	 * @return 返回当前时间的 yyyy-MM-dd 格式的字符串
	 */
	public static String getCurrentDateStr() {
		return getDateStr(new Date());
	}

	/**
	 * 返回当前时间指定的格式串.
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDateStr(String format) {
		return formatDate(new Date(), format);
	}

	/**
	 *
	 * @return
	 */
	public static String getCurrentTimeStr() {
		return formatDate(new Date(), FORMAT_TIME);
	}

	/**
	 *
	 * 获取当前微秒数.
	 *
	 * @return
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 *
	 * 获取当前秒数.
	 *
	 * @return
	 */
	public static long getCurrentSeconds() {
		long millis = getCurrentTimeMillis();

		return (millis - millis % 1000) / 1000;
	}

	/**
	 *
	 * @param format
	 * @return
	 */
	public static String getCurrentTimeStr(String format) {
		return formatDate(new Date(), format);
	}

	/**
	 * @return 返回当前时间的 yyyy-MM-dd HH:mm:ss 格式的字符串
	 */
	public static String getCurrentDateTimeStr() {
		return getDateTimeStr(new Date());
	}

	/**
	 * @return 返回当前时间的 yyyy-MM 格式的字符串
	 */
	public static String getCurrentMonthStr() {
		return getMonthStr(new Date());
	}

	/**
	 * 在指定的日期的基础上添加指定单位的数值，然后格式化成 yyyy-MM-dd HH:mm:ss 的字符串后返回
	 *
	 * @param date 指定的日期
	 * @param diffTime 指定的时间数值（如果需要减，则使用负数即可）
	 * @param unit 指定的时间单位
	 * @return 返回 yyyy-MM-dd HH:mm:ss 格式的字符串
	 */
	public static String timeAddToStr(Date date, long diffTime, TimeUnit unit) {
		if (date == null) {
			return null;
		}
		long resultTime = date.getTime() + unit.toMillis(diffTime);

		return getDateTimeStr(resultTime);
	}

	/**
	 * 在指定的日期的基础上添加指定单位的数值，并返回
	 *
	 * @param date 指定的日期
	 * @param diffTime 指定的时间数值，可以为负数
	 * @param unit 指定的时间单位
	 * @return 返回计算之后的日期
	 */
	public static Date timeAdd(Date date, long diffTime, TimeUnit unit) {
		if (date == null) {
			return null;
		}
		long resultTime = date.getTime() + unit.toMillis(diffTime);

		return new Date(resultTime);
	}

	/**
	 * 在指定的日期上添加指定days天数，然后返回
	 *
	 * @param date 指定的日期
	 * @param days 需要添加的天数，可以为负数
	 * @return 在指定的日期上添加指定days天数，然后返回
	 */
	public static Date timeAddByDays(Date date, int days) {
		return timeAdd(date, days, TimeUnit.DAYS);
	}

	/**
	 * @param date 日期
	 * @param months 需要添加的月份数，可以为负数
	 * @return 在指定的日期上添加指定months个月，然后返回
	 */
	public static Date timeAddByMonth(Date date, int months) {
		if (date == null) {
			return null;
		}
		if (months == 0) {
			return date;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);

		return cal.getTime();
	}

	/**
	 * 返回指定日期所在月份的第一天的日期
	 *
	 * @param date
	 * @return 返回指定日期所在月份的第一天的日期
	 */
	public static String getFirstDayOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		return getDateStr(cal.getTime());
	}

	/**
	 * @return 返回昨天的日期字符串，格式为 yyyy-MM-dd
	 */
	public static String getYestoday() {
		return timeAddToStr(new Date(), -1, TimeUnit.DAYS).split(" ")[0];
	}

	/**
	 * 按照 yyyy-MM-dd 的格式解析给定的日期字符串
	 *
	 * @param dateStr 给定的日期字符串
	 * @return 返回解析后的日期，如果解析失败，则返回null
	 */
	public static Date parseDate(String dateStr) {
		try {
			return new SimpleDateFormat(FORMAT_DATE).parse(dateStr);
		} catch (ParseException e) {
			LOGGER.error("parse '" + dateStr + "' error", e);
		}

		return null;
	}

	/**
	 * 按照 yyyy-MM-dd HH:mm:ss 的格式解析给定的日期字符串
	 *
	 * @param dateTimeStr 给定的日期字符串
	 * @return 返回解析后的日期，如果解析失败，则返回null
	 */
	public static Date parseDateTime(String dateTimeStr) {
		try {
			return new SimpleDateFormat(FORMAT_DATETIME).parse(dateTimeStr);
		} catch (ParseException e) {
			LOGGER.error("parse '" + dateTimeStr + "' error", e);
		}

		return null;
	}

	/**
	 * 按照指定的format格式解析给定的日期字符串
	 *
	 * @param dateStr 给定的日期字符串
	 * @param format 指定的日期格式
	 * @return 将日期字符串解析成Date对象
	 */
	public static Date parseToDate(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			LOGGER.error("parse '" + dateStr + "' with pattern '" + format + "' error", e);
		}
		return date;
	}

	/**
	 * 将给定的日期字符串按照 yyyy-MM-dd HH:mm:ss 格式解析成Timestamp对象
	 *
	 * @param dateTimeStr 给定的日期字符串
	 * @return 返回解析成功后的Timestamp对象
	 */
	public static Timestamp parseTimestamp(String dateTimeStr) {
		Date date = parseDateTime(dateTimeStr);

		return convert(date);
	}

	/**
	 * @return 返回当前时间的Timestamp对象
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @param date 指定的Date对象
	 * @return 将指定的Date对象转换成Timestamp对象
	 */
	public static Timestamp convert(Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * @param timestamp 指定的Timestamp对象
	 * @return 将指定的Timestamp对象转换成Date对象
	 */
	public static Date convert(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return new Date(timestamp.getTime());
	}

	/**
	 * 对给定的两个日期进行比较，如果date1 比 date2 大，则返回1；如果相等，则返回0；否则返回-1
	 *
	 * @param date1
	 * @param date2
	 * @return 对给定的两个日期进行比较，如果date1 比 date2 大，则返回1；如果相等，则返回0；否则返回-1
	 */
	public static int compare(Date date1, Date date2) {
		if (date1 == null) {
			return -1;
		}
		if (date2 == null) {
			return 1;
		}
		long timeDiff = date1.getTime() - date2.getTime();

		return timeDiff == 0 ? 0 : (int) (timeDiff / Math.abs(timeDiff));
	}

	/**
	 * 计算两个时间相差的天数
	 * 
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static long intervalDay(Date beginTime, Date endTime) {

		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginTime);
		beginCalendar.set(Calendar.HOUR_OF_DAY, 0);
		beginCalendar.set(Calendar.MINUTE, 0);
		beginCalendar.set(Calendar.SECOND, 0);
		beginCalendar.set(Calendar.MILLISECOND, 0);

		Calendar endTimeCalendar = Calendar.getInstance();
		endTimeCalendar.setTime(endTime);
		endTimeCalendar.set(Calendar.HOUR_OF_DAY, 0);
		endTimeCalendar.set(Calendar.MINUTE, 0);
		endTimeCalendar.set(Calendar.SECOND, 0);
		endTimeCalendar.set(Calendar.MILLISECOND, 0);

		return (endTimeCalendar.getTimeInMillis() - beginCalendar.getTimeInMillis()) / (1000 * 60 * 60 * 24);

	}

	/**
	 * 判断传入日期是否是周末（周六或者周日）
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWeek(Date date) {
		boolean isWeek = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			isWeek = true;
		}
		return isWeek;
	}

	/**
	 * 获取当天最小的时间yyyy-MM-dd 00:00:00
	 * 
	 * @return
	 */
	public static Date getCurMinDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
		return calendar.getTime();
	}

	/**
	 * 获取当天最大的时间 yyyy-MM-dd 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurMaxDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		return calendar.getTime();
	}
	
	/**
	* 得到现在小时
	*/
	public static Integer getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return Integer.parseInt(hour);
	}

}
