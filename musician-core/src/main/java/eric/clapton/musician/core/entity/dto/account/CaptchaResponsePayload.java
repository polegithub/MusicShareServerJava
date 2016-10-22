package eric.clapton.musician.core.entity.dto.account;

import java.io.Serializable;

public class CaptchaResponsePayload implements Serializable {
	private static final long serialVersionUID = 5954473606133925540L;

	private int cooldown;
	private int type;

	public CaptchaResponsePayload() {

	}

	public CaptchaResponsePayload(int type, int cooldown) {
		this.type = type;
		this.cooldown = cooldown;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}