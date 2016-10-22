package eric.clapton.musician.web;

import org.springframework.core.NestedRuntimeException;

public class ApiInterceptionException extends NestedRuntimeException {
	private static final long serialVersionUID = -3767609131408229730L;

	public ApiInterceptionException(String message) {
		super(message);
	}

	public ApiInterceptionException(String message, Throwable cause) {
		super(message, cause);
	}

}
