package eric.clapton.musician.repository.tweet;

import eric.clapton.infrastructure.data.jpa.repository.BaseRepository;
import eric.clapton.musician.core.entity.po.tweet.Tweet;

public interface TweetRepository extends BaseRepository<Tweet, Long> {

}
