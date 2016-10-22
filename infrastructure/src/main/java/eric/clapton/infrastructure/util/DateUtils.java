package eric.clapton.infrastructure.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	protected DateUtils() {

	}

	/**
	 * 使用指定的日期格式，格式化日历对象。
	 * 
	 * @param c
	 *            要格式化的日历对象。
	 * @param dateFormat
	 *            日期格式，不能为 <code>null</code>。
	 * @return
	 */
	public static final String formatDate(final Calendar c, final DateFormat dateFormat) {
		return c == null ? null : formatDate(c.getTime(), dateFormat);
	}

	public static final String formatDate(final Date d, final DateFormat dateFormat) {
		Assure.isNotNull(dateFormat, "dateFormat");
		return d == null ? null : dateFormat.format(d);
	}

	public static final String formatDate(final Date d, final String pattern) {
		return d == null ? null : formatDate(d, new SimpleDateFormat(pattern));
	}

	public static final String formatDate(final Date d, final String pattern, final Locale locale) {
		return d == null ? null : formatDate(d, new SimpleDateFormat(pattern, locale));
	}

	public static final String formatDate(final Calendar c, final String pattern) {
		return c == null ? null : formatDate(c.getTime(), pattern);
	}

	public static final String formatDate(final Calendar c, final String pattern, final Locale locale) {
		return c == null ? null : formatDate(c.getTime(), pattern, locale);
	}

	public static int compare(final Date x, final Date y) {
		if (x == null) {
			return y == null ? 0 : -1;
		}
		if (y == null) {
			return 1;
		}
		return x.compareTo(y);
	}

	public static int compare(final Calendar x, final Calendar y) {
		if (x == null) {
			return y == null ? 0 : -1;
		}
		if (y == null) {
			return 1;
		}
		return x.compareTo(y);
	}

	public static boolean equal(final Date x, final Date y) {
		return x == null ? y == null : y == null ? false : x.equals(y);
	}

	public static boolean equal(final Calendar x, final Calendar y) {
		return x == null ? y == null : y == null ? false : x.equals(y);
	}

	public static final Calendar earliest(final Calendar c1, final Calendar c2) {
		return c1 == null ? c1 : c2 == null ? c2 : c1.after(c2) ? c2 : c1;
	}

	public static final Calendar latest(final Calendar c1, final Calendar c2) {
		return earliest(c1, c2) == c1 ? c2 : c1;
	}

	public static Calendar toCalendar(final Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;
	}

	public static Date toDate(final Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.getTime();
	}

	public static final Date trunc(final Date date) {
		return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 提供和 Oracle <code>TRUNC</code> 函数类似功能的方法：将 {@link Calendar}
	 * 对象的时间部分去除，只保留日期部分。
	 * 
	 * @param c
	 *            要去除时间部分的 {@link Calendar} 对象；如果为 <code>null</code>，则为
	 *            <code>null</code> 。
	 * @return
	 */
	public static final Calendar trunc(final Calendar c) {
		if (c == null) {
			return null;
		}
		return org.apache.commons.lang3.time.DateUtils.truncate(c, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回一个 {@link Calendar} 对象，它表示的时刻为当前时刻的前一天（24小时之前）。
	 * 
	 * @see #tomorrow()
	 * @return
	 */
	public static Calendar oneDayBefore() {
		return oneDayBefore(null);
	}

	/**
	 * 返回一个 {@link Calendar} 对象，它表示的时刻为当前时刻的后一天（24小时之前）。
	 * 
	 * @see #tomorrow()
	 * @return
	 */
	public final static Calendar oneDayLater() {
		return oneDayLater(null);
	}

	/**
	 * 返回一个新的 {@link Calendar} 对象，它表示的时刻为指定 {@link Calendar}
	 * 对象表示的时间/日期的前一天（24小时之前）。
	 * 
	 * @param c
	 *            要获得之前一天时刻的 {@link Calendar} 对象。如果为 <code>null</code>，则为当前时间。
	 * @return
	 */
	public static final Calendar oneDayBefore(final Calendar c) {
		Calendar calendar = clone(c);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return calendar;
	}

	/**
	 * 返回一个新的 {@link Calendar} 对象，它表示的时刻为指定 {@link Calendar}
	 * 对象表示的时间/日期的后一天（24小时之后）。
	 * 
	 * @param c
	 *            要获得之后一天时刻的 {@link Calendar} 对象。如果为 <code>null</code>，则为当前时间。
	 * @return
	 */
	public static final Calendar oneDayLater(final Calendar c) {
		Calendar calendar = clone(c);
		calendar.add(Calendar.DAY_OF_YEAR, +1);
		return calendar;
	}

	/**
	 * 获取当前日期的 {@link Calendar} 表示，这不包括时间部分。
	 * 
	 * @return
	 */
	public static final Calendar today() {
		return trunc(Calendar.getInstance());
	}

	/**
	 * 获取昨日的 {@link Calendar} 表示，这不包括时间部分。如果要包括时间部分，请调用{@link #oneDayBefore()}
	 * 方法。
	 * 
	 * @see #oneDayBefore()
	 * 
	 * @return
	 */
	public static final Calendar yesterday() {
		return trunc(oneDayBefore());
	}

	/**
	 * 获取明日的 {@link Calendar} 表示，这不包括时间部分。如果要包括时间部分，请调用{@link #oneDayLater()}
	 * 方法。
	 * 
	 * @see #oneDayLater()
	 * @return
	 */
	public static final Calendar tomorrow() {
		return trunc(oneDayLater());
	}

	private static Calendar clone(final Calendar c) {
		return c == null ? Calendar.getInstance() : (Calendar) c.clone();
	}
	
	
	public static String calendar2DateString(Date date, String formater) {
		SimpleDateFormat df = new SimpleDateFormat(formater);
		String time = df.format(date);
		return time;
	}

	public static String calendar2DateString(Date date) {
		return calendar2DateString(date, "yyyy-MM-dd HH:mm:ss");
	}

	// 获取昨天的日期
	public static Date calendar2Yesterday() {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, -1); // 得到前一天
		return calendar.getTime();
	}

	public static Date calendar2Before(int amount) {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, -amount); // 得到前amount天

		return calendar.getTime();
	}

	public static Date string2Date(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			try {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				date = sdf.parse(str);
			} catch (ParseException p) {
				try {
					sdf = new SimpleDateFormat("yyyy/MM/dd");
					date = sdf.parse(str);
				} catch (ParseException pe) {
					try {
						sdf = new SimpleDateFormat("yyyy/M/dd");
						date = sdf.parse(str);
					} catch (ParseException d) {
						sdf = new SimpleDateFormat("yyyy.MM.dd");
						date = sdf.parse(str);

					}
				}
			}
		}
		return date;
	}

	public static Calendar String2Calendar(String str) throws ParseException {
		Date date = string2Date(str);
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		return instance;
	}

	public static boolean isSomeMonth(Date firstDate, Date secondDate) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(firstDate);
		cal2.setTime(secondDate);
		int i = cal1.get(Calendar.MONTH);
		int j = cal2.get(Calendar.MONTH);
		if (i == j) {
			return true;
		}
		return false;
	}

	

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/** 根据传入的日期，获得与给的日期相同月份的最后一天的日期 */
	public static Date getEndofMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 将下个月设为当前月
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);// 设置为第一天
		cal.add(Calendar.DATE, -1);// 再减一天即为上个月最后一天
		return cal.getTime();
	}

	/**
	 * 
	 * @param num
	 *            相加或相减的月份
	 * @param date
	 *            被相加或相减的日期
	 * @return yyyy-MM
	 */
	public static String addMonth(int num, String date) {
		try {
			Date month = new SimpleDateFormat("yyyy-MM").parse(date);
			Date newMonth = org.apache.commons.lang3.time.DateUtils.addMonths(month, num);
			return DateUtils.calendar2DateString(newMonth, "yyyy-MM");
		} catch (ParseException e) {
			return null;
		}
	}

	
	/*
	 * stmp : 时间戳毫秒数
	 */
	public static String milStmp2Date(Long stmp) {
		return fomatDate(new Date(stmp),"yyyy-MM-dd HH:mm:ss");
	}

	
	/*
	 * stmp : 时间戳毫秒数
	 */
	public static Date milStmTtoDate(Long stmp) {
		return new Date(stmp);
	}
	
	
	/*
	 * stmp : 时间戳秒数
	 */
	public static String secStmp2Date(Long stmp) {
		return fomatDate(new Date(stmp* 1000),"yyyy-MM-dd HH:mm:ss");
	}
	
	/*
	 * stmp : 时间戳秒数
	 */
	public static Date secStmpToDate(Long stmp) {
		return new Date(stmp * 1000);
	}
	
	/*
	 * 格式化 yyyy-MM-dd
	 */
	public static String fomatDate(Date date) {
		return fomatDate(date, "yyyy-MM-dd");
	}

	/*
	 * 格式化 yyyy-MM-dd
	 */
	public static String fomatDate(Date date, String style) {
		SimpleDateFormat format = new SimpleDateFormat(style);
		return format.format(date);
	}
	
	/*
	 * 格式化 yyyy-MM-dd
	 */
	public static Date toDate(String date, String style) {
		SimpleDateFormat format = new SimpleDateFormat(style);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
