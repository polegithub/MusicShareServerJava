package eric.clapton.infrastructure.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SetCommonDataInterceptor extends HandlerInterceptorAdapter {

    /** The path matcher. */
    private final PathMatcher pathMatcher = new AntPathMatcher();

    /** The Constant DEFAULT_EXCLUDE_PARAMETER_PATTERN. */
    private static final String[] DEFAULT_EXCLUDE_PARAMETER_PATTERN = new String[] {
            "\\&\\w*page.pn=\\d+", "\\?\\w*page.pn=\\d+",
            "\\&\\w*page.size=\\d+" };

    /** The exclude parameter patterns. */
    private String[] excludeParameterPatterns = DEFAULT_EXCLUDE_PARAMETER_PATTERN;

    /** The exclude url patterns. */
    private String[] excludeUrlPatterns = null;

    /**
     * Sets the exclude parameter patterns.
     * 
     * @param excludeParameterPatterns
     *            the new exclude parameter patterns
     */
    public void setExcludeParameterPatterns(String[] excludeParameterPatterns) {
        this.excludeParameterPatterns = excludeParameterPatterns;
    }

    /**
     * Sets the exclude url patterns.
     * 
     * @param excludeUrlPatterns
     *            the new exclude url patterns
     */
    public void setExcludeUrlPatterns(final String[] excludeUrlPatterns) {
        this.excludeUrlPatterns = excludeUrlPatterns;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
     * (javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        if (isExclude(request)) {
            return true;
        }

        if (request.getAttribute(Constants.CONTEXT_PATH) == null) {
            request.setAttribute(Constants.CONTEXT_PATH,
                    request.getContextPath());
        }
        if (request.getAttribute(Constants.CURRENT_URL) == null) {
            request.setAttribute(Constants.CURRENT_URL,
                    extractCurrentURL(request, true));
        }
        if (request.getAttribute(Constants.REFERER) == null) {
            request.setAttribute(Constants.REFERER, extractReferer(request));
        }

        return true;
    }

    /**
     * Checks if is exclude.
     * 
     * @param request
     *            the request
     * @return true, if is exclude
     */
    private boolean isExclude(final HttpServletRequest request) {
        if (excludeUrlPatterns == null) {
            return false;
        }
        for (String pattern : excludeUrlPatterns) {
            if (pathMatcher.match(pattern, request.getServletPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extract current url.
     * 
     * @param request
     *            the request
     * @param needQueryString
     *            the need query string
     * @return the string
     */
    private String extractCurrentURL(HttpServletRequest request,
            boolean needQueryString) {
        String url = request.getRequestURI();
        String queryString = request.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            queryString = "?" + queryString;
            for (String pattern : excludeParameterPatterns) {
                queryString = queryString.replaceAll(pattern, "");
            }
            if (queryString.startsWith("&")) {
                queryString = "?" + queryString.substring(1);
            }
        }
        if (!StringUtils.isEmpty(queryString) && needQueryString) {
            url = url + queryString;
        }
        return getBasePath(request) + url;
    }

    private String extractReferer(HttpServletRequest request) {
        String url = request.getParameter(Constants.REFERER);

        // 使用Filter时 文件上传时 getParameter时为null 所以改成Interceptor

        if (StringUtils.isEmpty(url)) {
            url = request.getHeader("Referer");
        }

        if (!StringUtils.isEmpty(url)
                && (url.startsWith("http://") || url.startsWith("https://"))) {
            return url;
        }

        if (!StringUtils.isEmpty(url)
                && url.startsWith(request.getContextPath())) {
            url = getBasePath(request) + url;
        }
        return url;
    }

    /**
     * Gets the base path.
     * 
     * @param req
     *            the req
     * @return the base path
     */
    private String getBasePath(HttpServletRequest req) {
        StringBuffer baseUrl = new StringBuffer();
        String scheme = req.getScheme();
        int port = req.getServerPort();

        // String servletPath = req.getServletPath ();
        // String pathInfo = req.getPathInfo ();

        baseUrl.append(scheme); // http, https
        baseUrl.append("://");
        baseUrl.append(req.getServerName());
        if ((scheme.equals("http") && port != 80)
                || (scheme.equals("https") && port != 443)) {
            baseUrl.append(':');
            baseUrl.append(req.getServerPort());
        }
        return baseUrl.toString();
    }

    private static class Constants {

        /** 上个页面地址. */
        public static final String REFERER = "referer";

        /** 当前请求的地址 带参数. */
        public static final String CURRENT_URL = "currentURL";

        /** The context path. */
        public static final String CONTEXT_PATH = "base";
    }

}
