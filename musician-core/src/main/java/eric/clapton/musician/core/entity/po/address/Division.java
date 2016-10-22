package eric.clapton.musician.core.entity.po.address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.base.Splitter;

import eric.clapton.infrastructure.entity.po.BaseEntity;

@Entity
@Table(name = "ms_division")
public class Division extends BaseEntity {
	private static final long serialVersionUID = -4988875107310010365L;

	private int displayOrder;
	private int level;
	private String name;
	private String pinyin;
	private String code;
	private String postalCode;
	private String path;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return super.getId();
	}

	@Column(name = "display_order")
	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Column(name = "level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pinyin")
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Column(name = "code", updatable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "postal_code")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "path", updatable = false)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static final String PATH_SPLITTER_STRING = "/";

	private static final Splitter PATH_SPLITTER = Splitter.on(PATH_SPLITTER_STRING).omitEmptyStrings().trimResults();

	@Transient
	public long[] getPathAsArray() {
		String path = getPath();
		if (path == null || path.isEmpty()) {
			return new long[0];
		}

		return PATH_SPLITTER.splitToList(path).stream().mapToLong(e -> Long.valueOf(e)).toArray();
	}
}
