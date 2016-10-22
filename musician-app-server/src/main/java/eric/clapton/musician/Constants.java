package eric.clapton.musician;

import java.util.Locale;

public class Constants {
	public static final String ERROR_CODE_METHOD_NOT_ALLOWED = "http.405";
	public static final String ERROR_CODE_BAD_REQUEST = "http.400";

	public static final int MAX_PAGE_SIZE = 500;
	public static final int DEFAULT_PAGE_INDEX = 0;
	public static final int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 默认的日期格式：{@value}
	 */
	public static final String DEFAULT_DATE_FORMAT = "yy-MM-dd";
	/**
	 * 默认的时间格式：{@value}
	 */
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	/**
	 * 默认的日期/时间格式：{@value}
	 */
	public static final String DEFAULT_DATE_TIME_FORMAT = DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT;

	/**
	 * 默认的区域性设置（中国）。
	 */
	public static final Locale DEFAULT_LOCALE = Locale.CHINA;

}
