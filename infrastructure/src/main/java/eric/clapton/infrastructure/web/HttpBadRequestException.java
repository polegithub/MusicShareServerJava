package eric.clapton.infrastructure.web;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 找不到资源时，抛出该错误。
 * 
 * @author cheer
 *
 */
@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
public class HttpBadRequestException extends RuntimeException {
	private static final long serialVersionUID = -2841490536274104179L;

	public HttpBadRequestException() {
		super();
	}

	public HttpBadRequestException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return null;
	}
}
