package eric.clapton.musician.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import eric.clapton.infrastructure.util.tuple.Tuple2;
import eric.clapton.infrastructure.util.tuple.Tuples;

public class ApiInterceptor extends HandlerInterceptorAdapter {
	private final String HASH_SALT = "rT|ioLh~++G^-V- +iEBdusy9rBDJ%CgIdxmxFNchlm+ekJnblCMJADL07D2";

	public ApiInterceptor() {
		System.out.println("hello!");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String hash = request.getHeader("MS-Param-Hash");
		if (hash == null || hash.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request header field 'MS-Param-Hash' is missing.");
			return false;
		}

		Map<String, String[]> parameterMap = request.getParameterMap();

		List<Tuple2<String, String[]>> parameters = new ArrayList<Tuple2<String, String[]>>();
		parameterMap.forEach((k, v) -> parameters.add(Tuples.create(k, v)));
		StringBuilder input = new StringBuilder();

		parameters.stream().sorted((x, y) -> x.getItem1().compareTo(y.getItem1())).forEachOrdered((e) -> {
			if (input.length() > 0) {
				input.append('&');
			}
			input.append(encodeURL(e.getItem1())).append('=').append(encodeURL(e.getItem2()[0]));
		});
		input.append(HASH_SALT);

		String paramHash = computeMD5Hash(input.toString());
		if (!paramHash.equals(hash)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Param hash sum mismatch.");
			return false;
		}
		return true;
	}

	private static String computeMD5Hash(String s) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new ApiInterceptionException(
					"Error computing MD5 Hash: Cryptographic algorithm 'MD5' is unavailable.", e);
		}

		md5.update(s.getBytes());
		return toHexString(md5.digest());
	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
	private static final String DEFAULT_ENCODING = "utf-8";

	private static String toHexString(byte[] bytes) {
		int len = bytes.length;
		StringBuilder out = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			out.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]).append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return out.toString();
	}

	private static final String encodeURL(String s) {
		try {
			return URLEncoder.encode(s, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new ApiInterceptionException(
					"Error performing URL encoding: Encoding '" + DEFAULT_ENCODING + "' is not supported.", e);
		}
	}
}
