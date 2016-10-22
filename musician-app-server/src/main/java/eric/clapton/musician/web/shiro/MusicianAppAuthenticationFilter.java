package eric.clapton.musician.web.shiro;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import eric.clapton.infrastructure.util.Assure;
import eric.clapton.infrastructure.util.StringUtils;
import eric.clapton.infrastructure.web.ContentTypes;
import eric.clapton.infrastructure.web.HttpServletRequestUtils;
import eric.clapton.musician.Constants;
import eric.clapton.musician.core.entity.dto.ApiResponse;
import eric.clapton.musician.core.entity.dto.account.AccountInfo;
import eric.clapton.musician.core.entity.po.account.AccountType;
import eric.clapton.musician.service.account.AccountService;
import eric.clapton.musician.util.ShiroSubjectUtils;

/**
 * 在 API 调用接口时进行拦截，如果检测到登录请求，则执行登录。否则进行登录合法性检测。
 * 
 * @author cheer
 *
 */
public class MusicianAppAuthenticationFilter extends AuthenticatingFilter { // FormAuthenticationFilter
	public static final String DEFAULT_ACCOUNT_TYPE_PARAM = "accountType";
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	public static final String DEFAULT_PHONE_NUMBER_PARAM = "phoneNumber";

	public static final String ERROR_CODE_INVALID_SESSION = "-268444430";
	public static final String ERROR_CODE_LOGIN_FAILURE = "-20070001";

	protected final static Logger logger = LoggerFactory.getLogger(MusicianAppAuthenticationFilter.class);

	private String phoneNumberParam = DEFAULT_PHONE_NUMBER_PARAM;
	private String accountTypeParam = DEFAULT_ACCOUNT_TYPE_PARAM;
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	private ObjectMapper objectMapper;
	private AccountService accountService;

	/**
	 * 从请求参数中读取
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		// String phoneNumber = getPhoneNumber(request);
		// String captcha = getCaptcha(request);
		// org.aspectj.weaver.reflect.ReflectionWorld.ReflectionWorldException
		// AccountType accountType = getAccountType(request);
		// String host = getHost(request);// DefaultWebSessionManager
		//
		// return new MusicianAppToken(phoneNumber, captcha, accountType, host);

		InputStream in = request.getInputStream();
		Map<?, ?> requestBody = objectMapper.readValue(in, Map.class);

		String phoneNumber = (String) requestBody.get(getPhoneNumberParam());
		String captcha = (String) requestBody.get(getCaptchaParam());
		AccountType accountType = parseAccountType((String) requestBody.get(getAccountTypeParam()));
		String host = getHost(request);

		return new MusicianAppToken(phoneNumber, captcha, accountType, host);
	}

	private static AccountType parseAccountType(String s) {
		if (StringUtils.isNullOrEmpty(s)) {
			return null;
		}
		if ("0".equals(s)) {
			return AccountType.FAN;
		}
		if ("1".equals(s)) {
			return AccountType.MUSICIAN;
		}
		if ("2".equals(s)) {
			return AccountType.SELLER;
		}

		return AccountType.valueOf(s.toUpperCase(Locale.ROOT));
	}

	protected String getPhoneNumber(ServletRequest request) {
		return WebUtils.getCleanParam(request, getPhoneNumberParam());
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (!(response instanceof HttpServletResponse)) {
			logger.warn("response is not a HttpServletResponse.");
			return false;
		}

		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse resp = (HttpServletResponse) response;

		if (isLoginRequest(request, response)) {
			// 从登录地址 POST请求登录
			if (HttpServletRequestUtils.isPost(req)) {
				logger.trace("Login submission detected. Attempting to execute login.");

				return executeLogin(request, response);
			} else {
				// 不准对登录地址进行非 POST 请求。
				issueMethodNotAllowed(req, resp);

				return false;
			}
		} else {
			// 返回登录过期的提示。
			serveJsonContent(response, ApiResponse.error(ERROR_CODE_INVALID_SESSION, "登录过期，请重新登录。"));

			return false;
		}
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		// 登录成功后，返回用户信息。

		Session session = subject.getSession();
		if (session == null) {
			throw new IllegalStateException("No session associated with the current subject.");
		}

		String sessionId = session.getId().toString();
		AccountInfo account = ShiroSubjectUtils.getAccountInfo(subject);

		logger.debug("Account (ID: {}) logged in successfully; Session id is '{}'.", account.getId(), sessionId);

		JSONObject payload = new JSONObject(2);
		payload.put("session", sessionId);
		payload.put("account", account);

		serveJsonContent(response, ApiResponse.succeed(payload));

		// prevent the chain from continuing.
		return false;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		// 返回错误信息
		try {
			serveJsonContent(response, ApiResponse.fail(ERROR_CODE_LOGIN_FAILURE, e.getMessage()));
		} catch (IOException io) {
			// TODO: wrap and rethrow
		}

		return false;
	}

	private void issueMethodNotAllowed(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());

		String message = String.format(Locale.ENGLISH, "The requested method %s is not allowed for the URL. "
				+ "For more information, refer to the API documentation.", request.getMethod());

		serveJsonContent(response, ApiResponse.fail(Constants.ERROR_CODE_METHOD_NOT_ALLOWED, message));
	}

	private void serveJsonContent(ServletResponse response, Object o) throws IOException {
		response.setContentType(ContentTypes.APPLICATION_JSON_UTF_8);

		getObjectMapper().writeValue(response.getOutputStream(), o);
	}

	public String getPhoneNumberParam() {
		return phoneNumberParam;
	}

	public void setPhoneNumberParam(String phoneNumberParam) {
		Assure.isNotEmpty(phoneNumberParam, "phoneNumberParam");

		this.phoneNumberParam = phoneNumberParam;
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		Assure.isNotEmpty(captchaParam, "captchaParam");

		this.captchaParam = captchaParam;
	}

	public String getAccountTypeParam() {
		return accountTypeParam;
	}

	public void setAccountTypeParam(String accountTypeParam) {
		Assure.isNotEmpty(accountTypeParam, "accountTypeParam");

		this.accountTypeParam = accountTypeParam;
	}

	public ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			throw new IllegalStateException("Property 'objectMapper' has not been set or is null.");
		}

		return objectMapper;
	}

	@Resource
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public AccountService getAccountService() {
		if (accountService == null) {
			throw new IllegalStateException("Property 'accountService' has not been set or is null.");
		}
		return accountService;
	}

	@Resource
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
}
