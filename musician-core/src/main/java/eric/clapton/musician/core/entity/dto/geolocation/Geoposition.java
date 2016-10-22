package eric.clapton.musician.core.entity.dto.geolocation;

import java.math.BigDecimal;

/**
 * 表示地理位置。
 * 
 * @author cheer
 *
 */
public class Geoposition {
	private Geocoordinate coordinate;
	private String address;
	private String detailAddress;

	public Geoposition(BigDecimal latitude, BigDecimal longitude) {
		this(latitude, longitude, null, null);
	}

	public Geoposition(BigDecimal latitude, BigDecimal longitude, String address) {
		this(latitude, longitude, address, null);
	}

	public Geoposition(BigDecimal latitude, BigDecimal longitude, String address, String detailAddress) {
		this(new Geocoordinate(latitude, longitude), address, detailAddress);
	}

	public Geoposition(Geocoordinate coordinate) {
		this(coordinate, null, null);
	}

	public Geoposition(Geocoordinate coordinate, String address) {
		this(coordinate, address, null);
	}

	public Geoposition(Geocoordinate coordinate, String address, String detailAddress) {
		this.coordinate = coordinate;
		this.address = address;
		this.detailAddress = detailAddress;
	}

	public Geoposition() {
		this((Geocoordinate) null, null, null);
	}

	/**
	 * 获取地理位置的经纬度坐标。
	 * 
	 * @return
	 */
	public Geocoordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Geocoordinate coordinate) {
		this.coordinate = coordinate;
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

}
