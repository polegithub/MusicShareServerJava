package eric.clapton.musician.service.tweet;

import org.springframework.data.domain.Page;

import eric.clapton.infrastructure.service.BaseService;
import eric.clapton.musician.core.entity.dto.tweet.TweetListRequest;
import eric.clapton.musician.core.entity.dto.tweet.TweetPublishRequest;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.tweet.Tweet;

public interface TweetService extends BaseService<Tweet> {
	Tweet publish(Account account, TweetPublishRequest r);

	Page<Tweet> listTweets(Account account, TweetListRequest r);
}
