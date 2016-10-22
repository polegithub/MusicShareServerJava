package eric.clapton.musician.web.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.DelegatingSession;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eric.clapton.infrastructure.util.Assure;

/**
 * 将 SESSION ID 存储在 HTTP 头部。
 * 
 * @author cheer
 *
 */
public class MusicianAppSessionManager extends DefaultSessionManager implements WebSessionManager {
	private static final Logger logger = LoggerFactory.getLogger(MusicianAppSessionManager.class);

	public static final String DEFAULT_SESSION_ID_HEADER_NAME = "MS-Session-Id";
	public static final String HEADER_SESSION_ID_SOURCE = "http-header";

	private boolean sessionIdResponseHeaderEnabled = true;
	private String sessionIdHeaderName = DEFAULT_SESSION_ID_HEADER_NAME;

	@Override
	protected void onStart(Session session, SessionContext context) {
		super.onStart(session, context);

		if (!WebUtils.isHttp(context)) {
			logger.warn("SessionContext argument is not HTTP compatible or does not have an HTTP request/response "
					+ "pair. No session ID response header will be set.");
			return;
		}

		HttpServletRequest request = WebUtils.getHttpRequest(context);
		HttpServletResponse response = WebUtils.getHttpResponse(context);

		if (isSessionIdHeaderEnabled()) {
			setSessionIdHeader(session.getId(), response);
		}

		request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
		request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
	}

	private void setSessionIdHeader(Serializable currentId, HttpServletResponse response) {
		Assure.isNotNull(currentId, "currentId");

		response.setHeader(getSessionIdHeaderName(), currentId.toString());
	}

	private void removeSessionIdHeader(HttpServletResponse response) {
		response.setHeader(getSessionIdHeaderName(), null);
	}

	@Override
	public Serializable getSessionId(SessionKey key) {
		Serializable id = super.getSessionId(key);
		if (id == null && WebUtils.isWeb(key)) {
			ServletRequest request = WebUtils.getRequest(key);
			ServletResponse response = WebUtils.getResponse(key);
			id = getSessionId(request, response);
		}
		return id;
	}

	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		return getReferencedSessionId(request, response);
	}

	private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {
		String id = getSessionIdFromRequestHeader(request);
		if (id != null) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, HEADER_SESSION_ID_SOURCE);
		}

		if (id != null) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
			// automatically mark it valid here. If it is invalid, the
			// onUnknownSession method below will be invoked and we'll remove
			// the attribute at that time.
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
		}
		return id;
	}

	private String getSessionIdFromRequestHeader(ServletRequest request) {
		if (!isSessionIdHeaderEnabled()) {
			logger.debug("Session ID header is disabled - session id will not be acquired from the request header.");
			return null;
		}

		if (!(request instanceof HttpServletRequest)) {
			logger.debug("Current request is not an HttpServletRequest - cannot get session ID. Returning null.");
			return null;
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		return httpRequest.getHeader(getSessionIdHeaderName());
	}

	@Override
	protected void onExpiration(Session s, ExpiredSessionException ese, SessionKey key) {
		super.onExpiration(s, ese, key);

		onInvalidation(key);
	}

	@Override
	protected void onInvalidation(Session session, InvalidSessionException ise, SessionKey key) {
		super.onInvalidation(session, ise, key);

		onInvalidation(key);
	}

	private void onInvalidation(SessionKey key) {
		ServletRequest request = WebUtils.getRequest(key);
		if (request != null) {
			request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID);
		}
		if (WebUtils.isHttp(key)) {
			logger.debug("Referenced session was invalid.  Removing session ID cookie.");

			removeSessionIdHeader(WebUtils.getHttpResponse(key));
		} else {
			logger.warn("SessionKey argument is not HTTP compatible or does not have an HTTP request/response "
					+ "pair. Session ID header will not be removed from the response due to invalidated session.");
		}
	}

	@Override
	protected void onStop(Session session, SessionKey key) {
		super.onStop(session, key);

		if (WebUtils.isHttp(key)) {
			HttpServletResponse response = WebUtils.getHttpResponse(key);

			logger.debug("Session has been stopped (subject logout or explicit stop).  Removing session ID cookie.");

			removeSessionIdHeader(response);
		} else {
			logger.debug("SessionKey argument is not HTTP compatible or does not have an HTTP request/response "
					+ "pair. Session ID header will not be removed from the response due to stopped session.");
		}
	}

	public boolean isSessionIdHeaderEnabled() {
		return sessionIdResponseHeaderEnabled;
	}

	public void setSessionIdHeaderEnabled(boolean sessionIdResponseHeaderEnabled) {
		this.sessionIdResponseHeaderEnabled = sessionIdResponseHeaderEnabled;
	}

	public String getSessionIdHeaderName() {
		Assure.isNotEmpty(sessionIdHeaderName, "sessionIdHeaderName");

		return sessionIdHeaderName;
	}

	public void setSessionIdHeaderName(String sessionIdHeaderName) {
		this.sessionIdHeaderName = sessionIdHeaderName;
	}

	@Override
	public boolean isServletContainerSessions() {
		return false;
	}

	protected Session createExposedSession(Session session, SessionContext context) {
		if (!WebUtils.isWeb(context)) {
			return super.createExposedSession(session, context);
		}
		ServletRequest request = WebUtils.getRequest(context);
		ServletResponse response = WebUtils.getResponse(context);
		SessionKey key = new WebSessionKey(session.getId(), request, response);
		return new DelegatingSession(this, key);
	}

	protected Session createExposedSession(Session session, SessionKey key) {
		if (!WebUtils.isWeb(key)) {
			return super.createExposedSession(session, key);
		}

		ServletRequest request = WebUtils.getRequest(key);
		ServletResponse response = WebUtils.getResponse(key);
		SessionKey sessionKey = new WebSessionKey(session.getId(), request, response);
		return new DelegatingSession(this, sessionKey);
	}

}
