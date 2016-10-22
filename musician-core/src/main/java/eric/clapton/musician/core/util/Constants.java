package eric.clapton.musician.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {
	protected Constants() {

	}

	public static final String SYSTEM = "(系统)";
	/**
	 * 应用程序默认的日期格式“yyyy-MM-dd”，如 <i>2013-08-02</i>。
	 */
	public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final String SESSION_ADMIN_NAME = "session_admin";
	
	public static final String ERROR_CODE_NAME = "code";
	
	public static final String ERROR_MESSAGE_NAME = "message";
	
	public static final String ERROR_FORWORD_ACTION_NAME = "action";
	
	
	public static final String[] PUBLISH_ORDER_COLUMN = {"timeid","title","description","addressid","accountid","created","orderid","price","starttime","endtime","remark","state","needcount","deadline","addressName","longitude","latitude","address","detail_address","city","contact","tel_nrs"};

	public static final int PAGE_SIZE = 20;
	
	public static final String[] PUBLISH_GRAP_ORDER_COLUMN = {"timeid","title","description","addressid","accountid","created","orderid","price","starttime","endtime","remark","state","needcount","deadline","addressName","longitude","latitude","address","detail_address","city","contact","tel_nrs","userid"};
	
	
	public static final String[] FIELD_ORDER_COLUMN = {"timeid","title","description","addressid","accountid","created","price","starttime","endtime","remark","state","needcount","deadline","addressName","longitude","latitude","address","detail_address","city","contact","tel_nrs"};

	
}
