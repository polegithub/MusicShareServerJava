package eric.clapton.musician.core.entity.dto;

import java.io.Serializable;

/**
 * 表示封装了一个名为 id ，类型为长整型数字的请求。
 * 
 * @author cheer
 *
 */
public class LongIdRequest implements Serializable {
	private static final long serialVersionUID = -7836181370131868530L;

	private Long id;

	public LongIdRequest() {

	}

	public LongIdRequest(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		Long id = getId();
		return id == null ? null : id.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(LongIdRequest other) {
		if (other == null) {
			return false;
		}

		Long thisId = getId();
		Long thatId = other.getId();
		return thisId == null ? thatId == null : thatId == null ? false : thisId.equals(thatId);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof LongIdRequest ? equals((LongIdRequest) obj) : false;
	}

	public static boolean equals(LongIdRequest left, LongIdRequest right) {
		return left == null ? right == null : left.equals(right);
	}
}
