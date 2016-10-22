package eric.clapton.infrastructure.entity.po;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class SoftDeletionSupportedEntity extends BaseEntity implements HasDeletionFlag {
    private boolean deleted;

    @Transient
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
