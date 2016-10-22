package eric.clapton.musician.core.entity.dto.tweet;

import java.io.Serializable;

public class MentionSuggestionRequest implements Serializable {
    private static final long serialVersionUID = -8532898909293769896L;

    private String at;

    public MentionSuggestionRequest() {

    }

    public MentionSuggestionRequest(String at) {
        this.at = at;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }
}
