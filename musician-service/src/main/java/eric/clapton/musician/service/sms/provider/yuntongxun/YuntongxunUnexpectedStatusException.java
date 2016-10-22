package eric.clapton.musician.service.sms.provider.yuntongxun;

import java.util.Locale;

import eric.clapton.infrastructure.util.StringUtils;

public class YuntongxunUnexpectedStatusException extends YuntongxunException {
	private static final long serialVersionUID = 2341672694054040460L;
	private final String statusMsg;
	private final String statusCode;

	public YuntongxunUnexpectedStatusException(String statusCode, String statusMsg) {
		super(buildExceptionMessage(statusCode, statusMsg));

		this.statusMsg = statusMsg;
		this.statusCode = statusCode;
	}

	public final String getStatusMsg() {
		return statusMsg;
	}

	public final String getStatusCode() {
		return statusCode;
	}

	private static String buildExceptionMessage(String statusCode, String statusMsg) {
		if (StringUtils.isNullOrEmpty(statusMsg)) {
			statusMsg = "[未知的错误]";
		}
		if (StringUtils.isNullOrEmpty(statusCode)) {
			return statusMsg;
		}
		return String.format(Locale.CHINA, "%s (错误代码 %s)", statusMsg, statusCode);
	}
}
