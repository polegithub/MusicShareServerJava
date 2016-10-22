package eric.clapton.musician.service.account.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eric.clapton.infrastructure.service.ServiceException;
import eric.clapton.infrastructure.service.impl.BaseServiceImpl;
import eric.clapton.infrastructure.util.Assure;
import eric.clapton.infrastructure.util.EnumUtils;
import eric.clapton.musician.core.entity.dto.account.AccountInfo;
import eric.clapton.musician.core.entity.dto.account.AddressInfo;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.account.AccountSkill;
import eric.clapton.musician.core.entity.po.account.AccountState;
import eric.clapton.musician.core.entity.po.account.AccountType;
import eric.clapton.musician.core.entity.po.account.Captcha;
import eric.clapton.musician.core.entity.po.skill.Skill;
import eric.clapton.musician.repository.account.AccountRepository;
import eric.clapton.musician.service.account.AccountService;
import eric.clapton.musician.service.account.CaptchaService;
import eric.clapton.musician.service.skill.SkillService;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, AccountRepository> implements AccountService {
	@Resource
	private CaptchaService captchaService;
	@Resource
	private SkillService skillService;

	@Override
	@Resource
	protected void baseSetRepository(AccountRepository repository) {
		super.setRepository(repository);
	}

	@Override
	public Account findByAccountName(String userName) {
		return getRepository().findByAccountName(userName);
	}

	@Override
	public Account findByPhoneNumber(String phoneNumber) {
		return getRepository().findByPhoneNumber(phoneNumber);
	}

	@Override
	@Transactional
	public Account loginWithPhoneNumber(String phoneNumber, AccountType accountType, String captcha) {
		Account account = findByPhoneNumber(phoneNumber);
		if (account == null) {
			account = registerWithPhoneNumber(phoneNumber, accountType, captcha);
		} else {
			if (account.getState() == AccountState.LOCKED) {
				throw new ServiceException(0x1007_0010, "您的帐号已被锁定，无法登录。");
			}
		}

		// TODO: for test purpose only
		if ("9999".equals(captcha)) {
			return onLoggedIn(account);
		}

		Captcha c = captchaService.getCaptchaFor(phoneNumber);
		if (c == null) {
			throw new ServiceException(0x1007_0020, "我们还没有向您的手机发送验证码，或者该验证码已经过期。");
		}

		if (!c.getCaptcha().equalsIgnoreCase(captcha)) {
			// 输入的验证码不对
			throw new ServiceException(0x1007_0030, "您输入的验证码不正确。");
		}

		// 删除使用过的验证码。
		captchaService.delete(c);

		return onLoggedIn(account);
	}

	protected Account onLoggedIn(Account account) {
		account.setLastLoggedIn(Calendar.getInstance());

		return save(account);
	}

	protected Account registerWithPhoneNumber(String phoneNumber, AccountType accountType, String captcha) {
		Account account = new Account();

		account.setPhoneNumber(phoneNumber);
		account.setType(accountType);
		if (accountType == AccountType.FAN) {
			account.setState(AccountState.NORMAL);
		} else {
			account.setState(AccountState.TO_BE_REVIEWED);
		}

		return account;
	}

	@Override
	public AccountInfo getAccountInfo(Long id) {
		return getAccountInfo(findOne(id));
	}

	@Override
	public AccountInfo getAccountInfo(Account account) {
		if (account == null) {
			return null;
		}

		AccountInfo i = new AccountInfo();
		AddressInfo address = new AddressInfo(account.getCountry(), account.getProvince(), account.getCity(),
				account.getDistrict());
		i.setAddress(address);
		i.setAvatar(account.getAvatar());
		i.setFollowers(account.getFollowers());
		i.setFollowing(account.getFollowing());
		i.setGender(EnumUtils.safeOrdinal(account.getGender()));
		i.setId(account.getId());
		i.setIntro(account.getIntro());
		i.setNickName(account.getNickName());
		i.setPhoneNumber(account.getPhoneNumber());
		i.setState(EnumUtils.safeOrdinal(account.getState()));
		i.setUserName(account.getUserName());
		i.setUserType(EnumUtils.safeOrdinal(account.getType()));

		return i;
	}

	@Override
	@Transactional
	public Account updateSkills(Long accountId, List<Long> skillIdList) {
		Assure.isNotNull(accountId, "accountId");
		Assure.isNotNull(skillIdList, "skillIdList");

		Account account = findOne(accountId);
		if (account == null) {
			throw ServiceException.notFound("找不到帐号。");
		}

		List<AccountSkill> skills = account.getSkills();
		if (skills == null) {
			account.setSkills(skills = new ArrayList<AccountSkill>());
		}

		Stream<Long> skillIdStream = skillIdList.stream().distinct();
		for (Long skillId : skillIdList) {
			Optional<AccountSkill> s = skills.stream().filter(e -> skillId.equals(e.getSkillId())).findFirst();
			if (s.isPresent()) {
				continue;
			}

			Skill skill = skillService.findOne(skillId);
			if (skill == null || skill.isDisabled() || skill.isDeleted()) {
				throw new ServiceException(0x7644_0081, "技能不存在，或者已被删除或停用。");
			}
		}

		skills.clear();
		skills.addAll(skillIdStream.map(i -> new AccountSkill(account, skillService.findOne(i)))
				.collect(Collectors.<AccountSkill> toList()));

		return save(account);
	}
}
