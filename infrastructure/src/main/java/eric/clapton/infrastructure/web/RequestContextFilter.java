package eric.clapton.infrastructure.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class RequestContextFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		RequestContext rc = RequestContext.begin(getServletContext(), request, response);
		try {
			before(request, response, filterChain, rc);
			filterChain.doFilter(request, response);
			after(request, response, filterChain, rc);
		} finally {
			if (rc != null) {
				rc.end();
			}
		}
	}

	protected void before(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain,
			RequestContext rc) throws ServletException, IOException {

	}

	protected void after(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain,
			RequestContext rc) throws ServletException, IOException {

	}
}
