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
        if (principal == null) {
            throw new IllegalStateException(
                    "There is no principal associated with the subject. Have you logged in?");
        }

        if (!(principal instanceof Long)) {
            String error = String
                    .format(Locale.ENGLISH,
                            "The type of the principal expected to be '%s', however, '%s' got.",
                            Long.class.getName(), principal.getClass()
                                    .getName());
            throw new IllegalStateException(error);
        }

        return (Long) principal;
    }

    public static final AccountInfo getAccountInfo(final Subject subject) {
        Long accountId = getAccountId(subject);

        AccountInfo account = accountService.getAccountInfo(accountId);
        if (account == null) {
            throw new IllegalStateException(
                    String.format(
                            Locale.ROOT,
                            "Unable to retrieve account information with identifier: %d.",
                            accountId));
        }

        return account;
    }

    public static final Account getAccount(final Subject subject) {
        Long accountId = getAccountId(subject);

        Account account = accountService.findOne(accountId);
        if (account == null) {
            throw new IllegalStateException(String.format(Locale.ROOT,
                    "There is not account with identifier: %d.", accountId));
        }

        return account;
    }
}
