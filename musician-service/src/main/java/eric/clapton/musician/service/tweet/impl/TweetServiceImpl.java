package eric.clapton.musician.service.tweet.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import eric.clapton.infrastructure.service.ServiceException;
import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.infrastructure.util.Assure;
import eric.clapton.infrastructure.util.Enumerable;
import eric.clapton.infrastructure.util.StringUtils;
import eric.clapton.musician.core.entity.dto.tweet.TweetListRequest;
import eric.clapton.musician.core.entity.dto.tweet.TweetPublishRequest;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.account.AccountFollow;
import eric.clapton.musician.core.entity.po.tweet.Tweet;
import eric.clapton.musician.core.entity.po.tweet.TweetAccountVisibility;
import eric.clapton.musician.core.entity.po.tweet.TweetMention;
import eric.clapton.musician.core.entity.po.tweet.TweetType;
import eric.clapton.musician.core.entity.po.tweet.TweetVisibility;
import eric.clapton.musician.core.util.PagingUtils;
import eric.clapton.musician.repository.tweet.TweetRepository;
import eric.clapton.musician.service.account.AccountService;
import eric.clapton.musician.service.tweet.TweetService;

@Service
public class TweetServiceImpl extends BaseServiceImpl<Tweet, TweetRepository> implements TweetService {
	@Resource
	private AccountService accountService;

	@Override
	@Resource
	protected void baseSetRepository(TweetRepository repository) {
		super.setRepository(repository);
	}

	@Override
	@Transactional
	public Tweet publish(Account account, TweetPublishRequest r) {
		Assure.isNotNull(account, "account");
		Assure.isNotNull(r, "r");

		TweetType type = r.getType();
		if (type == null) {
			throw new ServiceException(0x3174aff5, "'type' cannot be null or empty.");
		}
		switch (type) {
		case PHOTOS:
			if (Enumerable.isNullOrEmpty(r.getPhotos())) {
				throw new ServiceException(0x3174aff6, "'photos' cannot be null or empty if type is 'PHOTOS'.");
			}
			break;
		case TEXT:
			if (StringUtils.isNullOrEmpty(r.getText())) {
				throw new ServiceException(0x3174aff7, "'text' cannot be null or empty if type is 'TEXT'.");
			}
		default:
			break;
		}

		Long accountId = account.getId();
		List<Long> visibilities = r.getVisibilities();
		if (visibilities == null) {
			visibilities = new ArrayList<Long>();
		} else {
			visibilities = visibilities.stream().filter(v -> v != null && !v.equals(accountId)).distinct()
					.collect(Collectors.<Long> toList());
		}
		r.setVisibility(visibilities);

		List<Long> mentions = r.getMentions();
		if (mentions == null) {
			mentions = new ArrayList<Long>();
		} else {
			mentions = mentions.stream().filter(m -> m != null).distinct().collect(Collectors.<Long> toList());
		}
		r.setMentions(mentions);

		Tweet tweet = new Tweet();
		tweet.setAccount(account);

		if (type == TweetType.PHOTOS) {
			tweet.setPhotos(JSON.toJSONString(r.getPhotos()));
		}
		tweet.setPublished(Calendar.getInstance());
		tweet.setText(r.getText());
		tweet.setType(type);
		tweet.setVisibility(determineVisibility(r));

		if (!Enumerable.isNullOrEmpty(visibilities)) {
			List<TweetAccountVisibility> accountVisibilities = tweet.getAccountVisibilities();

			for (Long id : visibilities) {
				Account a = accountService.findOne(id);
				if (a == null) {
					throw new ServiceException(0x3174aff8,
							String.format(Locale.CHINA, "No account with identifier '%d' found.", id));
				}

				accountVisibilities.add(new TweetAccountVisibility(tweet, a));
			}
		}

		for (Long id : mentions) {
			Account a = accountService.findOne(id);
			if (a == null) {
				throw new ServiceException(0x3174aff8,
						String.format(Locale.CHINA, "No account with identifier '%d' found.", id));
			}
			tweet.getMentions().add(new TweetMention(tweet, a));
		}

		return save(tweet);
	}

	private TweetVisibility determineVisibility(final TweetPublishRequest r) {
		final List<Long> visibilities = r.getVisibilities();

		if (r.isPublic()) {
			if (Enumerable.isNullOrEmpty(visibilities)) {
				return TweetVisibility.PUBLIC;
			}
			return TweetVisibility.VISIBLE_TO;
		}

		if (Enumerable.isNullOrEmpty(visibilities)) {
			return TweetVisibility.PRIVATE;
		}
		return TweetVisibility.NOT_VISIBLE_TO;
	}

	@Override
	public Page<Tweet> listTweets(Account account, TweetListRequest r) {
		Assure.isNotNull(account, "account");
		Assure.isNotNull(r, "r");

		final Long accountId = account.getId();
		final Pageable pageable = r.getPageable();

		return getRepository().findAll(new Specification<Tweet>() {
			@Override
			public Predicate toPredicate(Root<Tweet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Path<Long> accountIdPath = root.get("account").get("id");

				// 1. 我的推文
				Predicate mine = cb.equal(accountIdPath, accountId);

				// 2. 我关注的人的推文
				// 而且这些推文应该对我可见：
				// a. 完全公开的
				// b. “对部分人可见”列表包含我的
				// c. “对部分人不可见”列表不包含我的

				// 2.1 查找我关注的人的推文：
				// exists (select af from AccountFollow af
				// where af.account.id = ?1
				// and af.followAccount.id = t.account.id
				// )
				final Subquery<AccountFollow> afSubquery = query.subquery(AccountFollow.class);
				final Root<AccountFollow> afRoot = afSubquery.from(AccountFollow.class);
				afSubquery.select(afRoot);
				afSubquery.where(cb.equal(afRoot.get("account").get("id"), accountId), // 我关注的人
						cb.equal(afRoot.get("followAccount").get("id"), accountIdPath) // 的推文
				);

				// 2.2 那些对我可见的推文：
				Path<TweetVisibility> visibilityPath = root.get("visibility");

				// 2.2.1 完全公开的：
				// visibility =
				// eric.clapton.musician.core.entity.po.tweet.TweetVisibility.PUBLIC
				Predicate publicTweets = cb.equal(visibilityPath, TweetVisibility.PUBLIC);

				// 2.2.2 部分可见的：
				// visibility =
				// eric.clapton.musician.core.entity.po.tweet.TweetVisibility.VISIBLE_TO
				// and
				// exists (select tav from TweetAccountVisibility tav
				// where tav.tweet.id = t.id
				// and tav.account.id = ?1
				// )

				Subquery<TweetAccountVisibility> tavSubquery = query.subquery(TweetAccountVisibility.class);
				Root<TweetAccountVisibility> tavRoot = tavSubquery.from(TweetAccountVisibility.class);
				tavSubquery.select(tavRoot);
				tavSubquery.where(cb.equal(tavRoot.get("tweet").get("id"), root.get("id")), // 别人的推文
						cb.equal(tavRoot.get("account").get("id"), accountId) // 推文对我可见
				);

				Predicate tweetsVisibleToMe = cb.and(cb.equal(visibilityPath, TweetVisibility.VISIBLE_TO), // 对部分人可见
						cb.exists(tavSubquery));

				// 2.2.3 部分不可见的：
				// visibility =
				// eric.clapton.musician.core.entity.po.tweet.TweetVisibility.NOT_VISIBLE_TO
				// and
				// not exists (select tav from TweetAccountVisibility tav
				// where tav.tweet.id = t.id
				// and tav.account.id = ?1
				// )
				tavSubquery = query.subquery(TweetAccountVisibility.class);
				tavRoot = tavSubquery.from(TweetAccountVisibility.class);
				tavSubquery.select(tavRoot);
				tavSubquery.where(cb.equal(tavRoot.get("tweet").get("id"), root.get("id")), // 别人的推文
						cb.equal(tavRoot.get("account").get("id"), accountId) // 推文对我不可见
				);
				Predicate tweetsInvisibleToMe = cb.and(cb.equal(visibilityPath, TweetVisibility.NOT_VISIBLE_TO), // 对部分人不可见
						cb.not(cb.exists(tavSubquery)) // 只要“我”不在不可见列表中即可
				);

				final Predicate followers = cb.and(cb.exists(afSubquery), publicTweets, tweetsVisibleToMe,
						tweetsInvisibleToMe);

				// 3. 提到我的推文
				// 而且这些推文应该对我可见：
				// a. 完全公开的
				// b. “对部分人可见”列表包含我的
				// c. “对部分人不可见”列表不包含我的

				// 3.1 查找提到我的推文：
				// exists (select tm from TweetMention tm
				// where tm.account.id = ?1
				// and tm.tweet.id = t.id
				// )
				Subquery<TweetMention> tmSubquery = query.subquery(TweetMention.class);
				Root<TweetMention> tmRoot = tmSubquery.from(TweetMention.class);
				tmSubquery.select(tmRoot);
				tmSubquery.where(cb.equal(tmRoot.get("account").get("id"), accountId), // 提到我
						cb.equal(afRoot.get("tweet").get("id"), root.get("id")) // 的推文
				);

				// 3.2 那些对我可见的推文：
				visibilityPath = root.get("visibility");

				// 3.2.1 完全公开的：
				// visibility =
				// eric.clapton.musician.core.entity.po.tweet.TweetVisibility.PUBLIC
				publicTweets = cb.equal(visibilityPath, TweetVisibility.PUBLIC);

				// 3.2.2 部分可见的：
				// visibility =
				// eric.clapton.musician.core.entity.po.tweet.TweetVisibility.VISIBLE_TO
				// and
				// exists (select tav from TweetAccountVisibility tav
				// where tav.tweet.id = t.id
				// and tav.account.id = ?1
				// )
				tavSubquery = query.subquery(TweetAccountVisibility.class);
				tavRoot = tavSubquery.from(TweetAccountVisibility.class);
				tavSubquery.select(tavRoot);
				tavSubquery.where(cb.equal(tavRoot.get("tweet").get("id"), root.get("id")), // 别人的推文
						cb.equal(tavRoot.get("account").get("id"), accountId) // 推文对我可见
				);

				tweetsVisibleToMe = cb.and(cb.equal(visibilityPath, TweetVisibility.VISIBLE_TO), // 对部分人可见
						cb.exists(tavSubquery));

				// 3.2.3 部分不可见的：
				// visibility =
				// eric.clapton.musician.core.entity.po.tweet.TweetVisibility.NOT_VISIBLE_TO
				// and
				// not exists (select tav from TweetAccountVisibility tav
				// where tav.tweet.id = t.id
				// and tav.account.id = ?1
				// )
				tavSubquery = query.subquery(TweetAccountVisibility.class);
				tavRoot = tavSubquery.from(TweetAccountVisibility.class);
				tavSubquery.select(tavRoot);
				tavSubquery.where(cb.equal(tavRoot.get("tweet").get("id"), root.get("id")), // 别人的推文
						cb.equal(tavRoot.get("account").get("id"), accountId) // 推文对我不可见
				);
				tweetsInvisibleToMe = cb.and(cb.equal(visibilityPath, TweetVisibility.NOT_VISIBLE_TO), // 对部分人不可见
						cb.not(cb.exists(tavSubquery)) // 只要“我”不在不可见列表中即可
				);

				final Predicate mentionMe = cb.and(cb.exists(tmSubquery), publicTweets, tweetsVisibleToMe,
						tweetsInvisibleToMe);

				query.where(cb.or(mine, followers, mentionMe));

				if (!PagingUtils.hasSort(pageable)) {
					query.orderBy(cb.desc(root.get("published")));
				}

				return null;
			}
		}, pageable, r.isNoCount());

	}
}
