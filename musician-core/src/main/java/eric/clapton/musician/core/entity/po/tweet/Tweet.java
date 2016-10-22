package eric.clapton.musician.core.entity.po.tweet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.musician.core.entity.po.account.Account;

@Entity
@Table(name = "ms_tweet")
public class Tweet extends BaseEntity {
	private static final long serialVersionUID = 1730886791082236880L;

	private Account account;
	private TweetType type;
	private TweetVisibility visibility;
	private String text;
	private String photos;
	private Calendar published;
	private Calendar privatized;

	private List<TweetAccountVisibility> accountVisibilities = new ArrayList<TweetAccountVisibility>();
	private List<TweetMention> mentions = new ArrayList<TweetMention>();

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

	@Enumerated
	@Column(name = "type")
	public TweetType getType() {
		return type;
	}

	public void setType(TweetType type) {
		this.type = type;
	}

	@Enumerated
	@Column(name = "visibility")
	public TweetVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(TweetVisibility visibility) {
		this.visibility = visibility;
	}

	@Column(name = "text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "photos")
	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	@Column(name = "published")
	public Calendar getPublished() {
		return published;
	}

	public void setPublished(Calendar published) {
		this.published = published;
	}

	@Column(name = "privatized")
	public Calendar getPrivatized() {
		return privatized;
	}

	public void setPrivatized(Calendar privatized) {
		this.privatized = privatized;
	}

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "tweet")
	@OrderBy("account")
	public List<TweetAccountVisibility> getAccountVisibilities() {
		return accountVisibilities;
	}

	public void setAccountVisibilities(List<TweetAccountVisibility> accountVisibilities) {
		this.accountVisibilities = accountVisibilities;
	}

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "tweet")
	@OrderBy("mentions")
	public List<TweetMention> getMentions() {
		return mentions;
	}

	public void setMentions(List<TweetMention> mentions) {
		this.mentions = mentions;
	}
}
