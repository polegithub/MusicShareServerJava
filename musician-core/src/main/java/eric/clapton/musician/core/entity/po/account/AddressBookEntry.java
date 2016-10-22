package eric.clapton.musician.core.entity.po.account;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

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
import javax.persistence.Transient;

import com.google.common.base.Splitter;

import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.infrastructure.util.StringUtils;

@Entity
@Table(name = "ms_address_book")
public class AddressBookEntry extends BaseEntity {
	private static final long serialVersionUID = 4829100316682137758L;

	private String name;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private String address;
	private String detailAddress;
	private String city;
	private String contact;
	private String telephoneNumbers;
	private String remarks;
	private Calendar created;
	private int displayOrder;
	private Account owner;

	@Override
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.getId();
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	@Transient
	public String[] getTelephoneNumbersAsArray() {
		String telephoneNumbers = getTelephoneNumbers();
		if (telephoneNumbers == null) {
			return new String[] {};
		}

		List<String> resultList = Splitter.on(',').omitEmptyStrings().trimResults().splitToList(telephoneNumbers);
		return resultList.toArray(new String[resultList.size()]);
	}

	public void setTelephoneNumbersAsArray(String[] telephoneNumbers) {
		if (telephoneNumbers == null) {
			this.telephoneNumbers = "";
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < telephoneNumbers.length; i++) {
				String n = StringUtils.makeSafe(telephoneNumbers[i], true);
				if (StringUtils.isNullOrEmpty(n)) {
					continue;
				}
				if (n.indexOf(',') >= 0) {
					throw new IllegalArgumentException("电话号码中不能包含逗号。");
				}
				if (sb.length() > 0) {
					sb.append(',');
				}
				sb.append(n);
			}
			this.telephoneNumbers = sb.toString();
		}
	}

	public void setTelephoneNumbers(String telephoneNumbers) {
		this.telephoneNumbers = telephoneNumbers;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "created")
	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	@Column(name = "display_order")
	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", updatable = false)
	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}
}
