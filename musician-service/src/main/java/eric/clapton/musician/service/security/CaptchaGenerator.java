package eric.clapton.musician.service.security;

public interface CaptchaGenerator {
	public static final String CONTENT_TYPE_TEXT = "text/plain";

	String getContentType();

	byte[] generate() throws CaptchaGenerationException;
}
