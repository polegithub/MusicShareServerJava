package eric.clapton.musician.core.entity.dto.performance;

import eric.clapton.musician.core.entity.dto.BaseSearchParams;
import eric.clapton.musician.core.entity.po.account.AccountType;

public class PublishedPerformancesSearchParams extends BaseSearchParams {
	private static final long serialVersionUID = -6825187874658860178L;

	private AccountType accountType;
	private Long accountId;

	public PublishedPerformancesSearchParams() {

	}

	public PublishedPerformancesSearchParams(Long accountId) {
		this.accountId = accountId;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}
