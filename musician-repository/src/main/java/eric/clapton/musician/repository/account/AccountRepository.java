package eric.clapton.musician.repository.account;

import org.springframework.data.jpa.repository.Query;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.account.Account;

public interface AccountRepository extends BaseRepository<Account, Long> {
	@Query("select u from Account u where u.userName = ?1")
	Account findByAccountName(String userName);

	@Query("select a from Account a where a.phoneNumber = ?1")
	Account findByPhoneNumber(String phoneNumber);
}
