package eric.clapton.musician.service.account.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Charsets;

import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.infrastructure.service.ServiceException;
import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.infrastructure.util.Assure;
import eric.clapton.infrastructure.util.Enumerable;
import eric.clapton.musician.core.entity.po.account.Captcha;
import eric.clapton.musician.core.entity.po.account.CaptchaState;
import eric.clapton.musician.repository.account.CaptchaRepository;
import eric.clapton.musician.service.account.CaptchaRequestTooFrequentExceptionException;
import eric.clapton.musician.service.account.CaptchaService;
import eric.clapton.musician.service.security.CaptchaGenerationException;
import eric.clapton.musician.service.security.CaptchaGenerator;
import eric.clapton.musician.service.sms.provider.yuntongxun.YuntongxunException;
import eric.clapton.musician.service.sms.provider.yuntongxun.YuntongxunSmsProvider;

@Service
public class CaptchaServiceImpl extends BaseServiceImpl<Captcha, CaptchaRepository> implements CaptchaService {
	private int captchaExpiresIn = 30;
	private int captchaRequestCooldown = 1;
	@Resource
	private YuntongxunSmsProvider smsProvider;

	@Override
	public int getCaptchaExpiresIn() {
		return captchaExpiresIn;
	}

	public void setCaptchaExpiresIn(int captchaExpiresIn) {
		this.captchaExpiresIn = captchaExpiresIn;
	}

	@Override
	public int getCaptchaRequestCooldown() {
		return captchaRequestCooldown;
	}

	public void setCaptchaRequestCooldown(int captchaRequestCooldown) {
		this.captchaRequestCooldown = captchaRequestCooldown;
	}

	@Override
	@Resource
	protected void baseSetRepository(CaptchaRepository repository) {
		setRepository(repository);
	}

	@Override
	@Transactional(noRollbackFor = { CaptchaRequestTooFrequentExceptionException.class })
	public Captcha requestCaptchaFor(String phoneNumber, CaptchaGenerator generator)
			throws CaptchaRequestTooFrequentExceptionException {
		Assure.isNotEmpty(phoneNumber, "phoneNumber");

		Calendar now = Calendar.getInstance();

		// 查找冷却时间以内为该手机号生成的验证码。
		int cd = getCaptchaRequestCooldown();
		Calendar c = (Calendar) now.clone();
		c.add(Calendar.MINUTE, -cd);
		List<Captcha> captchaList = getRepository().findByPhoneNumberAndCreatedGreaterThanOrEqual(phoneNumber, c);
		if (!captchaList.isEmpty()) {
			Captcha captcha = captchaList.get(0);
			int secondsBeforeNextRequest = (int) (cd * 60
					- (now.getTimeInMillis() - captcha.getCreated().getTimeInMillis()) / 1000);

			// 如果有，则返回该验证码。
			throw new CaptchaRequestTooFrequentExceptionException(
					String.format(Locale.CHINA, "您请求验证码的操作过于频繁，请 %d 秒后再试。", secondsBeforeNextRequest),
					secondsBeforeNextRequest, captcha);
		}

		// 没有？创建新的验证码。

		String captchaText = generateTextCaptcha(generator);// 验证码内容

		Calendar expires = (Calendar) now.clone();
		expires.add(Calendar.MINUTE, getCaptchaExpiresIn()); // 过期时间

		Captcha captcha = new Captcha();
		captcha.setCaptcha(captchaText);
		captcha.setPhoneNumber(phoneNumber);
		captcha.setCreated(now);
		captcha.setExpires(expires);

		return save(captcha);
	}

	private String generateTextCaptcha(CaptchaGenerator generator) {
		String contentType = generator.getContentType();
		if (!CaptchaGenerator.CONTENT_TYPE_TEXT.equals(contentType)) {
			throw new IllegalStateException(
					String.format(Locale.CHINA,
							"An illegal argument was passed to the method: "
									+ " The content type of the captcha generator('%s') is not supported.",
							contentType));
		}

		try {
			return new String(generator.generate(), Charsets.UTF_8);
		} catch (CaptchaGenerationException e) {
			throw new ServiceException(0x1001_0001, "服务器错误。", e);
		}
	}

	@Override
	public void sendCaptchaTo(String phoneNumber, Captcha captcha) {
		Assure.isNotEmpty(phoneNumber, "phoneNumber");
		Assure.isNotNull(captcha, "captcha");

		List<String> phoneNumbers = new ArrayList<String>(1);
		phoneNumbers.add(phoneNumber);

		List<String> args = new ArrayList<String>(2);
		args.add(captcha.getCaptcha());
		args.add(getCaptchaExpiresIn() + "");

		try {
			smsProvider.sendMessage(phoneNumbers, "1", args);
		} catch (YuntongxunException e) {
			logger.error("验证码发送失败。", e);

			throw new ServiceException(0x1001_0002, "服务器异常，验证码发送失败。", e);
		}
	}

	@Override
	public Captcha getCaptchaFor(String phoneNumber) {
		Assure.isNotEmpty(phoneNumber, "phoneNumber");

		Calendar now = Calendar.getInstance();

		Searchable searchable = Searchable.newSearchable();
		searchable.equal("phoneNumber", phoneNumber).greaterThan("expires", now).equal("state", CaptchaState.CREATED);
		searchable.orderByDescending("created");

		return Enumerable.firstOrDefault(findAllWithSort(searchable));
	}
}
