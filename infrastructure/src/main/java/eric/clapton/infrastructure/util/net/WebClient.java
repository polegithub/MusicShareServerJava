package eric.clapton.infrastructure.util.net;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 提供一个统一的接口用于下载和上传 HTTP 资源。
 * 
 * @author xuw
 *
 */
public interface WebClient {
	/**
	 * Downloads the resource as a byte array from the URI specified.
	 * 
	 * @param uri
	 *            The URI from which to download data.
	 * @return A Byte array containing the downloaded resource.
	 * @throws WebException
	 *             An error occurred while downloading data.
	 */
	byte[] downloadData(String uri) throws WebException;

	String downloadString(String uri) throws WebException;

	String downloadString(String uri, Charset charset) throws WebException;

	// Charset getCharset();

	String uploadString(String uri, String data, String contentType) throws WebException;

	String uploadString(String uri, String data, String contentType, Map<String, String> headers) throws WebException;
}
