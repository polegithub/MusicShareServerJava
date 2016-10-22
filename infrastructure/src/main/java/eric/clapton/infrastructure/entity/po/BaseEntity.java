package eric.clapton.infrastructure.entity.po;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 
 * 
 * @author xuw
 */
@SuppressWarnings("serial")
@MappedSuperclass
// @EntityListeners(value = { DefaultEntityListener.class })
public abstract class BaseEntity extends AbstractEntity<Long> {
	private Long id;

	@Transient
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
