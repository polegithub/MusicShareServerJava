package eric.clapton.infrastructure.util;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
	protected ServletUtils() {

	}

	public static boolean looksLikeAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");
		if (requestedWith != null && requestedWith.contains("XMLHttpRequest")) {
			return true;
		}

		String accept = request.getHeader("accept");
		return accept != null && accept.contains("application/json");
	}
}
