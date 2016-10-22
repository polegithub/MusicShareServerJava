package eric.clapton.musician.core.entity.dto.tweet;

import java.io.Serializable;
import java.util.List;

import eric.clapton.musician.core.entity.po.tweet.TweetType;

public class TweetPublishRequest implements Serializable {
    private static final long serialVersionUID = 3774502650582159423L;

    private TweetType type;
    private List<String> photos;
    private String text;
    private List<Long> visibilities;
    private List<Long> mentions;
    private boolean isPublic;

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

    public List<Long> getVisibilities() {
        return visibilities;
    }

    public void setVisibility(List<Long> visibilities) {
        this.visibilities = visibilities;
    }

    public List<Long> getMentions() {
        return mentions;
    }

    public void setMentions(List<Long> mentions) {
        this.mentions = mentions;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
