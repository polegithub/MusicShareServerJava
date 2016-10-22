package eric.clapton.musician.service.account;

public interface SmsCaptchaService {
	void createCaptchaFor(String phoneNumber);
}
