package eric.clapton.musician.util;

import static eric.clapton.infrastructure.util.DateUtils.formatDate;
import static eric.clapton.musician.Constants.DEFAULT_DATE_FORMAT;
import static eric.clapton.musician.Constants.DEFAULT_DATE_TIME_FORMAT;
import static eric.clapton.musician.Constants.DEFAULT_LOCALE;
import static eric.clapton.musician.Constants.DEFAULT_TIME_FORMAT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import eric.clapton.musician.Constants;

/**
 * 提供格式化的工具方法。
 * 
 * @author cheer
 *
 */
public class FormatUtils {
	protected FormatUtils() {

	}

	/**
	 * 格式化为日期字符串。格式为 {@link Constants#DEFAULT_DATE_FORMAT}。
	 * 
	 * @param c
	 *            要格式化的日期，或者 <code>null</code>。
	 * 
	 * @return 如果输入为 <code>null</code>，则为 <code>null</code>，否则为格式化后的字符串。
	 */
	public static final String formatAsDateString(final Date c) {
		return c == null ? null : formatDate(c, getDefaultDateFormat());
	}

	/**
	 * 格式化为日期与时间字符串。格式为 {@link Constants#DEFAULT_DATE_TIME_FORMAT}。
	 * 
	 * @param c
	 *            要格式化的日期，或者 <code>null</code>。
	 * 
	 * @return 如果输入为 <code>null</code>，则为 <code>null</code>，否则为格式化后的字符串。
	 */
	public static final String formatAsDateTimeString(final Date c) {
		return c == null ? null : formatDate(c, getDefaultDateTimeFormat());
	}

	/**
	 * 格式化为时间字符串。格式为 {@link Constants#DEFAULT_TIME_FORMAT}。
	 * 
	 * @param c
	 *            要格式化的日期，或者 <code>null</code>。
	 * 
	 * @return 如果输入为 <code>null</code>，则为 <code>null</code>，否则为格式化后的字符串。
	 */
	public static final String formatAsTimeString(final Date c) {
		return c == null ? null : formatDate(c, getDefaultTimeFormat());
	}

	/**
	 * 格式化为日期字符串。格式为 {@link Constants#DEFAULT_DATE_FORMAT}。
	 * 
	 * @param c
	 *            要格式化的日期，或者 <code>null</code>。
	 * 
	 * @return 如果输入为 <code>null</code>，则为 <code>null</code>，否则为格式化后的字符串。
	 */
	public static final String formatAsDateString(final Calendar c) {
		return c == null ? null : formatDate(c, getDefaultDateFormat());
	}

	/**
	 * 格式化为日期与时间字符串。格式为 {@link Constants#DEFAULT_DATE_TIME_FORMAT}。
	 * 
	 * @param c
	 *            要格式化的日期，或者 <code>null</code>。
	 * 
	 * @return 如果输入为 <code>null</code>，则为 <code>null</code>，否则为格式化后的字符串。
	 */
	public static final String formatAsDateTimeString(final Calendar c) {
		return c == null ? null : formatDate(c, getDefaultDateTimeFormat());
	}

	/**
	 * 格式化为时间字符串。格式为 {@link Constants#DEFAULT_TIME_FORMAT}。
	 * 
	 * @param c
	 *            要格式化的日期，或者 <code>null</code>。
	 * 
	 * @return 如果输入为 <code>null</code>，则为 <code>null</code>，否则为格式化后的字符串。
	 */
	public static final String formatAsTimeString(final Calendar c) {
		return c == null ? null : formatDate(c, getDefaultTimeFormat());
	}

	/**
	 * 获取默认的日期格式，其格式为由常数 {@link Constants#DEFAULT_DATE_FORMAT} 指定，并且使用
	 * {@linkplain Constants#DEFAULT_LOCALE 默认的区域性设置}。
	 * 
	 * @return
	 */
	public static final DateFormat getDefaultDateFormat() {
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT, DEFAULT_LOCALE);
	}

	/**
	 * 获取默认的日期/时间格式，其格式为由常数 {@link Constants#DEFAULT_DATE_TIME_FORMAT} 指定，并且使用
	 * {@linkplain Constants#DEFAULT_LOCALE 默认的区域性设置}。
	 * 
	 * @return
	 */
	public static final DateFormat getDefaultDateTimeFormat() {
		return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT, DEFAULT_LOCALE);
	}

	/**
	 * 获取默认的时间格式，其格式为由常数 {@link Constants#DEFAULT_TIME_FORMAT} 指定，并且使用
	 * {@linkplain Constants#DEFAULT_LOCALE 默认的区域性设置}。
	 * 
	 * @return
	 */
	public static final DateFormat getDefaultTimeFormat() {
		return new SimpleDateFormat(DEFAULT_TIME_FORMAT, DEFAULT_LOCALE);
	}
}
