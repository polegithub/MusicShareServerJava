package eric.clapton.musician.core.entity.po;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import eric.clapton.infrastructure.entity.po.BaseEntity;
import eric.clapton.infrastructure.entity.po.ModificationAware;

@MappedSuperclass
public abstract class ModificationAwareEntity extends BaseEntity implements ModificationAware {
	private static final long serialVersionUID = 648075818701541609L;

	private Calendar lastModifiedAt;
	private String lastModifiedBy;

	@Column(name = "LAST_MODIFIED_BY")
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Column(name = "LAST_MODIFIED_AT")
	public Calendar getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Calendar lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
}
