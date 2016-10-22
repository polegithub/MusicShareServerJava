package eric.clapton.musician.core.entity.po.account;

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

import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.musician.core.entity.po.skill.Skill;

@Entity
@Table(name = "ms_account_skill")
public class AccountSkill extends BaseEntity {
	private static final long serialVersionUID = -6630071092995132420L;

	private Account account;
	private Skill skill;
	private String skillCode;

	public AccountSkill() {
	}

	public AccountSkill(Account account, Skill skill) {
		this.account = account;
		this.skill = skill;
		if (skill != null) {
			this.skillCode = skill.getCode();
		}
	}

	@Override
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.getId();
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", updatable = false)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "skill_id", updatable = false)
	public Skill getSkill() {
		return skill;
	}

	@Transient
	public Long getSkillId() {
		Skill skill = getSkill();
		return skill == null ? null : skill.getId();
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	@Column(name = "skill_code", updatable = false)
	public String getSkillCode() {
		return skillCode;
	}

	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}
}
