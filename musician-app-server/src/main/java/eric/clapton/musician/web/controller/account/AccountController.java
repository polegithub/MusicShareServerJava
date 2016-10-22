package eric.clapton.musician.web.controller.account;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eric.clapton.infrastructure.util.StringUtils;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.account.AccountInfo;
import eric.clapton.musician.core.entity.dto.account.CaptchaRequest;
import eric.clapton.musician.core.entity.dto.account.CaptchaResponsePayload;
import eric.clapton.musician.core.entity.po.account.Captcha;
import eric.clapton.musician.service.account.CaptchaRequestTooFrequentExceptionException;
import eric.clapton.musician.service.account.CaptchaService;
import eric.clapton.musician.service.security.CaptchaGenerator;
import eric.clapton.musician.util.CurrentUser;
import eric.clapton.musician.web.controller.ApiControllerSupport;

@Controller
@RequestMapping("/account")
@ResponseBody
public class AccountController extends ApiControllerSupport {
	@Resource
	private CaptchaService captchaService;
	@Resource
	private CaptchaGenerator captchaGenerator;

	/**
	 * 生成验证码。
	 * 
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping("/captcha")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public ApiResponse generateCaptcha(@RequestBody CaptchaRequest r) {
		final String phoneNumber = r.getPhoneNumber();

		if (StringUtils.isNullOrEmpty(phoneNumber)) {
			return ApiResponse.fail("isv.missing_param", "Request parameter 'phoneNumber' cannot be null or empty.");
		}

		logger.debug("开始为  {} 生成手机验证码。", phoneNumber);

		// Generate a new captcha
		Captcha captcha;
		try {
			captcha = captchaService.requestCaptchaFor(phoneNumber, captchaGenerator);
		} catch (CaptchaRequestTooFrequentExceptionException e) {
			int cooldown = e.getSecondsBeforeNextRequest();

			return ApiResponse.succeed(new CaptchaResponsePayload(1, cooldown));
		}

		String captchaText = captcha.getCaptcha();

		logger.debug("已为 {} 生成手机验证码 {}。准备将其发送到用户手机。", phoneNumber, captchaText);

		captchaService.sendCaptchaTo(phoneNumber, captcha);

		logger.debug("已将验证码 {} 发送到手机 {}。", captchaText, phoneNumber);

		int cooldown = captchaService.getCaptchaRequestCooldown() * 60;
		return ApiResponse.succeed(new CaptchaResponsePayload(1, cooldown));
	}

	@RequestMapping("/brief")
	public ApiResponse getAccountInfo() {
		AccountInfo accountInfo = CurrentUser.getAccountInfo();
		return ApiResponse.succeed(accountInfo);
	}

}
