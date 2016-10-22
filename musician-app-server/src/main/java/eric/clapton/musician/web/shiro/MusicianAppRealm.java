package eric.clapton.musician.web.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import eric.clapton.infrastructure.service.ServiceException;
import eric.clapton.musician.core.entity.po.account.Account;
import eric.clapton.musician.core.entity.po.account.AccountType;
import eric.clapton.musician.service.account.AccountService;

public class MusicianAppRealm extends AuthorizingRealm {
	@Resource
	private AccountService accountService;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof MusicianAppToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		MusicianAppToken token = (MusicianAppToken) authcToken;

		String accountName = token.getAccountName();
		AccountType accountType = token.getAccountType();
		String captcha = token.getCaptcha();

		Account account = null;

		try {
			account = accountService.loginWithPhoneNumber(accountName, accountType, captcha);

		} catch (ServiceException e) {
			Integer errorCode = e.getErrorCode();
			if (errorCode != null) {
				switch (errorCode) {
				case 0x1007_0010:
					throw new LockedAccountException(e.getMessage());
				case 0x1007_0020:
					throw new ExpiredCredentialsException(e.getMessage());
				case 0x1007_0030:
					throw new IncorrectCredentialsException(e.getMessage());
				}
			}

			throw new AuthenticationException(e.getMessage());
		}

		return new SimpleAuthenticationInfo(account.getId(), token.getCredentials(), getName());
	}
}
