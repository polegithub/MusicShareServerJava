package eric.clapton.musician.util;

import java.util.Locale;

import javax.annotation.Resource;

import org.apache.shiro.subject.Subject;

import eric.clapton.infrastructure.util.Assure;
import eric.clapton.musician.core.entity.dto.account.AccountInfo;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.service.account.AccountService;

public class ShiroSubjectUtils {
	private static AccountService accountService;

	@Resource
	protected void setAccountService(AccountService accountService) {
		ShiroSubjectUtils.accountService = accountService;
	}

	public static final Long getAccountId(final Subject subject) {
		Assure.isNotNull(subject, "subject");
		Object principal = subject.getPrincipal();
		if (!(principal instanceof Long)) {
			throw new IllegalStateException("Principal expected to be an instance of java.lang.Long.");
		}

		return (Long) principal;
	}

	public static final AccountInfo getAccountInfo(final Subject subject) {
		Long accountId = getAccountId(subject);

		AccountInfo account = accountService.getAccountInfo(accountId);
		if (account == null) {
			throw new IllegalStateException(String.format(Locale.ROOT,
					"Unable to retrieve account information with identifier: %d.", accountId));
		}

		return account;
	}

	public static final Account getAccount(final Subject subject) {
		Long accountId = getAccountId(subject);

		Account account = accountService.findOne(accountId);
		if (account == null) {
			throw new IllegalStateException(
					String.format(Locale.ROOT, "There is not account with identifier: %d.", accountId));
		}

		return account;
	}
}
