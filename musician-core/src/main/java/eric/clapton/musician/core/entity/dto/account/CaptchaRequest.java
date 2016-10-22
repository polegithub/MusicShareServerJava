package eric.clapton.musician.core.entity.dto.account;

import java.io.Serializable;

public class CaptchaRequest implements Serializable {
	private static final long serialVersionUID = -7692651531360394989L;

	private String phoneNumber;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
