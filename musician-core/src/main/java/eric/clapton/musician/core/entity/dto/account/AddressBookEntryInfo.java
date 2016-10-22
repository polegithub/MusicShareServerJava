package eric.clapton.musician.core.entity.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddressBookEntryInfo implements Serializable {
	private static final long serialVersionUID = -8656676380921528320L;
	private Long id;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String address;
	private String detailAddress;
	private String remarks;
	private String name;
	private String city;
	private String contact;
	private String[] telephoneNumbers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String[] getTelephoneNumbers() {
		return telephoneNumbers;
	}

	public void setTelephoneNumbers(String[] telephoneNumbers) {
		this.telephoneNumbers = telephoneNumbers;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

}
