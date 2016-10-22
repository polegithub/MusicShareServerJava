package eric.clapton.musician.core.entity.dto.skill;

import java.io.Serializable;

public class SkillInfo implements Serializable {
	private static final long serialVersionUID = 5732578050449306501L;
	private Long id;
	private String name;

	public SkillInfo() {

	}

	public SkillInfo(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
