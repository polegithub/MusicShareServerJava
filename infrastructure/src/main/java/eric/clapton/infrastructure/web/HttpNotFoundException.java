package eric.clapton.infrastructure.web;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 找不到资源时，抛出该错误。
 * 
 * @author cheer
 *
 */
@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class HttpNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2358669528392663660L;

	public HttpNotFoundException() {
		super();
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return null;
	}
}
