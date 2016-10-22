package eric.clapton.musician.service.account;

import java.util.List;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.dto.account.AccountInfo;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.account.AccountType;

public interface AccountService extends BaseService<Account> {
	Account findByAccountName(String userName);

	Account findByPhoneNumber(String phoneNumber);

	/**
	 * 使用手机号码登入账户。如果手机号码没有被注册过，则自动注册一个新的账户。
	 * 
	 * @param phoneNumber
	 *            要登入的账户的手机号码。
	 * @param accountType
	 *            账户的类型。
	 * @param captcha
	 *            向该手机号码发送的验证码。
	 * @return 登入的账户。
	 */
	Account loginWithPhoneNumber(String phoneNumber, AccountType accountType, String captcha);

	AccountInfo getAccountInfo(Account account);

	AccountInfo getAccountInfo(Long id);

	Account updateSkills(Long accountId, List<Long> skillIdList);
}
