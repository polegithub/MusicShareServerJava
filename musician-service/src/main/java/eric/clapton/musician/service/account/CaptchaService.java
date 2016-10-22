package eric.clapton.musician.service.account;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.po.account.Captcha;
import eric.clapton.musician.service.security.CaptchaGenerator;

public interface CaptchaService extends BaseService<Captcha> {
	int getCaptchaExpiresIn();

	/**
	 * 为指定的的手机号创建验证码。
	 * 
	 * @param phoneNumber
	 * @param generator
	 * @return
	 * @throws CaptchaRequestTooFrequentExceptionException
	 */
	Captcha requestCaptchaFor(String phoneNumber, CaptchaGenerator generator)
			throws CaptchaRequestTooFrequentExceptionException;

	Captcha getCaptchaFor(String phoneNumber);

	/**
	 * 将验证码发送到指定的手机号码上。
	 * 
	 * @param phoneNumber
	 * @param captcha
	 */
	void sendCaptchaTo(String phoneNumber, Captcha captcha);

	/**
	 * 请求验证码的冷却时间（分）。
	 * 
	 * @return
	 */
	int getCaptchaRequestCooldown();
}
