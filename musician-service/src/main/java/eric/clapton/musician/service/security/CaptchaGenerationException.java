package eric.clapton.musician.service.security;

import org.springframework.core.NestedCheckedException;

public class CaptchaGenerationException extends NestedCheckedException {
	private static final long serialVersionUID = -1522307214877694160L;

	public CaptchaGenerationException(String msg) {
		super(msg);
	}

	public CaptchaGenerationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
