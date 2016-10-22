package eric.clapton.musician.service.account;

import org.springframework.core.NestedCheckedException;

import eric.clapton.musician.core.entity.po.account.Captcha;

/**
 * 验证码生成请求过于频繁引发的异常。
 * 
 * @author cheer
 *
 */
public class CaptchaRequestTooFrequentExceptionException extends
        NestedCheckedException {
    private static final long serialVersionUID = -110379823008551711L;

    private final Captcha captcha;
    private final int secondsBeforeNextRequest;

    public CaptchaRequestTooFrequentExceptionException(String message,
            int secondsBeforeNextRequest, Captcha captcha) {
        super(message);

        this.captcha = captcha;
        this.secondsBeforeNextRequest = secondsBeforeNextRequest;
    }

    public final Captcha getCaptcha() {
        return captcha;
    }

    /**
     * 距离下一次可以请求验证码所需时间（秒）。
     * 
     * @return
     */
    public final int getSecondsBeforeNextRequest() {
        return secondsBeforeNextRequest;
    }
}
