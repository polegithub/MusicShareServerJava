package eric.clapton.musician.web.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;

import eric.clapton.musician.core.entity.po.account.AccountType;

public class MusicianAppToken implements HostAuthenticationToken {
	private static final long serialVersionUID = 491461516659442288L;

	private String accountName;
	private String captcha;
	private AccountType accountType;
	private String host;

	public MusicianAppToken() {
	}

	public MusicianAppToken(final String accountName, final String captcha) {
		this(accountName, captcha, null, null);
	}

	public MusicianAppToken(final String accountName, final String captcha, final AccountType accountType) {
		this(accountName, captcha, accountType, null);
	}

	public MusicianAppToken(final String accountName, final String captcha, final AccountType accountType,
			final String host) {
		this.accountName = accountName;
		this.captcha = captcha;
		this.host = host;
		this.accountType = accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	@Override
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Object getPrincipal() {
		return getAccountName();
	}

	public Object getCredentials() {
		return getCaptcha();
	}

	public void clear() {
		this.accountName = null;
		this.captcha = null;
		this.host = null;
		this.accountType = null;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append('[').append(getClass().getName()).append(']');
		out.append(" 帐号");
		String accountName = getAccountName();
		if (accountName == null || accountName.isEmpty()) {
			out.append("未指定");
		} else {
			out.append(": ").append(accountName);
		}

		return out.toString();
	}
}
