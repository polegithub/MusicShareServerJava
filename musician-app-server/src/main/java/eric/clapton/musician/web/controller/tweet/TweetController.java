package eric.clapton.musician.web.controller.tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Joiner;

import eric.clapton.infrastructure.data.jpa.search.SearchOperator;
import eric.clapton.infrastructure.data.jpa.search.Searchable;
import eric.clapton.infrastructure.data.jpa.search.filter.SearchFilterHelper;
import eric.clapton.infrastructure.util.Enumerable;
import eric.clapton.infrastructure.util.StringUtils;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.LongIdRequest;
import eric.clapton.musician.core.entity.dto.tweet.Mention;
import eric.clapton.musician.core.entity.dto.tweet.MentionSuggestionRequest;
import eric.clapton.musician.core.entity.dto.tweet.TweetListRequest;
import eric.clapton.musician.core.entity.dto.tweet.TweetPublishRequest;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.account.AccountState;
import eric.clapton.musician.core.entity.po.tweet.Tweet;
import eric.clapton.musician.core.entity.po.tweet.TweetType;
import eric.clapton.musician.service.account.AccountService;
import eric.clapton.musician.service.tweet.TweetService;
import eric.clapton.musician.util.CurrentUser;
import eric.clapton.musician.web.controller.ApiControllerSupport;

@Controller
@RequestMapping("/tweet")
@ResponseBody
public class TweetController extends ApiControllerSupport {
	@Resource
	private TweetService tweetService;
	@Resource
	private AccountService accountService;

	/**
	 * 获取推文列表。
	 * 
	 * @return
	 */
	public ApiResponse list(@RequestBody() TweetListRequest r) {
		tweetService.listTweets(CurrentUser.getAccount(), r);
		return null;
	}

	/**
	 * 删除我的推文。
	 * 
	 * @param r
	 * @return
	 */
	@RequestMapping("/destroy")
	public ApiResponse destroy(@RequestBody() LongIdRequest r) {
		Long id = r.getId();
		if (id == null) {
			return parameterMissing("id");
		}
		Tweet tweet = tweetService.findOne(r.getId());
		if (tweet != null && tweet.getAccount().getId().equals(CurrentUser.getAccountId())) {
			tweet = null;
		}
		if (tweet == null) {
			return ApiResponse.fail("tweet.not_found", "没有找到要删除的推文。");
		}

		tweetService.delete(tweet);

		return ApiResponse.succeed();
	}

	/**
	 * 发布推文。
	 */
	@RequestMapping("/publish")
	public ApiResponse publish(@RequestBody() TweetPublishRequest r) {
		TweetType type = r.getType();
		if (type == null) {
			return parameterMissing("type");
		}
		switch (type) {
		case PHOTOS:
			if (Enumerable.isNullOrEmpty(r.getPhotos())) {
				return parameterMissing("photos");
			}
			break;
		case TEXT:
			if (StringUtils.isNullOrEmpty(r.getText())) {
				return parameterMissing("text");
			}
		}

		Account account = CurrentUser.getAccount();
		tweetService.publish(account, r);

		return ApiResponse.succeed();
	}

	/**
	 * 提供 at 的建议。
	 * 
	 * @param r
	 * @return
	 */
	@RequestMapping("/mention/suggest")
	public ApiResponse suggestMention(@RequestBody() MentionSuggestionRequest r) {
		String at = r.getAt();
		if (StringUtils.isNullOrEmpty(at)) {
			return parameterMissing("at");
		} // zhou-jie-lun // z%-j%-l%
		char[] chars = at.toCharArray();
		List<Character> pinyinInitials = new ArrayList<Character>(chars.length);
		for (char c : chars) {
			pinyinInitials.add(c);
		}

		Searchable searchable = Searchable.newSearchable();
		searchable.notEqual("id", CurrentUser.getAccountId());
		searchable.equal("state", AccountState.NORMAL);
		searchable.or(SearchFilterHelper.startsWith("userName", at), SearchFilterHelper.startsWith("nickName", at),
				SearchFilterHelper.startsWith("nickNamePinyin2", at), SearchFilterHelper.newCondition("nickNamePinyin",
						SearchOperator.LIKE_WITH_WILDCARD, Joiner.on("%-").join(pinyinInitials) + "%"));

		searchable.setPage(0, 10);
		searchable.disablePageCount();

		List<Mention> mentions = accountService.findAll(searchable).getContent().stream()
				.map(a -> new Mention(a.getId(), a.getUserName(), a.getNickName()))
				.collect(Collectors.<Mention> toList());

		return ApiResponse.succeed(mentions);
	}
}
