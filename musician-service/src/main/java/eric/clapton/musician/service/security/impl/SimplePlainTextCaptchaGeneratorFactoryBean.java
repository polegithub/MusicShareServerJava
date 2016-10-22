package eric.clapton.musician.service.security.impl;

import org.springframework.beans.factory.FactoryBean;

import eric.clapton.infrastructure.util.StringUtils;

public class SimplePlainTextCaptchaGeneratorFactoryBean implements FactoryBean<SimplePlainTextCaptchaGenerator> {
	private int maxCaptchaTextLength = 4;
	private int minCaptchaTextLength = 4;
	private char[] captchaCharacters = "0123456789".toCharArray();
	private String charset;

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
	public SimplePlainTextCaptchaGenerator getObject() throws Exception {
		int maxCaptchaTextLength = getMaxCaptchaTextLength();
		if (maxCaptchaTextLength <= 0) {
			throw new IllegalArgumentException("属性 MaxCaptchaTextLength 不能是负数。");
		}
		int minCaptchaTextLength = getMinCaptchaTextLength();
		if (minCaptchaTextLength <= 0) {
			throw new IllegalArgumentException("属性 MinCaptchaTextLength 不能是负数。");
		}
		if (maxCaptchaTextLength < minCaptchaTextLength) {
			throw new IllegalArgumentException("属性 MaxCaptchaTextLength 的值不能小于属性 MinCaptchaTextLength 的值。");
		}
		captchaCharacters = getCaptchaCharacters();
		if (captchaCharacters == null || captchaCharacters.length == 0) {
			throw new IllegalArgumentException("属性 CaptchaCharacters 的值不能为 null 或空数组。");
		}
		String charset = getCharset();
		if (StringUtils.isNullOrEmpty(charset)) {
			charset = "UTF-8";
		}

		SimplePlainTextCaptchaGenerator generator = new SimplePlainTextCaptchaGenerator();
		generator.setCaptchaCharacters(captchaCharacters);
		generator.setCharset(charset);
		generator.setMaxCaptchaTextLength(maxCaptchaTextLength);
		generator.setMinCaptchaTextLength(minCaptchaTextLength);

		return generator;
	}

	@Override
	public Class<?> getObjectType() {
		return SimplePlainTextCaptchaGenerator.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
