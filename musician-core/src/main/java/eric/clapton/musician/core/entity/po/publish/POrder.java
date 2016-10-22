package eric.clapton.musician.core.entity.po.publish;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.order.OrderState;

@Entity
@Table(name = "ms_merchant_publish")
public class POrder extends BaseEntity {
	private static final long serialVersionUID = -6992193477925440646L;

	private String title;
	private String description;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private String address;
	private String detailAddress;
	private String contact;
	private String telephoneNumbers;
	private String city;
	private Account account;
	private Calendar created;
	private OrderState state;

	private List<PublishTime> times;
	private List<PublishStyle> styles;

	public POrder() {

	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return super.getId();
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "longitude")
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude")
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	@Column(name = "address")
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

	@Column(name = "contact")
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

	@Column(name = "city")
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
	
	@OneToMany(mappedBy = "orderid", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<PublishTime> getTimes() {
		return times;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setTimes(List<PublishTime> times) {
		this.times = times;
	}
	
	@OneToMany(mappedBy = "orderid", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<PublishStyle> getStyles() {
		return styles;
	}

	public void setStyles(List<PublishStyle> styles) {
		this.styles = styles;
	}

	@Column(updatable = false)
	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
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
