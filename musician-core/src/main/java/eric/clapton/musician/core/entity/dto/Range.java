package eric.clapton.musician.core.entity.dto;

import java.io.Serializable;

public class Range<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = -5574483848785931982L;

	private T from;
	private T to;

	public Range() {

	}

	public Range(T from, T to) {
		this.from = from;
		this.to = to;
	}

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getTo() {
		return to;
	}

	public void setTo(T to) {
		this.to = to;
	}
}
