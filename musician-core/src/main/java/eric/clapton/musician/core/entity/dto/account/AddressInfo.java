package eric.clapton.musician.core.entity.dto.account;

import java.io.Serializable;

/**
 * 包含地址信息。
 * 
 * @author cheer
 *
 */
public class AddressInfo implements Serializable {
	private static final long serialVersionUID = 772450268807069389L;

	private String country;
	private String province;
	private String city;
	private String district;
	private String street;

	public AddressInfo() {
		this(null, null, null, null);
	}

	public AddressInfo(String country, String province) {
		this(country, province, null, null);
	}

	public AddressInfo(String country, String province, String city) {
		this(country, province, city, null);
	}

	public AddressInfo(String country, String province, String city, String district) {
		this(country, province, city, district, null);
	}

	public AddressInfo(String country, String province, String city, String district, String street) {
		this.country = country;
		this.province = province;
		this.city = city;
		this.district = district;
		this.street = street;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [");
		if (country != null) {
			builder.append("country=");
			builder.append(country);
			builder.append(", ");
		}
		if (province != null) {
			builder.append("province=");
			builder.append(province);
			builder.append(", ");
		}
		if (city != null) {
			builder.append("city=");
			builder.append(city);
			builder.append(", ");
		}
		if (district != null) {
			builder.append("district=");
			builder.append(district);
		}
		builder.append("]");
		return builder.toString();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
