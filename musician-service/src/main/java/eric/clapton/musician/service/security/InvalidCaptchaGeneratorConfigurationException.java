package eric.clapton.musician.service.security;

public class InvalidCaptchaGeneratorConfigurationException extends CaptchaGenerationException {
	private static final long serialVersionUID = 5136058733183708559L;

	public InvalidCaptchaGeneratorConfigurationException(String message) {
		super(message);
	}

	public InvalidCaptchaGeneratorConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
}
