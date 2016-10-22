package eric.clapton.musician.core.entity.dto.tweet;

import java.io.Serializable;
import java.util.List;

import eric.clapton.musician.core.entity.po.tweet.TweetType;

public class TweetListItem implements Serializable {
	private static final long serialVersionUID = -509881306861891338L;

	private Long id;
	private boolean isPrivate;
	private TweetType type;
	private List<String> photos;
	private String text;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public TweetType getType() {
		return type;
	}

	public void setType(TweetType type) {
		this.type = type;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
