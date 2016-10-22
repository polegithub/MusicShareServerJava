package eric.clapton.musician.core.entity.dto.geolocation;

import java.io.Serializable;
import java.math.BigDecimal;

import eric.clapton.infrastructure.util.Assure;

public class Geocoordinate implements Serializable {
	private static final long serialVersionUID = -4434315524350393874L;

	private BigDecimal latitude = BigDecimal.ZERO;
	private BigDecimal longitude = BigDecimal.ZERO;

	public Geocoordinate() {

	}

	public Geocoordinate(BigDecimal latitude, BigDecimal longitude) {
		setLatitude(latitude);
		setLongitude(longitude);
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		Assure.isNotNull(latitude, "latitude");

		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		Assure.isNotNull(longitude, "longitude");

		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Geocoordinate)) {
			return false;
		}
		Geocoordinate other = (Geocoordinate) obj;
		if (latitude == null) {
			if (other.latitude != null) {
				return false;
			}
		} else if (!latitude.equals(other.latitude)) {
			return false;
		}
		if (longitude == null) {
			if (other.longitude != null) {
				return false;
			}
		} else if (!longitude.equals(other.longitude)) {
			return false;
		}
		return true;
	}
}
