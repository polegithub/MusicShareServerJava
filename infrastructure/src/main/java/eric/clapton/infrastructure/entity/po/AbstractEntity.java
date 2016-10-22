package eric.clapton.infrastructure.entity.po;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 所有数据库映射实体的基类。
 * 
 * @param <TId>
 *            ID 的类型。
 * @author cheer
 */
@SuppressWarnings("serial")
public abstract class AbstractEntity<TId extends Serializable> implements Persistable<TId> {
	@Override
	public abstract TId getId();

	public abstract void setId(final TId id);

	@Override
	@JsonIgnore
	public boolean isNew() {
		return getId() == null;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		AbstractEntity<?> that = (AbstractEntity<?>) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;

		return hashCode;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
