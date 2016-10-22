package eric.clapton.musician.core.entity.po.account;

import java.util.Calendar;

import javax.persistence.PrePersist;

public class AccountListener {
	@PrePersist
	public void prePersist(Object o) {
		if (o != null && o instanceof Account) {
			Account account = (Account) o;

			account.setCreated(Calendar.getInstance());
		}
	}
}
