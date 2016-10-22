package eric.clapton.musician.core.entity.po.tweet;

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
import eric.clapton.musician.core.entity.po.account.Account;

@Entity
@Table(name = "ms_tweet_mention")
public class TweetMention extends BaseEntity {
	private static final long serialVersionUID = -1097545174099985205L;

	private Tweet tweet;
	private Account account;

	public TweetMention() {

	}

	public TweetMention(Tweet tweet, Account account) {
		this.tweet = tweet;
		this.account = account;
	}

	@Override
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.getId();
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "tweet_id", updatable = false)
	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", updatable = false)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
