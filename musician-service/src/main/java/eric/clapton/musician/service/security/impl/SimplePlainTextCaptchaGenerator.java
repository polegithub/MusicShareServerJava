package eric.clapton.musician.service.security.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import eric.clapton.musician.service.security.CaptchaGenerationException;
import eric.clapton.musician.service.security.CaptchaGenerator;
import eric.clapton.musician.service.security.InvalidCaptchaGeneratorConfigurationException;

/**
 * 实现一个简单的纯文本验证码生成器。
 * 
 * @author cheer
 *
 */
public class SimplePlainTextCaptchaGenerator implements CaptchaGenerator {
	private int maxCaptchaTextLength = 4;
	private int minCaptchaTextLength = 4;
	private char[] captchaCharacters;
	private String charset;

	private Random random;

	public SimplePlainTextCaptchaGenerator() throws NoSuchAlgorithmException {
		// random = SecureRandom.getInstanceStrong();
		random = new Random();
	}

	/**
	 * 总是返回 text/plain。
	 */
	@Override
	public String getContentType() {
		return "text/plain";
	}

	public int getMaxCaptchaTextLength() {
		return maxCaptchaTextLength;
	}

	public void setMaxCaptchaTextLength(int maxCaptchaTextLength) {
		this.maxCaptchaTextLength = maxCaptchaTextLength;
	}

	public int getMinCaptchaTextLength() {
		return minCaptchaTextLength;
	}

	public void setMinCaptchaTextLength(int minCaptchaTextLength) {
		this.minCaptchaTextLength = minCaptchaTextLength;
	}

	public char[] getCaptchaCharacters() {
		return captchaCharacters;
	}

	public void setCaptchaCharacters(char[] captchaCharacters) {
		this.captchaCharacters = captchaCharacters;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public byte[] generate() throws CaptchaGenerationException {
		int captchaTextLength = nextInt(random, getMinCaptchaTextLength(), getMaxCaptchaTextLength());
		StringBuilder output = new StringBuilder();

		char[] characters = getCaptchaCharacters();
		if (characters == null || characters.length == 0) {
			throw new InvalidCaptchaGeneratorConfigurationException("验证码生成器配置不正确。属性 CaptchaCharacters 为 null 或空数组。");
		}

		for (int i = 0; i < captchaTextLength; i++) {
			int nextIndex = random.nextInt(characters.length);
			output.append(characters[nextIndex]);
		}
		try {
			return output.toString().getBytes(getCharset());
		} catch (UnsupportedEncodingException e) {
			throw new CaptchaGenerationException("创建验证码时出现了异常：不支持的字符编码。有关异常的详细信息，请参阅异常的 Cause 属性。", e);
		}
	}

	private static final int nextInt(Random random, int from, int to) {
		return from + random.nextInt(to - from + 1);
	}
}
