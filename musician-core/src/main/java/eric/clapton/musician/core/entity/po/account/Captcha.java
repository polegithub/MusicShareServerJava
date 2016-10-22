package eric.clapton.musician.core.entity.po.account;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import eric.clapton.infrastructure.entity.po.BaseEntity;

@Entity
@Table(name = "ms_captcha")
public class Captcha extends BaseEntity {
	private static final long serialVersionUID = 7907540175117413876L;

	private String captcha;
	private String phoneNumber;
	private Calendar created;
	private Calendar expires;
	private CaptchaState state;

	public Captcha() {
		this.state = CaptchaState.CREATED;
	}

	@Override
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.getId();
	}

	@Column(name = "captcha", updatable = false)
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	@Column(name = "phone_number", updatable = false)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "created", updatable = false)
	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	@Column(name = "expires")
	public Calendar getExpires() {
		return expires;
	}

	public void setExpires(Calendar expires) {
		this.expires = expires;
	}

	@Column(name = "state")
	@Enumerated
	public CaptchaState getState() {
		return state;
	}

	public void setState(CaptchaState state) {
		this.state = state;
	}
}
