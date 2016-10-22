package eric.clapton.musician.core.entity.dto.tweet;

import eric.clapton.musician.core.entity.dto.BasePageRequest;

public class TweetListRequest extends BasePageRequest {
	private static final long serialVersionUID = 3627401731588273121L;

	private boolean myTweetsOnly;

	public boolean isMyTweetsOnly() {
		return myTweetsOnly;
	}

	public void setMyTweetsOnly(boolean myTweetsOnly) {
		this.myTweetsOnly = myTweetsOnly;
	}
}
