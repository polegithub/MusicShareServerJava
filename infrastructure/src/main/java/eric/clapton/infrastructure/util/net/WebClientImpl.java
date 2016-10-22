package eric.clapton.infrastructure.util.net;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;

public class WebClientImpl implements WebClient {
	private final MultiThreadedHttpConnectionManager connectionManager;
	private HttpClient client;
	private HttpMethodRetryHandler handler;

	private static Logger LOGGER = LoggerFactory.getLogger(WebClientImpl.class);

	public WebClientImpl() {
		this.connectionManager = new MultiThreadedHttpConnectionManager();
		this.handler = new DefaultHttpMethodRetryHandler(3, true);

		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setConnectionTimeout(DEFAULT_CONNECTION_TIME_OUT);
		params.setSoTimeout(DEFAULT_SOCKET_TIME_OUT);
		params.setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTION_PER_HOST);
		params.setMaxTotalConnections(DEFAULT_MAX_TOTAL_CONNECTIONS);

		client = new HttpClient(connectionManager);
	}

	public static final int DEFAULT_CONNECTION_TIME_OUT = 60 * 1000;
	public static final int DEFAULT_SOCKET_TIME_OUT = 65000;
	public static final int DEFAULT_MAX_CONNECTION_PER_HOST = 50;
	public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 200;
	public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;

	public HttpMethodRetryHandler getHttpMethodRetryHandler() {
		return handler;
	}

	@Override
	public byte[] downloadData(String url) throws WebException {
		GetMethod getMethod = null;

		try {
			getMethod = new GetMethod(url);
			int statusCode = client.executeMethod(getMethod);

			if (statusCode == HttpStatus.SC_OK) {
				return getMethod.getResponseBody();
			}

			throw new WebException(String.format(Locale.CHINA, "无法从 URI[%s] 下载资源。HTTP 状态响应代码不正确：%d %s。", statusCode,
					getMethod.getStatusText()));
		} catch (Throwable t) {
			throw new WebException(String.format(Locale.CHINA, "无法从 URI[%s] 下载资源。出现了未知的错误。", url), t);
		} finally {
			tryReleaseConnection(getMethod);
		}
	}

	@Override
	public String downloadString(String uri) throws WebException {
		return downloadString(uri, DEFAULT_CHARSET);
	}

	@Override
	public String downloadString(String uri, Charset charset) throws WebException {
		return new String(downloadData(uri), charset);
	}

	private static void tryReleaseConnection(HttpMethod method) {
		if (method == null) {
			return;
		}

		try {
			method.releaseConnection();
		} catch (Throwable t) {
			LOGGER.warn("Failed to release HTTP connection. An error has occurred.", t);
		}
	}

	public int getConnectionTimeOut() {
		return connectionManager.getParams().getConnectionTimeout();
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
	}

	public int getSocketTimeOut() {
		return connectionManager.getParams().getSoTimeout();
	}

	public void setSocketTimeOut(int socketTimeOut) {
		connectionManager.getParams().setSoTimeout(socketTimeOut);
	}

	public int getMaxConnectionPerHost() {
		return connectionManager.getParams().getDefaultMaxConnectionsPerHost();
	}

	public void setMaxConnectionPerHost(int maxConnectionPerHost) {
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
	}

	public int getMaxTotalConnections() {
		return connectionManager.getParams().getMaxTotalConnections();
	}

	public void setMaxTotalConnections(int maxTotalConnections) {
		connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
	}

	@Override
	public String uploadString(String uri, String data, String contentType) throws WebException {
		return uploadString(uri, data, contentType, null);
	}

	@Override
	public String uploadString(String uri, String data, String contentType, Map<String, String> headers)
			throws WebException {
		PostMethod post = null;

		try {
			post = new PostMethod(uri);
			post.setRequestEntity(new StringRequestEntity(data, contentType, "UTF-8"));
			if (headers != null) {
				for (String key : headers.keySet()) {
					post.setRequestHeader(key, headers.get(key));
				}
			}
			int statusCode = client.executeMethod(post);

			if (statusCode == HttpStatus.SC_OK) {
				return post.getResponseBodyAsString();
			}

			throw new WebException(String.format(Locale.CHINA, "无法从 URI[%s] 下载资源。HTTP 状态响应代码不正确：%d %s。", post.getURI(),
					statusCode, post.getStatusText()));
		} catch (Throwable t) {
			throw new WebException(String.format(Locale.CHINA, "无法从 URI[%s] 下载资源。出现了未知的错误。", uri), t);
		} finally {
			tryReleaseConnection(post);
		}
	}

}
