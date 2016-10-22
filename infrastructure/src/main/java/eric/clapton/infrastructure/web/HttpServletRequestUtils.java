package eric.clapton.infrastructure.web;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtils {
    protected HttpServletRequestUtils() {

    }

    public final static String HTTP_METHOD_POST = "POST";
    public final static String HTTP_METHOD_GET = "GET";

    public static final boolean isPost(HttpServletRequest request) {
        return HTTP_METHOD_POST.equalsIgnoreCase(request.getMethod());
    }

    public static final boolean isGet(HttpServletRequest request) {
        return HTTP_METHOD_GET.equalsIgnoreCase(request.getMethod());
    }
}
