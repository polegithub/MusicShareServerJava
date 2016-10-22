package eric.clapton.musician.service.sms.provider.yuntongxun;

import org.springframework.core.NestedCheckedException;

public class YuntongxunException extends NestedCheckedException {
	private static final long serialVersionUID = 2341672694054040460L;

	public YuntongxunException(String message) {
		super(message);
	}

	public YuntongxunException(String message, Throwable cause) {
		super(message, cause);
	}

}
