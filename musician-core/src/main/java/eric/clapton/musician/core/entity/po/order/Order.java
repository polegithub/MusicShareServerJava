package eric.clapton.musician.core.entity.po.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
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
import javax.persistence.Transient;

import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.musician.core.entity.po.account.Account;

@Entity
@Table(name = "ms_order")
@EntityListeners({ OrderEntityListener.class })
public class Order extends BaseEntity {
	private static final long serialVersionUID = -6992193477925440646L;

	private String title;
	private String description;
	private Calendar dateFrom;
	private Calendar dateTo;
	private Calendar deadline;
	private int recruits;
	private BigDecimal price;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private String address;
	private String detailAddress;
	private String contact;
	private String telephoneNumbers;
	private String city;
	private Account account;
	private Calendar created;
	private int recruited;
	private int signedUp;
	private OrderState state;

	private List<OrderGenre> genres;

	public Order() {
		state = OrderState.NORMAL;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return super.getId();
	}

	@Column()
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column()
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "date_from")
	public Calendar getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Calendar dateFrom) {
		this.dateFrom = dateFrom;
	}

	@Column(name = "date_to")
	public Calendar getDateTo() {
		return dateTo;
	}

	public void setDateTo(Calendar dateTo) {
		this.dateTo = dateTo;
	}

	@Column
	public Calendar getDeadline() {
		return deadline;
	}

	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}

	@Column
	public int getRecruits() {
		return recruits;
	}

	public void setRecruits(int recruits) {
		this.recruits = recruits;
	}

	@Column
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Column
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	@Column
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "detail_address")
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	@Column
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "tel_nrs")
	public String getTelephoneNumbers() {
		return telephoneNumbers;
	}

	public void setTelephoneNumbers(String telephoneNumbers) {
		this.telephoneNumbers = telephoneNumbers;
	}

	@Column()
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@JoinColumn(name = "account_id", updatable = false)
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
	public Account getAccount() {
		return account;
	}

	@Transient
	public Long getAccountId() {
		Account account = getAccount();
		return account == null ? null : account.getId();
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "order")
	@OrderBy("id")
	public List<OrderGenre> getGenres() {
		return genres;
	}

	public void setGenres(List<OrderGenre> genres) {
		this.genres = genres;
	}

	@Column(updatable = false)
	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	@Column(name = "recruited")
	public int getRecruited() {
		return recruited;
	}

	public void setRecruited(int recruited) {
		this.recruited = recruited;
	}

	@Column(name = "signed_up")
	public int getSignedUp() {
		return signedUp;
	}

	public void setSignedUp(int signedUp) {
		this.signedUp = signedUp;
	}

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}
}
