package eric.clapton.musician.test.service;

import javax.annotation.Resource;

import org.junit.Test;

import eric.clapton.musician.service.security.CaptchaGenerationException;
import eric.clapton.musician.service.security.CaptchaGenerator;
import eric.clapton.musician.test.SpringTestCase;

public class CaptchaGeneratorTest extends SpringTestCase {
	@Resource
	private CaptchaGenerator captchaGenerator;

	@Test
	public void testGenerateCaptcha() throws CaptchaGenerationException {
		byte[] captchaBytes = captchaGenerator.generate();
		String captcha = new String(captchaBytes);
		System.out.println(captcha);
	}

}
