package eric.clapton.musician.core.entity.po.account;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eric.clapton.infrastructure.entity.po.BaseEntity;

@Entity
@Table(name = "ms_account_follow")
public class AccountFollow extends BaseEntity {
	private static final long serialVersionUID = -5385006901017974051L;
	private Account account;
	private Account followAccount;
	private Calendar followed;

	@Override
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.getId();
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", updatable = false)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "follow_account_id", updatable = false)
	public Account getFollowAccount() {
		return followAccount;
	}

	public void setFollowAccount(Account followAccount) {
		this.followAccount = followAccount;
	}

	@Column(name = "followed")
	public Calendar getFollowed() {
		return followed;
	}

	public void setFollowed(Calendar followed) {
		this.followed = followed;
	}
}
