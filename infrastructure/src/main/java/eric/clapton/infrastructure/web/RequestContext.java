package eric.clapton.infrastructure.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class RequestContext {
	private final static ThreadLocal<RequestContext> contexts = new ThreadLocal<RequestContext>();

	private ServletContext context;

	private HttpSession session;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private Map<String, Cookie> cookies;
	private String base;

	public static RequestContext begin(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) {
		RequestContext rc = new RequestContext();
		rc.context = ctx;
		rc.request = req;
		rc.response = res;
		rc.session = req.getSession(false);
		rc.cookies = new HashMap<String, Cookie>();
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (Cookie ck : cookies) {
				rc.cookies.put(ck.getName(), ck);
			}

		rc.base = req.getScheme() + "://" + req.getServerName()
				+ (req.getServerPort() == 80 ? "" : (":" + req.getServerPort()))
				+ ("/".equalsIgnoreCase(req.getContextPath()) ? "" : req.getContextPath());
		contexts.set(rc);
		return rc;
	}

	public static RequestContext get() {
		return contexts.get();
	}

	public void end() {
		this.context = null;
		this.request = null;
		this.response = null;
		this.session = null;
		this.cookies = null;
		this.base = null;
		contexts.remove();
	}

	public ServletContext context() {
		return context;
	}

	public HttpSession session() {
		return session;
	}

	public HttpSession session(boolean create) {
		return (session == null && create) ? (session = request.getSession(create)) : session;
	}

	public HttpServletRequest request() {
		return request;
	}

	public HttpServletResponse response() {
		return response;
	}

	public String base() {
		return base;
	}
}
