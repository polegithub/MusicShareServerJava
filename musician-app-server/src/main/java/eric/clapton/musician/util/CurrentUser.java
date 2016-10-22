package eric.clapton.musician.util;

import org.apache.shiro.SecurityUtils;

import eric.clapton.musician.core.entity.dto.account.AccountInfo;
import eric.clapton.musician.core.entity.po.account.Account;

public class CurrentUser {
	// private static final Logger logger =
	// LoggerFactory.getLogger(CurrentUser.class);

	protected CurrentUser() {

	}

	public static final Long getAccountId() {
		return ShiroSubjectUtils.getAccountId(SecurityUtils.getSubject());
	}

	public static final Account getAccount() {
		return ShiroSubjectUtils.getAccount(SecurityUtils.getSubject());
	}

	public static final AccountInfo getAccountInfo() {
		return ShiroSubjectUtils.getAccountInfo(SecurityUtils.getSubject());
	}
}
